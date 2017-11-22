package com.ltz.edittextlistview.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ltz.edittextlistview.R;
import com.ltz.edittextlistview.bean.ItemBean;
import com.ltz.edittextlistview.ui.MainActivity;
import com.ltz.edittextlistview.widget.DateTimePickDialogUtil2016;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by luoxiaoke on 2016/1/16 12:14.
 * listitem适配器
 */
public class ListAdapter extends BaseAdapter {

    private List<ItemBean> datas;
    private Context context;
    private LayoutInflater inflater;
    private Activity activity;

    //List<Map<Integer, String>> p = new ArrayList<>();

    public ListAdapter(List<ItemBean> datas, Context context, Activity activity) {
        this.datas = datas;
        this.context = context;
        this.activity = activity;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item, null);
            viewHolder.place = (EditText) convertView.findViewById(R.id.place_value);
            viewHolder.stime = (TextView) convertView.findViewById(R.id.stime_value);
            viewHolder.etime = (TextView) convertView.findViewById(R.id.etime_value);
            viewHolder.num = (TextView) convertView.findViewById(R.id.num);
            viewHolder.tvDelete = (TextView) convertView.findViewById(R.id.tv_detele);
            viewHolder.delete = (RelativeLayout) convertView.findViewById(R.id.detele);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if (position == 0) {
            viewHolder.tvDelete.setVisibility(View.GONE);
        } else {
            viewHolder.tvDelete.setVisibility(View.VISIBLE);
        }

        viewHolder.num.setText(String.valueOf(position + 1));


        final ItemBean bean = datas.get(position);
        if (viewHolder.place.getTag() instanceof TextWatcher) {
            viewHolder.place.removeTextChangedListener((TextWatcher) (viewHolder.place.getTag()));
        }

        if (TextUtils.isEmpty(bean.getPlace())) {
            viewHolder.place.setText("");
        } else {
            viewHolder.place.setText(bean.getPlace());
        }

        if (bean.isFocus()) {
            if (!viewHolder.place.isFocused()) {
                viewHolder.place.requestFocus();
            }
            CharSequence text = bean.getPlace();
            viewHolder.place.setSelection(TextUtils.isEmpty(text) ? 0 : text.length());
        } else {
            if (viewHolder.place.isFocused()) {
                viewHolder.place.clearFocus();
            }
        }

        viewHolder.place.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(final View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    final boolean focus = bean.isFocus();
                    check(position);
                    if (!focus && !viewHolder.place.isFocused()) {
                        viewHolder.place.requestFocus();
                        viewHolder.place.onWindowFocusChanged(true);
                    }
                }
                return false;
            }
        });

        final TextWatcher watcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s)) {
                    bean.setPlace(null);
                } else {
                    bean.setPlace(String.valueOf(s));
                }
            }
        };

        viewHolder.place.addTextChangedListener(watcher);
        viewHolder.place.setTag(watcher);


        viewHolder.stime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DateTimePickDialogUtil2016 pick = new DateTimePickDialogUtil2016(activity, "");
                pick.dateTimePicKDialog(viewHolder.stime, position, 1);
            }
        });
        viewHolder.etime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DateTimePickDialogUtil2016 pick = new DateTimePickDialogUtil2016(activity, "");
                pick.dateTimePicKDialog(viewHolder.etime, position, 2);
            }
        });

        viewHolder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position != 0) {
                    new AlertDialog.Builder(context).setTitle("你确定要删除行程明细" + (position + 1) + "吗？").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            MainActivity.data2.remove(position);
                            notifyDataSetChanged();
                            dialog.dismiss();
                        }
                    }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();
                }

            }
        });


        viewHolder.stime.setText(bean.getStime());
        viewHolder.etime.setText(bean.getEtime());

        return convertView;
    }


    private void check(int position) {
        for (ItemBean l : datas) {
            l.setFocus(false);
        }
        datas.get(position).setFocus(true);
    }

    class ViewHolder {
        EditText place;
        TextView stime;
        TextView etime;
        TextView num;
        TextView tvDelete;
        RelativeLayout delete;
    }
}

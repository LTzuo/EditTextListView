package com.ltz.edittextlistview.adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;
import com.ltz.edittextlistview.R;
import com.ltz.edittextlistview.bean.Bean;
import java.util.List;

/**
 * listview 里面嵌套ediTtext 滑动之后 保存数据
 * Created by 1 on 2017/11/22.
 */
public class MyAdapter extends BaseAdapter{

    private Context context;
    private List<Bean> mDatas;
    private LayoutInflater inflater;

    public MyAdapter(Context context, List<Bean> datas){
        this.context = context;
        this.mDatas = datas;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int i) {
        return mDatas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int position, View v, ViewGroup viewGroup) {
       final  Holder holder;
        if(v == null){
            holder = new Holder();
            v = inflater.inflate(R.layout.item_list,null);
            holder.title = v.findViewById(R.id.title);
            holder.edit = v.findViewById(R.id.edit);
            v.setTag(holder);
        }else{
            holder = (Holder) v.getTag();
        }

        holder.title.setText(mDatas.get(position).getTitle());
        final Bean bean = mDatas.get(position);
        if (holder.edit.getTag() instanceof TextWatcher) {
            holder.edit.removeTextChangedListener((TextWatcher) (holder.edit.getTag()));
        }

        if (TextUtils.isEmpty(bean.getEdit_string())) {
            holder.edit.setText("");
        } else {
            holder.edit.setText(bean.getEdit_string());
        }

        if (bean.isFocus()) {
            if (!holder.edit.isFocused()) {
                holder.edit.requestFocus();
            }
            CharSequence text = bean.getEdit_string();
            holder.edit.setSelection(TextUtils.isEmpty(text) ? 0 : text.length());
        } else {
            if (holder.edit.isFocused()) {
                holder.edit.clearFocus();
            }
        }

        holder.edit.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(final View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    final boolean focus = bean.isFocus();
                    check(position);
                    if (!focus && !holder.edit.isFocused()) {
                        holder.edit.requestFocus();
                        holder.edit.onWindowFocusChanged(true);
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
                    bean.setEdit_string(null);
                } else {
                    bean.setEdit_string(String.valueOf(s));
                }
            }
        };

        holder.edit.addTextChangedListener(watcher);
        holder.edit.setTag(watcher);
        return v;
    }

    private void check(int position) {
        for (Bean l : mDatas) {
            l.setFocus(false);
        }
        mDatas.get(position).setFocus(true);
    }

    class Holder {
        public TextView title;
        public EditText edit;
    }
}

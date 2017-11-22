package com.ltz.edittextlistview.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.ltz.edittextlistview.R;
import com.ltz.edittextlistview.adapter.ListAdapter;
import com.ltz.edittextlistview.adapter.MyAdapter;
import com.ltz.edittextlistview.bean.Bean;
import com.ltz.edittextlistview.bean.ItemBean;
import com.ltz.qrcode.qrcode.QRCodeEncoder;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static List<ItemBean> data2;
    public static List<Bean>    data1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ContentView(0);
    }

    private void ContentView(int id){
        if(id == 0){
            setContentView(R.layout.activity_main);
            initLayout();
        }
        else if(id == R.id.btn1){
            setContentView(R.layout.listview_layout);
            initButton1Layout();
        }else if(id == R.id.btn2){
            setContentView(R.layout.listview_layout);
            initButton2Layout();
        }else if(id == 1){
            setContentView(R.layout.qr_layout);
            initQrLayout();
        }
    }

    private void initLayout(){
        Button button1 = (Button) findViewById(R.id.btn1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContentView(R.id.btn1);
            }
        });
        Button button2 = (Button) findViewById(R.id.btn2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContentView(R.id.btn2);
            }
        });
        Button qr = (Button) findViewById(R.id.qr);
        qr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, QRCodeScanActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initButton1Layout(){
        Button  back = (Button) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContentView(0);
            }
        });
        ListView listView = (ListView) findViewById(R.id.listview);
        View footView = LayoutInflater.from(this).inflate(R.layout.button1,null);
        Button button = (Button) footView.findViewById(R.id.add);
        listView.addFooterView(footView);
        data1 = new ArrayList<>();
        for(int i = 0 ; i < 20 ; i++){
            Bean bean = new Bean();
            bean.setTitle("垃圾类别>"+i);
            data1.add(bean);
        }
        final MyAdapter adapter = new MyAdapter(MainActivity.this, data1);
        listView.setAdapter(adapter);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              ContentView(1);
            }
        });
    }

    private void initButton2Layout(){
        Button  back = (Button) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContentView(0);
            }
        });
        ListView listView = (ListView) findViewById(R.id.listview);
        View footView = LayoutInflater.from(this).inflate(R.layout.button,null);
        Button button = (Button) footView.findViewById(R.id.add);
        listView.addFooterView(footView);
        data2 = new ArrayList<>();
        data2.add(new ItemBean());
        final ListAdapter adapter = new ListAdapter(data2, this, MainActivity.this);
        listView.setAdapter(adapter);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data2.add(new ItemBean());
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void initQrLayout(){
        ImageView qr_img = (ImageView) findViewById(R.id.qr_img);
        Button  back = (Button) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContentView(0);
            }
        });
        // 二维码中间图标
        final Bitmap centerImage = BitmapFactory.decodeResource(getResources(), R.mipmap.ltz_img);
        // 生成的二维码图案
        Bitmap qrCodeImage = new QRCodeEncoder.Builder()
                .width(500) // 二维码图案的宽度
                .height(500)
                .paddingPx(0) // 二维码的内边距
                .marginPt(3) // 二维码的外边距
                .centerImage(centerImage) // 二维码中间图标
                .build()
                .encode("测试");

        qr_img.setImageBitmap(qrCodeImage);

    }



}

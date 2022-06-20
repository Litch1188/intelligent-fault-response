package com.example.intelligentfaultdiagnosis;

import android.content.Context;
//<<<<<<< HEAD
import android.content.Intent;
//=======
import android.util.Log;
//>>>>>>> 0ebcb3859964febc0bfda4cf9533c9b20a0a83ad
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class MyMessageAdapter extends BaseAdapter {
    private List<Message> messagedata;
    private Context context;

    public MyMessageAdapter(List<Message> messagedata, Context context) {
        this.messagedata = messagedata;
        this.context = context;
    }

    public void update(List<Message> messagedata, Context context)
    {
        this.messagedata = messagedata;
        this.context = context;
    }
    //添加了更新adapter的方法，传入新的list更新
    @Override
    public int getCount() {
        return messagedata.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item, parent, false);
        }
        //接收信息
        if(messagedata.get(position).getType()==2){
            convertView = LayoutInflater.from(context).inflate(R.layout.layout, parent, false);
            TextView view=convertView.findViewById(R.id.daohang);
            Button button1=convertView.findViewById(R.id.button1);
            Button button2=convertView.findViewById(R.id.button2);
            Button button3=convertView.findViewById(R.id.button3);
            Button button4=convertView.findViewById(R.id.button4);
            Button button5=convertView.findViewById(R.id.button5);
            view.setText(messagedata.get(position).getMessage());
            button1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e("点击了",button1.getText().toString());

                }
            });
            button2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e("点击了",button2.getText().toString());
                }
            });
            button3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e("点击了",button3.getText().toString());
                }
            });
            button4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e("点击了",button4.getText().toString());
                }
            });
            button5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e("点击了",button5.getText().toString());
                }
            });

        }
        else if(messagedata.get(position).getType()==1){
            TextView view=convertView.findViewById(R.id.left_message);
            view.setText(messagedata.get(position).getMessage());
            LinearLayout layout=convertView.findViewById(R.id.right_layout);
            layout.setVisibility(View.GONE);//隐藏一边的对话
        }
        else if(messagedata.get(position).getType()==3){
            convertView = LayoutInflater.from(context).inflate(R.layout.solution_message, parent, false);
            TextView view=convertView.findViewById(R.id.left_message3);
            view.setText(messagedata.get(position).getMessage());
            Button button1=convertView.findViewById(R.id.solution_button);
            button1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View button1) {
                    Intent intent=new Intent(MainActivity.mActivity,BrowseSolutionActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    MainActivity.mActivity.startActivity(intent);
                }
            });
        }
        else{
            TextView view=convertView.findViewById(R.id.right_message);
            view.setText(messagedata.get(position).getMessage());
            LinearLayout layout=convertView.findViewById(R.id.left_layout);
            layout.setVisibility(View.GONE);//隐藏一边的对话
        }
        return convertView;
    }

}

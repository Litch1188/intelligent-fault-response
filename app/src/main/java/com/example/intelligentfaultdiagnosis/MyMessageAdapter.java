package com.example.intelligentfaultdiagnosis;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
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
            convertView = LayoutInflater.from(context).inflate(R.layout.catalog, parent, false);
            TextView view=convertView.findViewById(R.id.left_message2);
            view.setText(messagedata.get(position).getMessage());

        }
        else if(messagedata.get(position).getType()==1){
            TextView view=convertView.findViewById(R.id.left_message);
            view.setText(messagedata.get(position).getMessage());
            LinearLayout layout=convertView.findViewById(R.id.right_layout);
            layout.setVisibility(View.GONE);//隐藏一边的对话
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

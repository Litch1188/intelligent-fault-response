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
import java.util.Map;

public class MyMessageAdapter extends BaseAdapter {
    private List<Message> messagedata;
    private MainActivity context;

    public MyMessageAdapter(List<Message> messagedata, MainActivity context) {
        this.messagedata = messagedata;
        this.context = context;
    }

    public void update(List<Message> messagedata, MainActivity context)
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
            Button button6=convertView.findViewById(R.id.button6);
            Button button7=convertView.findViewById(R.id.button7);
            Button[] btn_list=new Button[]{button1,button2,button3,button4,button5,button6,button7};
            for (Integer i=0;i<context.faullt_list.size();i++)
            {
                btn_list[i].setText(context.faullt_list.get(i+1));
                String msg=context.faullt_list.get(i+1);
                btn_list[i].setTag(msg);
            }
            for(int i=context.faullt_list.size();i<7;i++)
            {
                btn_list[i].setVisibility(View.GONE);
            }
            view.setText(messagedata.get(position).getMessage());
//            for(int i=0;i<context.faullt_list.size();i++) {
//            }

            button1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Log.e("点击了",button1.getText().toString());
//                    int pos=(Integer) button1.getTag();
//                    Log.e("pos",pos+"");
//                    context.get_secondlist(pos);
                    Message message=new Message();
                    String msg=(String) button1.getTag();
                    message.setMessage(msg,6);
                    messagedata.add(message);
                    context.update_list();
                    context.getSolution(message.getMessage());
                }
            });
            button2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Log.e("点击了",button2.getText().toString());
//                    int pos=(Integer) button2.getTag();
//                    Log.e("pos",pos+"");
//                    context.get_secondlist(pos);
                    Message message=new Message();
                    String msg=(String) button2.getTag();
                    message.setMessage(msg,6);
                    messagedata.add(message);
                    context.update_list();
                    context.getSolution(message.getMessage());
                }
            });
            button3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Log.e("点击了",button3.getText().toString());
//                    int pos=(Integer) button3.getTag();
//                    Log.e("pos",pos+"");
//                    context.get_secondlist(pos);
                    Message message=new Message();
                    String msg=(String) button3.getTag();
                    message.setMessage(msg,6);
                    messagedata.add(message);
                    context.update_list();
                    context.getSolution(message.getMessage());
                }
            });
            button4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Log.e("点击了",button4.getText().toString());
//                    int pos=(Integer) button4.getTag();
//                    Log.e("pos",pos+"");
//                    context.get_secondlist(pos);
                }
            });
            button5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Log.e("点击了",button5.getText().toString());
//                    int pos=(Integer) button5.getTag();
//                    Log.e("pos",pos+"");
//                    context.get_secondlist(pos);
                }
            });
            button6.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    int pos=(Integer) button6.getTag();
//                    Log.e("pos",pos+"");
//                    context.get_secondlist(pos);
                }
            });
            button7.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    int pos=(Integer) button7.getTag();
//                    Log.e("pos",pos+"");
//                    context.get_secondlist(pos);
                }
            });

        }
        else if(messagedata.get(position).getType()==1){
            convertView = LayoutInflater.from(context).inflate(R.layout.item, parent, false);
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
            button1.setTag(position);
            button1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View button1) {
                    int pos2=(Integer) button1.getTag();
                    Log.d("pos",pos2+"");
                    Intent intent=new Intent(MainActivity.mActivity,BrowseSolutionActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("Position",pos2);
                    MainActivity.mActivity.startActivity(intent);
                }
            });
        }
        else if(messagedata.get(position).getType()==4)
        {
           convertView=LayoutInflater.from(context).inflate(R.layout.score,parent,false);
           Button button1=convertView.findViewById(R.id.score_button1);
           Button button2=convertView.findViewById(R.id.score_button2);
           button1.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                  v.setSelected(true);
                  v.setClickable(false);
                  button2.setSelected(false);
                  button2.setClickable(false);
               }
           });
           button2.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   v.setSelected(true);
                   v.setClickable(false);
                   button1.setSelected(false);
                   button1.setClickable(false);
               }
           });
        }
        else if(messagedata.get(position).getType()==5)
        {
          convertView=LayoutInflater.from(context).inflate(R.layout.layout,parent,false);
            TextView view=convertView.findViewById(R.id.daohang);
            Button button1=convertView.findViewById(R.id.button1);
            Button button2=convertView.findViewById(R.id.button2);
            Button button3=convertView.findViewById(R.id.button3);
            Button button4=convertView.findViewById(R.id.button4);
            Button button5=convertView.findViewById(R.id.button5);
            Button button6=convertView.findViewById(R.id.button6);
            Button button7=convertView.findViewById(R.id.button7);
            Button[] btn_list=new Button[]{button1,button2,button3,button4,button5,button6,button7};
            view.setText(messagedata.get(position).getMessage());
            int size=context.second_map_list.get(messagedata.get(position).getMessage()).size();
            for(int i=0;i<size;i++)
            {
                btn_list[i].setText(context.second_map_list.get(messagedata.get(position).getMessage()).get(i));
            }

        }
        else{
            convertView = LayoutInflater.from(context).inflate(R.layout.item, parent, false);
            TextView view=convertView.findViewById(R.id.right_message);
            view.setText(messagedata.get(position).getMessage());
            LinearLayout layout=convertView.findViewById(R.id.left_layout);
            layout.setVisibility(View.GONE);//隐藏一边的对话
        }
        return convertView;
    }



}

package com.example.intelligentfaultdiagnosis;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<Message> messagedata=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Message message1=new Message();
        Message message2=new Message();
        Message message3=new Message();
        Message message4=new Message();
        message1.setMessage("Hello!",1);
        message2.setMessage("这是一条message1",0);
        message3.setMessage("这是一条message2",1);
        message4.setMessage("这是一条message3",0);
        messagedata.add(message1);
        messagedata.add(message2);
        messagedata.add(message3);
        messagedata.add(message4);
        ListView listview=findViewById(R.id.List_Vessel);
        listview.setAdapter(new MyMessageAdapter(messagedata,this));
    }
}
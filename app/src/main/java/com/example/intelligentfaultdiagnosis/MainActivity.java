package com.example.intelligentfaultdiagnosis;

import androidx.appcompat.app.AppCompatActivity;
import static java.security.AccessController.getContext;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    private List<Message> messagedata=new ArrayList<>();
    public static List<Solution_Data> solutiondata=new ArrayList<>();
    private Button button1;
    private EditText editText;
    private MyMessageAdapter MyAdapt;
    private ListView listview;
    public static Activity mActivity;
    private int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mActivity = this;
        listview=findViewById(R.id.List_Vessel);//初始化listview布局
        button1=(Button)findViewById(R.id.send_button );//初始化发送按钮布局
        button1.setOnClickListener((v)->{sendMessage(button1);});//设置点击监听器，sendMessage有个View形参以确定是哪个布局
        editText=(EditText)findViewById(R.id.SendText);//初始化输入框布局
        editText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER))
                {
                    sendMessage(v);
                    return true;
                }
                return false;
            }
        });
        Message message1=new Message();
        message1.setMessage("我是您的小助手鹏鹏，请问您遇到了什么问题？",2);
        messagedata.add(message1);
        Message message2=new Message();
        message2.setMessage("我是您的小助手鹏鹏，请问您遇到了什么问题？",3);
        messagedata.add(message2);
        Message message3=new Message();
        message3.setMessage("sjd",4);
        messagedata.add(message3);
        MyAdapt=new MyMessageAdapter(messagedata,this);//初始化渲染列表
        listview.setAdapter(MyAdapt);
    }

    //sendMessage函数将获取的输入框内容封装成Message实例，更新mainActivity里的messagedata列表
    // 然后再用update函数更新MyMessageAdapter，最后重新渲染列表
    public void sendMessage(View View)
    {
        editText=(EditText)findViewById(R.id.SendText);
        String SendMsg=editText.getText().toString();
        Message userMsg=new Message();
        userMsg.setMessage(SendMsg,0);
        messagedata.add(userMsg);
        MyAdapt.update(messagedata,this);//更新Listview列表
        listview.setAdapter(MyAdapt);
        editText=(EditText)findViewById(R.id.SendText);
        editText.setText("");
        listview.setSelection(listview.getBottom());
        getSolution(SendMsg);


    }


    public void getSolution(String sentence){
        AndroidNetworking.get("http://47.112.216.3/model/autoLocateFault?sentence="+sentence.toString())
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject data=response.getJSONObject("fault");
                            Message fault_info=new Message();
                             String fault_name =data.getString("fault_name");
                             fault_info.setMessage("您遇到的故障可能为"+fault_name,3);
                             messagedata.add(fault_info);
                             Log.d("fault_name",fault_name.toString());
                             update_list();
                             JSONObject solution=response.getJSONObject("solution");
                             id=solution.getInt("solution_id");
                             JSONArray step=solution.getJSONArray("step_list");
                             Integer size=step.length();
                             solutiondata.clear();
                             for(int i=0;i<size;i++)
                             {
                                 JSONObject solution_I= step.getJSONObject(i);
                                 String step_content=solution_I.getString("content");
                                 String link=solution_I.getString("picture_path");
                                 Log.d("link",link);
                                 Solution_Data step1 =new Solution_Data();
                                 step1.setStep(step_content,link,id);
                                 solutiondata.add(step1);
                             }

                        }
                        catch (JSONException Ex){
                            Ex.printStackTrace();
                            Toast.makeText(MainActivity.this,"Data error!",Toast.LENGTH_SHORT).show();
                        }

                    }
                    @Override
                    public void onError(ANError anError) {
                        Toast.makeText(MainActivity.this,"Network error!", Toast.LENGTH_SHORT).show();
                    }
                });
        listview.setSelection(listview.getBottom());
}


    public void update_list(){
        MyAdapt.update(messagedata,this);//更新Listview列表
        listview.setAdapter(MyAdapt);
        Log.d("fault_name","1");
        listview.setSelection(listview.getBottom());
    }

    public static List<Solution_Data> givedata()
    {
        return solutiondata;
    }

    public void update_solution(){
        //每个解决方案button点击的时候获取对应的解决方案
        Button button1=(Button)findViewById(R.id.solution_button);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View button1) {
                Intent intent=new Intent(MainActivity.mActivity,BrowseSolutionActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                MainActivity.mActivity.startActivity(intent);
            }
        });

    }

}
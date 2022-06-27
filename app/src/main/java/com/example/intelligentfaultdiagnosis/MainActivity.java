package com.example.intelligentfaultdiagnosis;

import androidx.appcompat.app.AppCompatActivity;
import static java.security.AccessController.getContext;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    private List<Message> messagedata=new ArrayList<>();
    public static List<Solution_Data> solutiondata=new ArrayList<>();
    private Button button1;
    private EditText editText;
    private MyMessageAdapter MyAdapt;
    private ListView listview;
    public static Activity mActivity;
    public static ArrayList<List<Solution_Data>> Sol_List=new ArrayList<>();
    public  Map<String,Map<Integer,String>> second_map_list=new HashMap<>();
    public String[] fault_list=new String[10];
    public Map<Integer,String> total_map=new HashMap<>();
    //维护一个存储所有solution_data_list的ArrayList
    public static int Pos=-1;
    //每一个solution_data_list在ArrayList里面的位置
    private int pos;
    private int position;
    //点击按钮时拿到的位置：按钮所在的Item在对话Listview里面的位置
    public Map<Integer,String> faullt_list=new HashMap<>();
    public Map<Integer,String> second_maplist=new HashMap<>();
    //用position去找Pos的数组，大家都是从0开始的
    public static int[] pos_to_Pos=new int[100];
    private int id;
    public static Message scoreMsg=new Message();
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
        get_faultlist();
        Message message2=new Message();
        message2.setMessage("我是您的小助手鹏鹏，请问您遇到了什么问题？",1);
        messagedata.add(message2);
//        Message message3=new Message();
//        message3.setMessage("sjd",4);
//        messagedata.add(message3);
        MyAdapt=new MyMessageAdapter(messagedata,this);//初始化渲染列表
        listview.setAdapter(MyAdapt);
    }

    //sendMessage函数将获取的输入框内容封装成Message实例，更新mainActivity里的messagedata列表
    // 然后再用update函数更新MyMessageAdapter，最后重新渲染列表
    public void sendMessage(View View)
    {
        editText=(EditText)findViewById(R.id.SendText);
        String SendMsg=editText.getText().toString();
        if(TextUtils.isEmpty(SendMsg))
        {
            return;
        }
        else {
            Message userMsg = new Message();
            userMsg.setMessage(SendMsg, 0);
            messagedata.add(userMsg);
            MyAdapt.update(messagedata, this);//更新Listview列表
            listview.setAdapter(MyAdapt);
            editText = (EditText) findViewById(R.id.SendText);
            editText.setText("");
            listview.setSelection(listview.getBottom());
            getSolution(SendMsg);
//            scoreMsg.setMessage("abc",4);
//            send_score();
        }
    }

//    public void send_score()
//    {
//
//        messagedata.add(scoreMsg);
//        MyAdapt.update(messagedata, this);//更新Listview列表
//        listview.setAdapter(MyAdapt);
//    }
    public void getSolution(String sentence){
        AndroidNetworking.get("http://47.112.216.3/model/autoLocateFault?sentence="+sentence.toString())
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            List<Solution_Data> sol_data=new ArrayList<>();
                            JSONObject data=response.getJSONObject("fault");
                            Message fault_info=new Message();
                             String fault_name =data.getString("fault_name");
                             fault_info.setMessage("您遇到的故障可能为"+fault_name,3);
                             messagedata.add(fault_info);
                             Message score_msg=new Message();
                             score_msg.setMessage("abc",4);
                             messagedata.add(score_msg);
                             Log.d("fault_name",fault_name.toString());
                             update_list();
                             JSONObject solution=response.getJSONObject("solution");
                             id=solution.getInt("solution_id");
                             JSONArray step=solution.getJSONArray("step_list");
                             Integer size=step.length();
                             sol_data.clear();
                             for(int i=0;i<size;i++)
                             {
                                 JSONObject solution_I= step.getJSONObject(i);
                                 String step_content=solution_I.getString("content");
                                 String link=solution_I.getString("picture_path");
                                 Log.d("link",link);
                                 Solution_Data step1 =new Solution_Data();
                                 step1.setStep(step_content,link,id);
                                 sol_data.add(step1);
                             }
                                 Sol_List.add(sol_data);

                             for(int i=0;i<Sol_List.size();i++)
                             {
                             Log.d("每一个解决方案的第一项",Sol_List.get(i).get(0).getSolutionDataContext());
                             }
                             //一条消息对应一个返回
                            Pos+=1;
                            pos=messagedata.size()-2;//从0开始
                            pos_to_Pos[pos]=Pos;

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
public void get_faultlist()
{
    AndroidNetworking.get("http://47.112.216.3//fault/getCommonFault")
            .setPriority(Priority.HIGH)
            .build()
            .getAsJSONObject(new JSONObjectRequestListener() {
                @Override
                public void onResponse(JSONObject response) {

                    try {
                        JSONArray fault_list=response.getJSONArray("data");
                        Integer size=fault_list.length();
                        for(int i=0;i<size;i++)
                        {
                            JSONObject fault= fault_list.getJSONObject(i);
                            int id=fault.getInt("common_fault_id");
                            String fault_name=fault.getString("fault_name");
//                            Log.d("link",link);
//                            Solution_Data step1 =new Solution_Data();
//                            step1.setStep(step_content,link,id);
//                            sol_data.add(step1);
                            faullt_list.put(id,fault_name);
                        }
                        for(Map.Entry<Integer,String> entry:faullt_list.entrySet())
                        {
                            Log.e("map",entry.getKey()+"  "+entry.getValue());
                        }
                        Message message1=new Message();
                        message1.setMessage("我是您的小助手鹏鹏，请问您遇到了以下问题吗？您也可以输入遇到的问题，我会为您解答。",2);
                        messagedata.add(message1);
                        update_list();
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

//public void get_secondlist(int fault_id)
//{
//    AndroidNetworking.get("http://47.112.216.3/fault/getStructureFaultById?faultId="+fault_id)
//            .setPriority(Priority.HIGH)
//            .build()
//            .getAsJSONObject(new JSONObjectRequestListener() {
//                @Override
//                public void onResponse(JSONObject response) {
//                    try {
//                        JSONArray second_list=response.getJSONArray("struct_fault_list");
//                        Integer size=second_list.length();
//                        Map<Integer,String> secondList=new HashMap<>();
//                        for(int i=0;i<size;i++)
//                        {
//                            JSONObject fault= second_list.getJSONObject(i);
//                            int id=fault.getInt("fault_id");
//                            String fault_name=fault.getString("fault_name");
////                            Log.d("link",link);
////                            Solution_Data step1 =new Solution_Data();
////                            step1.setStep(step_content,link,id);
////                            sol_data.add(step1);
//                            secondList.put(id,fault_name);
////                            total_map.put(id,fault_name);
//                        }
//                        second_map_list.put(fault_id+"",secondList);
//                        for(Map.Entry<Integer,String> entry:secondList.entrySet())
//                        {
//                            Log.e("map",entry.getKey()+"  "+entry.getValue());
//                        }
//                        Message message_second=new Message();
//                        message_second.setMessage(fault_id+"",5);
//                        messagedata.add(message_second);
//                        update_list();
//                    }
//                    catch (JSONException Ex){
//                        Ex.printStackTrace();
//                        Toast.makeText(MainActivity.this,"Data error!",Toast.LENGTH_SHORT).show();
//                    }
//                }
//
//                @Override
//                public void onError(ANError anError) {
//                    Toast.makeText(MainActivity.this,"Network error!", Toast.LENGTH_SHORT).show();
//                }
//            });
//}
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
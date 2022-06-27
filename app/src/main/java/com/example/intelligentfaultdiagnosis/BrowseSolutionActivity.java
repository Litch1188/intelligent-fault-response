package com.example.intelligentfaultdiagnosis;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Map;


//这是点击解决方案后的页面
public class BrowseSolutionActivity extends AppCompatActivity {
    private ListView listView;
    private Activity SolutionActivity;
    private SolutionAdapter SolAdapt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse_solution);
        listView=findViewById(R.id.solution_content_list);
        SolutionActivity=this;
        Intent intent=getIntent();
        int pos=intent.getIntExtra("Position",0);
        Log.d("拿到的item位置",pos+"");
        int pos_in_list=MainActivity.pos_to_Pos[pos];
        Log.d("取出的对应解决方案位置",pos_in_list+"");
//        SolAdapt.update(MainActivity.Sol_List.get(pos_in_list),this);
        Log.d("解决方案第一个",MainActivity.Sol_List.get(pos_in_list).get(0).getSolutionDataContext());
        SolAdapt=new SolutionAdapter(MainActivity.Sol_List.get(pos_in_list),this);
        listView.setAdapter(SolAdapt);
        ImageButton backButton = (ImageButton)findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View backbutton) {
                //结束当前Activity

                SolutionActivity.finish();
            }
        });



    }



}
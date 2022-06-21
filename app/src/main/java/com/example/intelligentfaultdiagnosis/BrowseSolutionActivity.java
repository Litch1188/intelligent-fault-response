package com.example.intelligentfaultdiagnosis;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.os.Bundle;
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
        SolAdapt=new SolutionAdapter(MainActivity.givedata(),this);
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
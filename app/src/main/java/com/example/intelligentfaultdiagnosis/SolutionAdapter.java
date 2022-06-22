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
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.List;
public class SolutionAdapter extends BaseAdapter{
    private List<Solution_Data> messagedata;
    private Context context;
    private TextView view;
    private ImageView img_view;
    public SolutionAdapter(List<Solution_Data> messagedata, Context context) {
        this.messagedata = messagedata;
        this.context = context;
    }

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
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.solution_item, parent, false);
        }
        if(messagedata.get(position).getSolutionDataContext()!=null)
        {
            view=convertView.findViewById(R.id.sol_textView);
            view.setText(messagedata.get(position).getSolutionDataContext());
        }
        if(messagedata.get(position).getSolutionImgLink()!=null)
        {
            img_view=convertView.findViewById(R.id.sol_imageView);
            Glide.with(img_view).load(messagedata.get(position).getSolutionImgLink()).into(img_view);
        }
        else
        {
            LinearLayout layout2=convertView.findViewById(R.id.sol_imageView);
            layout2.setVisibility(View.GONE);//隐藏
        }
        return convertView;
    }
}



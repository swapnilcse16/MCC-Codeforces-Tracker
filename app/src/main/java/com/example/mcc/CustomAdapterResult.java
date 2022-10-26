package com.example.mcc;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class CustomAdapterResult extends BaseAdapter {
    Context context;
    LayoutInflater inflater;
    List<Result> tempList;

    public CustomAdapterResult(Context context, List<Result> tempList) {
        this.context = context;
        this.tempList = tempList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return tempList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        final int i = position;
        if(view==null){
            inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view=inflater.inflate(R.layout.cutom_result,parent,false);
        }

        final TextView name = view.findViewById(R.id.nameRes);
        final TextView comment = view.findViewById(R.id.commentRes);
        final TextView accepted = view.findViewById(R.id.acceptedRes);
        final TextView point = view.findViewById(R.id.pointRes);
        final TextView cfSolved = view.findViewById(R.id.cfRes);
        final TextView rank = view.findViewById(R.id.positionRes);

        int POS = i+1;
        String s = POS + "";

        if(POS%10==1)   s = s + "st";
        else if(POS%10==2)  s = s + "nd";
        else if(POS%10==3)  s = s + "rd";
        else    s = s + "th";

        rank.setText(s);

        Result r = tempList.get(i);
        name.setText(r.getName());
        String cmnt= "N/A";
        if(!r.getComment().trim().equals(""))   cmnt = r.getComment().trim();
        comment.setText(cmnt);
        if(cmnt.equals("N/A")){
            comment.setTextColor(Color.rgb(0,0,0));
            comment.setTypeface(Typeface.DEFAULT);
        }
        accepted.setText(r.getSolved() + "");
        point.setText(r.getPoint()+"");
        cfSolved.setText(r.getCf()+"");
        return view;
    }
}

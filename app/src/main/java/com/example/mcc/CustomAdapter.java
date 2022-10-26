package com.example.mcc;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class CustomAdapter extends BaseAdapter {
    Context context;
    LayoutInflater inflater;
    List<Participant> tempList;

    public CustomAdapter(Context context, List<Participant> tempList) {
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
            view=inflater.inflate(R.layout.custom_cf,parent,false);
        }

        final TextView name = view.findViewById(R.id.nameCF);
        final TextView handle = view.findViewById(R.id.handleCF);
        final TextView rank = view.findViewById(R.id.rankCF);
        final TextView pos = view.findViewById(R.id.positionCF);

        int POS = i+1;
        String s = POS + "";

        if(POS%10==1)   s = s + "st";
        else if(POS%10==2)  s = s + "nd";
        else if(POS%10==3)  s = s + "rd";
        else    s = s + "th";

        pos.setText(s);
        name.setText(tempList.get(i).getName());
        handle.setText(tempList.get(i).getHandle());
        rank.setText(tempList.get(i).getGroup());

        name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://codeforces.com/submissions/" + tempList.get(i).getHandle()));
                context.startActivity(intent);
            }
        });

        handle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://codeforces.com/submissions/" + tempList.get(i).getHandle()));
                context.startActivity(intent);
            }
        });


        return view;
    }

}
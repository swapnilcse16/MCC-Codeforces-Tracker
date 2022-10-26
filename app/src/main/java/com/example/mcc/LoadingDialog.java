package com.example.mcc;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

public class LoadingDialog {
    Activity activity;
    AlertDialog dialog;
    String text;

    LoadingDialog(Activity myActivity, String t){
        activity = myActivity;
        text = t;
    }

    void startLoadingDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = activity.getLayoutInflater();
        View view = inflater.inflate(R.layout.custom_dialog, null);
        builder.setView(inflater.inflate(R.layout.custom_dialog, null));
        TextView t = view.findViewById(R.id.progressText);
        t.setText(text);
        builder.setCancelable(true);

        dialog = builder.create();
        dialog.show();
    }

    void dismissDialog(){
        dialog.dismiss();
    }
}
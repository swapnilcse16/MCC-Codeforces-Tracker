package com.example.mcc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    EditText id;
    Button log_in;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        id=findViewById(R.id.id);
        log_in=findViewById(R.id.log_in);
        progressBar=findViewById(R.id.progressBar);

        SharedPreferences sharedPreferences=getSharedPreferences("rememberFile",MODE_PRIVATE);
        String remeber =sharedPreferences.getString("nid_login","123");

        /*if(remeber == null){

        }*/
        if (remeber.equals("123"))
        {

        }
        else if(remeber.equals("NULL"))
        {

        }
        else
        {
            id.setText(remeber);
        }



        log_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String id_s=id.getText().toString();

                if (id_s.isEmpty())
                {
                    Toast.makeText(getApplicationContext(),"Enter Handle", Toast.LENGTH_LONG).show();
                }
                else
                {
                    progressBar.setVisibility(View.VISIBLE);
                    final String str = id_s;

                    DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference("Participants");
                    rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot snapshot) {
                            if (snapshot.hasChild(str)) {
                                String group=snapshot.child(str).child("group").getValue().toString();
                                String handle=snapshot.child(str).child("handle").getValue().toString();
                                String name=snapshot.child(str).child("name").getValue().toString();
                                FuncVar.GROUP = group;
                                FuncVar.HANDLE = handle;
                                FuncVar.NAME = name;

                                Intent intent=new Intent(MainActivity.this,Home.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                SharedPreferences sharedPreferences3=getSharedPreferences("rememberFile",MODE_PRIVATE);
                                SharedPreferences.Editor editor2=sharedPreferences3.edit();
                                editor2.putString("nid_login",str);
                                editor2.apply();
                                progressBar.setVisibility(View.GONE);
                                startActivity(intent);
                            }
                            else
                            {
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(getApplicationContext(),"Invalid login credentials",Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }


            }
        });

    }
}
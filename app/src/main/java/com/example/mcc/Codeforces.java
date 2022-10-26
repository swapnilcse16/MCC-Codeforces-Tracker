package com.example.mcc;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class Codeforces extends Fragment {

    Spinner grpSp;
    ListView lView;
    HashMap<String, String> grpFlag;
    HashMap<String, ArrayList<String>> participants, handles;
    LoadingDialog loadingDialog;
    ArrayList<String> groups;
    LinearLayout linLyt;
    int COUNT = 0;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.codeforces, container, false);

        grpSp = view.findViewById(R.id.grpCF);
        lView = view.findViewById(R.id.lViewCF);

        grpFlag = new HashMap<>();
        participants = new HashMap<>();
        participants = new HashMap<>();
        handles = new HashMap<>();
        groups = new ArrayList<>();
        loadingDialog = new LoadingDialog(getActivity(), "Loading");
        linLyt = view.findViewById(R.id.linLyt1);
        linLyt.setVisibility(View.INVISIBLE);
        COUNT = 0;
        loadingDialog.startLoadingDialog();

        retrieveGroups();

        grpSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                setListView();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return view;
    }

    void retrieveGroups(){
        DatabaseReference d = FirebaseDatabase.getInstance().getReference("Participants");
        d.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot i: dataSnapshot.getChildren()){
                    Participant p = i.getValue(Participant.class);
                    String g = p.getGroup();
                    String n = p.getName();
                    String h = p.getHandle();

                    String flag = grpFlag.get(g);
                    if(flag==null){
                        groups.add("  " + g);
                        participants.put(g, new ArrayList<String>());
                        handles.put(g, new ArrayList<String>());
                        participants.get(g).add(n);
                        handles.get(g).add(h);
                        grpFlag.put(g, "taken");

                    }
                    else{
                        participants.get(g).add(n);
                        handles.get(g).add(h);
                    }

                }

                Collections.sort(groups);
                ArrayAdapter<String> adapterName = new ArrayAdapter<>(
                        getActivity(),
                        R.layout.custom_spinner,
                        groups
                );
                grpSp.setAdapter(adapterName);
                int len = groups.size();
                for(int k=0; k<len; k++){
                    String gp = groups.get(k).trim();
                    if(gp.equals(FuncVar.GROUP)){
                        grpSp.setSelection(k);
                        break;
                    }
                }
                setListView();
                try{
                    loadingDialog.dismissDialog();
                }catch(Exception e){}
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    void setListView(){
        COUNT++;
        if(COUNT < 2)  return;
        if(COUNT%2==1)  return;
        linLyt.setVisibility(View.INVISIBLE);
        try{
            COUNT++;
            String g = grpSp.getSelectedItem().toString().trim();
            int len = handles.get(g).size();
            String h[]= new String[len];
            for(int i=0; i<len; i++)    h[i] = handles.get(g).get(i);
            new FetchNews(getActivity(), lView, participants.get(g), grpSp.getSelectedItem().toString().trim(), linLyt).execute(h);
        }catch(Exception e){
            toast(e.toString());
        }
    }

    void toast(String s){
        Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
    }
}

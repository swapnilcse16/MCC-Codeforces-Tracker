package com.example.mcc;

import android.os.Bundle;
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

public class EventResult extends Fragment {

    Spinner grpSp, eventSp;
    ListView lView;

    ArrayList<String> groups;
    HashMap<String, String> grpFlag;
    ArrayList<Result> allResults;
    LinearLayout linearLayout;

    LoadingDialog load;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.events, container, false);

        grpSp = view.findViewById(R.id.grpEv);
        eventSp = view.findViewById(R.id.eventEv);
        lView = view.findViewById(R.id.lViewEv);
        linearLayout = view.findViewById(R.id.linLytRes);
        load = new LoadingDialog(getActivity(), "Loading...");
        load.startLoadingDialog();

        linearLayout.setVisibility(View.INVISIBLE);

        grpFlag = new HashMap<>();
        groups = new ArrayList<>();
        allResults = new ArrayList<>();

        retrieveAll();

        grpSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(groups.size()==0)  return;
                setEvents();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        eventSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(groups.size()==0)  return;
                setResult();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return view;
    }

    void retrieveAll(){
        DatabaseReference d = FirebaseDatabase.getInstance().getReference("Results");
        d.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot i: snapshot.getChildren()){
                    Result r = i.getValue(Result.class);
                    if(r.getRank()==Integer.MAX_VALUE)  continue;
                    allResults.add(r);
                    String g = r.getGroup().trim();

                    String gFlag = grpFlag.get(g);
                    if(gFlag==null){
                        groups.add(" " + g);
                        grpFlag.put(g, "taken");
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
                linearLayout.setVisibility(View.VISIBLE);
                load.dismissDialog();
                setEvents();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    void setEvents(){
        if(groups.size()==0)    return;
        String g = grpSp.getSelectedItem().toString().trim();
        HashMap<String, String> evFlag = new HashMap<>();
        ArrayList<String>events = new ArrayList<>();
        int len = allResults.size();
        for(int i=0; i<len; i++){
            Result r = allResults.get(i);
            String rG = r.getGroup().trim();
            String ev = r.getEvent().trim();
            if(!rG.equals(g))   continue;
            String eFlag = evFlag.get(ev);
            if(eFlag==null){
                events.add(" " + ev);
                evFlag.put(ev,"taken");
            }
        }
        Collections.sort(events);
        ArrayAdapter<String> adapterName = new ArrayAdapter<>(
                getActivity(),
                R.layout.custom_spinner,
                events
        );
        eventSp.setAdapter(adapterName);
        setResult();
    }

    void setResult(){
        String grp = grpSp.getSelectedItem().toString().trim();
        String ev = eventSp.getSelectedItem().toString().trim();

        int len = allResults.size();
        ArrayList<Result> temp = new ArrayList<>();
        for(int i=0; i<len; i++){
            Result r = allResults.get(i);
            if(r.getGroup().equals(grp) && r.getEvent().equals(ev)){
                temp.add(r);
            }
        }

        Collections.sort(temp, new ResultComparator());
        CustomAdapterResult c = new CustomAdapterResult(getActivity(), temp);
        lView.setAdapter(c);
    }
}

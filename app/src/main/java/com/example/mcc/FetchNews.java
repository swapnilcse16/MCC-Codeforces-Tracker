package com.example.mcc;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;


public class FetchNews extends AsyncTask<String, Void, String> {

    Context myContext;
    int arrSize;
    ListView listView;
    ArrayList<String> names;
    ArrayList<Participant> results;
    String group;
    LinearLayout linearLayout;
    LoadingDialog ld;
    int count;

    public FetchNews(Context context, ListView l, ArrayList<String> n, String g, LinearLayout ll) {
        this.myContext = context;
        names = n;
        listView = l;
        results = new ArrayList<>();
        count = 0;
        group = g;
        linearLayout = ll;
        ld = new LoadingDialog((Activity) context, "Loading");
        ld.startLoadingDialog();
    }

    @Override
    protected String doInBackground(String... strings) {

        for(int h=0; h<strings.length; h++){
            count = 0;
            Log.i("Handle0000s", strings[h]);
            String s = NetworkUtils.getNews(strings[h]);
            try {
                Log.i("Inner", "Cry Inner");
                JSONObject jsonObject = new JSONObject(s);
                String status = jsonObject.getString("status");
                JSONArray resultArray = jsonObject.getJSONArray("result");
                int i = 0;
                arrSize = resultArray.length();
                HashMap<String, String> hm = new HashMap<>();

                while (i < resultArray.length() ) {
                    JSONObject info = resultArray.getJSONObject(i);
                    try {
                        JSONObject problem = info.getJSONObject("problem");
                        String verdict = info.getString("verdict");
                        String probNo = problem.getString("contestId") + problem.getString("index");

                        String x = strings[h] + " " + probNo + " " + verdict;
                        if(verdict.equals("OK")){
                            String taken = hm.get(x);
                            if(taken==null){
                                count++;
                                hm.put(x, "taken");
                            }
                        }

                    } catch (Exception e) {
                        Log.i("Inner", "Cry Inner");
                        e.printStackTrace();
                    }
                    i++;
                }
                Participant pp = new Participant(count+"", names.get(h), strings[h]);
                results.add(pp);

            } catch (JSONException e) {
                Log.i("Outer", "Cry Outer");
                e.printStackTrace();
            }
            //if(h==10)    break;
        }

        Collections.sort(results, new ParticipantComparator());
        return "";
    }

    @Override
    protected void onPostExecute(String s) {
        CustomAdapter c = new CustomAdapter(myContext, results);
        listView.setAdapter(c);
        linearLayout.setVisibility(View.VISIBLE);
        try{
            ld.dismissDialog();
        }catch (Exception e){

        }
    }
}

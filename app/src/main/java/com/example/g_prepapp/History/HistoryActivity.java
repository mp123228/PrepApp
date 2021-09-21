package com.example.g_prepapp.History;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.g_prepapp.Common.Util;
import com.example.g_prepapp.JSONParser;
import com.example.g_prepapp.MainActivity;
import com.example.g_prepapp.Category.MainRoomActivity;
import com.example.g_prepapp.R;
import com.example.g_prepapp.my.JsonParser;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {

    RecyclerView recy;
    ProgressDialog pDialog;
    JSONArray array=null;
    ArrayList<HistoryModel> slist;
    JsonParser sjsp=new JsonParser();
    JSONParser json=new JSONParser();
    String uid="",mode="",category="",skip="",score="",date="";
    HistoryAdp adp;
    SharedPreferences ss;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        ss=getSharedPreferences(MainActivity.session_data,MODE_PRIVATE);
        uid=ss.getString("uid","");

        recy=findViewById(R.id.recyh);
        new fetch_category().execute();

    }

    class fetch_category extends AsyncTask<String,String,String>
    {
        protected void onPreExecute()
        {
            super.onPreExecute();
            pDialog=new ProgressDialog(HistoryActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {

            slist=new ArrayList<HistoryModel>();
            List<NameValuePair> params=new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("uid",uid));

            try
            {

                JSONObject obj=json.makeHttpRequest(Util.historyFetch_Url,"GET",params);
                array=obj.getJSONArray(HistoryTags.Tag_array);

                for(int i=0;i< array.length();i++)
                {

                    JSONObject o=array.getJSONObject(i);
                    uid=o.getString(HistoryTags.Tag_uid);
                    mode=o.getString(HistoryTags.Tag_mode);
                    category=o.getString(HistoryTags.Tag_category);
                    skip=o.getString(HistoryTags.Tag_skip);
                    score=o.getString(HistoryTags.Tag_score);
                    date=o.getString(HistoryTags.Tag_date);

                    slist.add(new HistoryModel(uid,mode,category,skip,score,date));

                }
            }
            catch (Exception e)
            {
                Log.e("Exception is:",e+"");
            }

            return null;
        }
        protected void onPostExecute(String file_url)
        {
            pDialog.dismiss();
            Log.e("data is-",slist.toString());
            if(slist.isEmpty())
            {
                Toast.makeText(HistoryActivity.this, "history empty", Toast.LENGTH_SHORT).show();
            }
            else
            {

                adp=new HistoryAdp(slist,HistoryActivity.this);
                recy.setAdapter(adp);
                recy.setLayoutManager(new LinearLayoutManager(HistoryActivity.this));

            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        startActivity(new Intent(HistoryActivity.this, MainRoomActivity.class));
    }
}
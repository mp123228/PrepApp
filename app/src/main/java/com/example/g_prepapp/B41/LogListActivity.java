package com.example.g_prepapp.B41;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.g_prepapp.Common.Util;
import com.example.g_prepapp.DBhelper;
import com.example.g_prepapp.JSONParser;
import com.example.g_prepapp.LoginActivity;
import com.example.g_prepapp.R;
import com.example.g_prepapp.my.JsonParser;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LogListActivity extends AppCompatActivity {


    RecyclerView recylog;
    ProgressDialog pDialog;
    ArrayList<AuthModel> atlist;
    JsonParser jsp=new JsonParser();
    JSONParser json=new JSONParser();

    JSONArray array=null;
    String user,dayname,monthname,date,time,loginout;
    SharedPreferences ses=null;
    public static final String session_data="data";
    String uid="";

    DBhelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_list);

        db=new DBhelper(LogListActivity.this);
        recylog=findViewById(R.id.recyloglist);
        ses=getSharedPreferences(session_data,MODE_PRIVATE);
        uid=ses.getString("uid",null);



//        Cursor cr=db.getdetails();
//        if(cr.getCount()==0)
//        {
//            Toast.makeText(LogListActivity.this, "record not found", Toast.LENGTH_SHORT).show();
//        }
//        else
//        {
//            cr.moveToFirst();
//            ArrayList<AuthModel> model=new ArrayList<>();
//
//            do {
//                model.add(new AuthModel(cr.getString(1),cr.getString(2),cr.getString(3),cr.getString(4),cr.getString(5),cr.getString(6)));
//            }while(cr.moveToNext());
//
//
//            Collections.reverse(model);
//            AttempAdp atadp=new AttempAdp(model,LogListActivity.this);
//            Log.e("data is-=",model.toString());
//            recylog.setAdapter(atadp);
//            LinearLayoutManager linlaout=new LinearLayoutManager(LogListActivity.this);
////            linlaout.setReverseLayout(true);
////            linlaout.setStackFromEnd(true);
//
//            recylog.setLayoutManager(linlaout);
//        }

        new fetch_attemp().execute();


    }

    class fetch_attemp extends AsyncTask<String,String,String>
    {
        protected void onPreExecute()
        {
            super.onPreExecute();
            pDialog=new ProgressDialog(LogListActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }
        @Override
        protected String doInBackground(String... strings) {
            atlist=new ArrayList<>();

            List<NameValuePair> params=new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("userid",uid));

            try
            {
                JSONObject obj=json.makeHttpRequest(Util.attemptfetch_Url,"GET",params);

                array=obj.getJSONArray(AttempTags.Tag_array);
                for(int i=0;i< array.length();i++)
                {

                    JSONObject o=array.getJSONObject(i);
                    user =o.getString(AttempTags.Tag_User);
                    dayname=(o.getString(AttempTags.Tag_dayname));
                    monthname=(o.getString(AttempTags.Tag_monthname));
                    date=(o.getString(AttempTags.Tag_logdate));
                    time=(o.getString(AttempTags.Tag_time));
                    loginout=(o.getString(AttempTags.Tag_log_in_out));
                    atlist.add(new AuthModel(user,dayname,monthname,date,time,loginout));

                    Log.e("User:", user);

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
            //reverese list
            Collections.reverse(atlist);
            AttempAdp atadp=new AttempAdp(atlist,LogListActivity.this);
            Log.e("data is-=",atlist.toString());
            recylog.setAdapter(atadp);
            LinearLayoutManager linlaout=new LinearLayoutManager(LogListActivity.this);
//            linlaout.setReverseLayout(true);
//            linlaout.setStackFromEnd(true);

            recylog.setLayoutManager(linlaout);
        }
    }
}
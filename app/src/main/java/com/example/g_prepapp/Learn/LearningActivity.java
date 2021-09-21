package com.example.g_prepapp.Learn;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.g_prepapp.Common.Util;
import com.example.g_prepapp.DBhelper;
import com.example.g_prepapp.Dbhelper2;
import com.example.g_prepapp.History.HistoryActivity;
import com.example.g_prepapp.History.HistoryAdp;
import com.example.g_prepapp.History.HistoryModel;
import com.example.g_prepapp.History.HistoryTags;
import com.example.g_prepapp.JSONParser;
import com.example.g_prepapp.R;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class LearningActivity extends AppCompatActivity
{

    String category="";
    ProgressDialog pDialog;
    ArrayList<LearningModel> slist;
    JSONParser json=new JSONParser();
    JSONArray array=null;
    String id="",question="",ans="";
    LearningAdp adp;
    RecyclerView recylearn;

    Dbhelper2 db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learning2);

        recylearn=findViewById(R.id.learningrecy);
        Intent intent=getIntent();

        db=new Dbhelper2(LearningActivity.this);

        if(intent!=null)
        {

            category=intent.getStringExtra("lcategory");


            Toast.makeText(LearningActivity.this, category.toString(), Toast.LENGTH_SHORT).show();
            slist=new ArrayList<>();

            Cursor c = db.getlearningdetails(category);

            if(c.getCount()==0)
            {
                Toast.makeText(LearningActivity.this, "record not found", Toast.LENGTH_SHORT).show();
            }
            else
            {
                c.moveToFirst();
                do
                    {
                        slist.add(new LearningModel(c.getString(1).toString(),c.getString(2),c.getString(3)));

                }while(c.moveToNext());


                adp=new LearningAdp(LearningActivity.this,slist);
                recylearn.setAdapter(adp);
                recylearn.setLayoutManager(new LinearLayoutManager(LearningActivity.this));



            }




            //new fetch_learn_category().execute();

        }
        else
        {
            Toast.makeText(this, "data not available here!", Toast.LENGTH_SHORT).show();
        }

    }

    class fetch_learn_category extends AsyncTask<String,String,String>
    {
        protected void onPreExecute()
        {
            super.onPreExecute();
            pDialog=new ProgressDialog(LearningActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {

            slist=new ArrayList<LearningModel>();
            List<NameValuePair> params=new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("category",category));

            try
            {

                JSONObject obj=json.makeHttpRequest(Util.learnQuestionAnsUrl,"GET",params);
                array=obj.getJSONArray(LearnTags.Tag_learn);

                for(int i=0;i< array.length();i++)
                {

                    JSONObject o=array.getJSONObject(i);
                    id=o.getString(LearnTags.Tag_id);
                    question=o.getString(LearnTags.Tag_ques);
                    ans=o.getString(LearnTags.Tag_answer);

                    slist.add(new LearningModel(id,question,ans));

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
                Toast.makeText(LearningActivity.this, "learning empty", Toast.LENGTH_SHORT).show();
            }
            else
            {

                adp=new LearningAdp(LearningActivity.this,slist);
                recylearn.setAdapter(adp);
                recylearn.setLayoutManager(new LinearLayoutManager(LearningActivity.this));

            }
        }
    }
}
package com.example.g_prepapp.Category;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.g_prepapp.B41.LogListActivity;
import com.example.g_prepapp.Common.Util;
import com.example.g_prepapp.DBhelper;
import com.example.g_prepapp.Dbhelper2;
import com.example.g_prepapp.History.HistoryActivity;
import com.example.g_prepapp.JSONParser;
import com.example.g_prepapp.Learn.LearnCategoryAdp;
import com.example.g_prepapp.Learn.LearnCateogoryModel;
import com.example.g_prepapp.Learn.LearnTags;
import com.example.g_prepapp.Learn.LearningActivity;
import com.example.g_prepapp.LoginActivity;
import com.example.g_prepapp.MainActivity;
import com.example.g_prepapp.ModelLearning;
import com.example.g_prepapp.R;
import com.example.g_prepapp.UpdateProfileActivity;
import com.example.g_prepapp.my.JsonParser;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainRoomActivity extends AppCompatActivity {

    public static Spinner sp;
    ArrayList<String> arr=new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;
    ArrayList<CategoryModel> slist=null;
    ArrayList<LearnCateogoryModel> slistlearn=null;
    ProgressDialog pDialog;
    String modeselection="";
    RecyclerView recy;
    JSONArray array=null;
    JsonParser sjsp=new JsonParser();
    String cat_name="",cat_learnname="";
    int cat_id=0;
    ProgressDialog pdialog;
    int cnt=0;

    public DBhelper db;
    public Dbhelper2 db2;


    String username="";
    JSONParser jsonp=new JSONParser();

    SharedPreferences ss=null;
    public static final String session_data="data";

    FloatingActionButton fbtn;
    RecyclerView recyread;
    String uid="";
    String dayname="",monthname="",date="",time="",loginout="";

    ArrayList<String> arraycat=new ArrayList<>();
    ArrayList<ModelLearning> arraylearn=new ArrayList<>();

    ModelLearning[] modellearn=new ModelLearning[10];


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_room);

        db=new DBhelper(MainRoomActivity.this);
        db2=new Dbhelper2(MainRoomActivity.this);


        ss=getSharedPreferences(session_data,MODE_PRIVATE);
        uid=ss.getString("uid",null);

        fbtn=findViewById(R.id.fbtn);
        fbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                startActivity(new Intent(MainRoomActivity.this, HistoryActivity.class));

            }
        });

        recy=findViewById(R.id.recy);
        recyread=findViewById(R.id.recyread);
        sp=findViewById(R.id.sp);
        arr.add("Easy");
        arr.add("Medium");
        arr.add("Hard");
        ArrayAdapter adp=new ArrayAdapter(MainRoomActivity.this, android.R.layout.simple_list_item_1,arr);
        adp.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        sp.setAdapter(adp);


        Cursor cl = db2.getleandetails();

        if(cl.getCount()==0)
        {

            arraylearn.add(new ModelLearning(1, "gk", " Which crop is sown on the largest area in India", "Rice"));
            arraylearn.add(new ModelLearning(2, "gk", "Entomology is the science that studies", "Insects"));
            arraylearn.add(new ModelLearning(3, "gk", "Grand Central Terminal, Park Avenue, New York is the world", "railway station"));
            arraylearn.add(new ModelLearning(5, "gk", "The world smallest country is", "Vatican city"));
            arraylearn.add(new ModelLearning(6, "cment", "The world smallest country is", "lawn tennis"));
            arraylearn.add(new ModelLearning(8, "cment", "Accounting is the process of matching", "Benifits and costs"));
            arraylearn.add(new ModelLearning(9, "net", "What is SWOT analysis?", "Strength, Weakness, Opportunity and Threat"));
            arraylearn.add(new ModelLearning(10, "net", "Which country is known as the world's sugar bowl", "Cuba"));


            for (int j = 0; j < arraylearn.size(); j++) {
                long roc = db2.setlearndetails(arraylearn.get(j).getId(), arraylearn.get(j).getCategory().toString(), arraylearn.get(j).getQuestion().toString(), arraylearn.get(j).getAns().toString());
                if (roc > 0) {
                    Toast.makeText(MainRoomActivity.this, "question submited", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainRoomActivity.this, "try again!", Toast.LENGTH_SHORT).show();
                }
            }
        }










        new fetch_category().execute();


        Cursor c1=db.getdetails();


        Cursor ct = db.getdetails();
if(cl.getCount()==0)
{

    arraycat.add("gk");
    arraycat.add("cment");
    arraycat.add("net");

    for (int i = 0; i < arraycat.size(); i++)
    {
        long rel = db.setdetails(arraycat.get(i).toString());
        if (rel > 0) {
            Toast.makeText(MainRoomActivity.this, "submited", Toast.LENGTH_SHORT).show();
        }
        else
            {
            Toast.makeText(MainRoomActivity.this, "try again!", Toast.LENGTH_SHORT).show();
            }
    }

}

            Cursor c = db.getdetails();
            if (c.getCount() == 0) {
                Toast.makeText(MainRoomActivity.this, "record not found!", Toast.LENGTH_SHORT).show();
            } else {

                slistlearn = new ArrayList<>();

                c.moveToFirst();
                do {

                    slistlearn.add(new LearnCateogoryModel(c.getString(1)));


                } while (c.moveToNext());

                LearnCategoryAdp adplearn = new LearnCategoryAdp(MainRoomActivity.this, slistlearn);
                Log.e("list learn data is-", slistlearn.toString());
                recyread.setAdapter(adplearn);
                recyread.setLayoutManager(new GridLayoutManager(MainRoomActivity.this, 2));

            }



        // new fetch_learn_category().execute();

    }

    class fetch_category extends AsyncTask<String,String,String>
    {
        protected void onPreExecute()
        {
            super.onPreExecute();
            pDialog=new ProgressDialog(MainRoomActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... strings)
        {
            slist=new ArrayList<>();
            try
            {
                JSONObject obj=sjsp.getJSONFromUrl(Util.categoryUrl);
                array=obj.getJSONArray(CategoryTags.Tag_array);
                for(int i=0;i< array.length();i++)
                {
                    JSONObject o=array.getJSONObject(i);
                    cat_id=Integer.parseInt(o.getString(CategoryTags.Tag_Catid));
                    cat_name=(o.getString(CategoryTags.Tag_Catname));
                    slist.add(new CategoryModel(cat_id,cat_name));

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
            CategoryAdp adp=new CategoryAdp(slist,MainRoomActivity.this);
            Log.e("data is-",slist.toString());
            recy.setAdapter(adp);
            recy.setLayoutManager(new GridLayoutManager(MainRoomActivity.this,2));
        }
    }

    class fetch_learn_category extends AsyncTask<String,String,String>
    {
        protected void onPreExecute()
        {

        }

        @Override
        protected String doInBackground(String... strings)
        {
            slistlearn=new ArrayList<>();
            try
            {
                JSONObject obj=sjsp.getJSONFromUrl(Util.learnUrl);
                array=obj.getJSONArray(LearnTags.Tag_learn);
                for(int i=0;i< array.length();i++)
                {
                    JSONObject o=array.getJSONObject(i);
                    cat_learnname=(o.getString(LearnTags.category));


                    slistlearn.add(new LearnCateogoryModel(cat_learnname));
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

            LearnCategoryAdp adplearn=new LearnCategoryAdp(MainRoomActivity.this,slistlearn);
            Log.e("list learn data is-",slistlearn.toString());
            recyread.setAdapter(adplearn);
            recyread.setLayoutManager(new GridLayoutManager(MainRoomActivity.this,2));

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

            onDestroy();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {

        MenuInflater inflater=new MenuInflater(MainRoomActivity.this);
        inflater.inflate(R.menu.optionmenu,menu);

        return true;

    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {

        switch (item.getItemId())
        {

            case R.id.idupdateprofile:

                startActivity(new Intent(MainRoomActivity.this, UpdateProfileActivity.class));

                break;

            case R.id.idlogout:

                SharedPreferences.Editor ses=ss.edit();
                username=ss.getString("username",null);
                ses.clear();
                ses.commit();
                Toast.makeText(this, "Logout Successfully", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainRoomActivity.this, LoginActivity.class));

                SimpleDateFormat sfd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                Date dm=new Date();
                String dd=sfd.format(dm);
                String[] splitString=dd.split(" ");
                date=splitString[0];
                time=splitString[1];
                Calendar c=Calendar.getInstance();
                c.setTime(dm);
                int dayofweek=dm.getMonth() + 1;
                Log.e("Day of week=",dayofweek+"");
                dayname = new SimpleDateFormat("EEEE").format(dm);
                getmonthname(dayofweek);
                loginout="logout";

                Toast.makeText(MainRoomActivity.this, "Email:"+username
                        +"\nPassword:"+"\ndayname:"+dayname+"" +
                        "\nmonthname:"+monthname+"\n" +
                        "Date="+date+"\n" +
                        "Time="+time+"\nloginout:" +loginout+
                        "", Toast.LENGTH_SHORT).show();

//                long res=db.setdetails(username,dayname,monthname,date,time,loginout);
//
//                if(res>0)
//                {
//                    startActivity(new  Intent(MainRoomActivity.this,LoginActivity.class));
//                    Toast.makeText(MainRoomActivity.this, "record submited logout", Toast.LENGTH_SHORT).show();
//                }
//                else
//                {
//                    Toast.makeText(MainRoomActivity.this, "record not submited", Toast.LENGTH_SHORT).show();
//                }

                 new logout_attemp().execute();

                 break;

            case R.id.atlist:

                startActivity(new Intent(MainRoomActivity.this,LogListActivity.class));

                break;

        }
        return true;
    }


    class logout_attemp extends AsyncTask<String,String,String>
    {

        protected  void onPreExecute()
        {
            super.onPreExecute();
            pdialog=new ProgressDialog(MainRoomActivity.this);
            pdialog.setMessage("please wait...");
            pdialog.setIndeterminate(false);
            pdialog.setCancelable(true);
            pdialog.show();
        }

        @Override
        protected String doInBackground(String... strings)
        {

            List<NameValuePair> params=new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("userid",uid));
            params.add(new BasicNameValuePair("user",username));
            params.add(new BasicNameValuePair("ddayname",dayname));
            params.add(new BasicNameValuePair("mmonthname",monthname));
            params.add(new BasicNameValuePair("llogdate",date));
            params.add(new BasicNameValuePair("ttime",time));
            params.add(new BasicNameValuePair("llog_in_out",loginout));

            try {

                JSONObject obj=jsonp.makeHttpRequest(Util.attemptUrl,"POST",params);
                int ans=obj.getInt("success");
                if(ans==1)
                {
                    cnt=1;
                }
                else
                {
                    cnt=0;
                }

            }
            catch (Exception e)
            {
                Log.e("Exception=++",e+"");
            }
            return null;
        }

        protected void onPostExecute(String file_url)
        {

            pdialog.dismiss();
            if(cnt==0)
            {
                Toast.makeText(MainRoomActivity.this, "Data not inserted!", Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(MainRoomActivity.this, "Data inserted.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void getmonthname(int month)
    {
        switch (month)
        {
            case 1:
                monthname="January";
                break;
            case 2:
                monthname="February";
                break;
            case 3:
                monthname="March";
                break;
            case 4:
                monthname="April";;
                break;
            case 5:
                monthname="May";;
                break;
            case 6:
                monthname="June";;

                break;
            case 7:
                monthname="July";;
                break;
            case 8:
                monthname="August";;
                break;
            case 9:
                monthname="Semptember";;

                break;
            case 10:
                monthname="Octomber";;

                break;
            case 11:
                monthname="November";;

                break;
            case 12:
                monthname="December";;

                break;

        }
    }
}
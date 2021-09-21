package com.example.g_prepapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.g_prepapp.Category.MainRoomActivity;
import com.example.g_prepapp.Common.Util;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class UpdateProfileActivity extends AppCompatActivity {


    EditText edname,eduser,edpass;
    SharedPreferences ss=null;
    public static final String session_data="data";
    ProgressDialog pdialog;
    String uid="";
    String Tag_array="users";
    Button btnupdate;
    JSONParser jsonp=new JSONParser();

    JSONArray array=null;
    String username="",password="";
    String Tag_user="user",Tag_name="name",Tag_identity="identity";
    String user="",name="",identity="";
    int cnt=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

        ss=getSharedPreferences(session_data,MODE_PRIVATE);
        uid=ss.getString("uid",null);

        edname=findViewById(R.id.name);
        edpass=findViewById(R.id.upass);
        eduser=findViewById(R.id.uname);
        btnupdate=findViewById(R.id.btnupdate);




        btnupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                username=eduser.getText().toString();
                name=eduser.getText().toString();
                password=edpass.getText().toString();

            new update_Profile().execute();

            }

            class update_Profile extends AsyncTask<String,String,String>
            {

                @Override
                protected void onPreExecute()
                {
                    super.onPreExecute();
                    pdialog=new ProgressDialog(UpdateProfileActivity.this);
                    pdialog.setMessage("please wait...");
                    pdialog.setIndeterminate(false);
                    pdialog.setCancelable(true);
                    pdialog.show();
                }

                @Override
                protected String doInBackground(String... strings)
                {
                    List<NameValuePair> params=new ArrayList<>();
                    params.add(new BasicNameValuePair("user",username));
                    params.add(new BasicNameValuePair("name",name));
                    params.add(new BasicNameValuePair("pass",password));
                    params.add(new BasicNameValuePair("uid",uid));


                    try {

                        JSONObject obj=jsonp.makeHttpRequest(Util.updateprofile,"POST",params);
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

                @Override
                protected void onPostExecute(String s) {
                    super.onPostExecute(s);
                    pdialog.dismiss();
                    if(cnt==1)
                    {

                        Toast.makeText(UpdateProfileActivity.this, "Record inserted", Toast.LENGTH_SHORT).show();
                        edname.setText("");
                        eduser.setText("");
                        edpass.setText("");
                        startActivity(new Intent(UpdateProfileActivity.this,MainRoomActivity.class));

                    }
                    else
                    {

                        Toast.makeText(UpdateProfileActivity.this, "Sorry Try Again", Toast.LENGTH_SHORT).show();

                    }

                }
            }
        });

        new fetchuserdata().execute();


    }

    class fetchuserdata extends AsyncTask<String,String,String>
    {
        protected  void onPreExecute()
        {
            super.onPreExecute();
            pdialog=new ProgressDialog(UpdateProfileActivity.this);
            pdialog.setMessage("please wait...");
            pdialog.setIndeterminate(false);
            pdialog.setCancelable(true);
            pdialog.show();
        }

        @Override
        protected String doInBackground(String... strings)
        {
            List<NameValuePair> params=new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("uid",uid));
            try
            {
                JSONObject obj=jsonp.makeHttpRequest(Util.fetch_updateprofile,"GET",params);
                array=obj.getJSONArray(Tag_array);
                int ans=obj.getInt("success");
                if(ans==1)
                {
                    cnt=1;
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject ob = array.getJSONObject(i);
                        name = ob.getString(Tag_name);
                        user=ob.getString(Tag_user);
                        identity=ob.getString(Tag_identity);

                    }
                }
                else {
                    cnt=0;
                }
            }
            catch (Exception e)
            {

            }
            return null;
        }

        protected void onPostExecute(String file_url)
        {

            pdialog.dismiss();
            if(cnt==0)
            {
                Toast.makeText(UpdateProfileActivity.this, "register detail not found!", Toast.LENGTH_SHORT).show();

            }
            else
            {
                edname.setText(name);
                eduser.setText(user);
                edpass.setText(identity);
            }

        }
    }

}
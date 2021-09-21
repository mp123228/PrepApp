package com.example.g_prepapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.g_prepapp.B41.AuthModel;
import com.example.g_prepapp.Category.MainRoomActivity;
import com.example.g_prepapp.Common.Util;
import com.example.g_prepapp.JSONParser;
import com.example.g_prepapp.R;
import com.example.g_prepapp.RegisterActivity;
import com.example.g_prepapp.my.JsonParser;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class LoginActivity extends AppCompatActivity
{

    Button btnlog;
    EditText eduser,edpass;
    TextView tvregister;
    int cnt=0;
    String username=null,password=null,uid="";
    String Tag_array="users",user="";
    String Tag_uid="uid",Tag_user="user";
    ProgressDialog pdialog=null;
    JSONParser jsonp=new JSONParser();

    JSONArray array=null;
    JsonParser sjsp=new JsonParser();

    SharedPreferences ses=null;
    public static final String session_data="data";
    String dayname="",monthname="",date="",time="",loginout="";

    String captcha="";
    int idx=0,idx2=0,idx3=0;
    char[] escapeChars = { '<', '(', '[', '{', '\\', '^', '-', '=', '$', '!', '|', ']', '}', ')', '?', '*', '+', '.', '>' };
    int rno=0;
    String specialchar="";
    Random random=new Random();
    TextView txcaptcha;
    EditText edcaptcha;
    boolean T=true;
    String edcap="";

    DBhelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        db=new DBhelper(LoginActivity.this);
        ses=getSharedPreferences(session_data,MODE_PRIVATE);

        txcaptcha=findViewById(R.id.txcaptcha);
        edcaptcha=findViewById(R.id.edcaptcha);
        idx=random.nextInt(6);
        idx3=random.nextInt(6);

        while (T) {
            if (idx == idx3) {
                idx3 = random.nextInt(6);
            }
            else
            {
                T=false;
            }
        }

        Log.e("idx=",idx+"");
        Log.e("idx3=",idx3+"");
        idx2=random.nextInt(19);
        specialchar= String.valueOf(escapeChars[idx2]);
        rno=random.nextInt(10);

        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 6;
        Random random = new Random();

        StringBuilder buffer = new StringBuilder(targetStringLength);
        for (int i = 0; i < targetStringLength; i++)
        {
            int randomLimitedInt = leftLimit + (int) (random.nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append((char) randomLimitedInt);

            if (i == 5)
            {

                String generatedString = buffer.toString();

                captcha = generatedString;
            }
        }
        //update captcha
        for(int j=0;j<captcha.length();j++)
        {
            if(idx==j)
            {
                captcha=captcha.replace(captcha.charAt(j)+"",String.valueOf(rno));
            }

            if(idx3==j)
            {
                captcha= captcha.replace(captcha.charAt(j)+"",specialchar);
            }
        }

        txcaptcha.setText(captcha);

        Toast.makeText(this, "Newcaptch="+captcha+"", Toast.LENGTH_SHORT).show();



        tvregister=findViewById(R.id.tvregister);
        eduser=findViewById(R.id.eduser);
        edpass=findViewById(R.id.edpass);
        btnlog=findViewById(R.id.btnlogin);
        tvregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));

            }
        });

        btnlog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                edcap=edcaptcha.getText().toString();
                if(edcap.equals(captcha))
                {

                    username = eduser.getText().toString();
                    password = edpass.getText().toString();

                    SimpleDateFormat sfd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                    Date dm = new Date();
                    String dd = sfd.format(dm);
                    String[] splitString = dd.split(" ");
                    date = splitString[0];
                    time = splitString[1];
                    Calendar c = Calendar.getInstance();
                    c.setTime(dm);
                    int dayofweek = dm.getMonth() + 1;
                    Log.e("Day of week=", dayofweek + "");
                    dayname = new SimpleDateFormat("EEEE").format(dm);
                    getmonthname(dayofweek);
                    loginout = "login";

//                Toast.makeText(LoginActivity.this, "Email:"+username
//                        +"\nPassword:"+password+"\ndayname:"+dayname+"" +
//                        "\nmonthname:"+monthname+"\n" +
//                        "Date="+date+"\n" +
//                        "Time="+time+"\nloginout:" +loginout+
//                        "", Toast.LENGTH_SHORT).show();






//                    long res=db.setdetails(username,dayname,monthname,date,time,loginout);
//                    if(res>0)
//                    {
//                        startActivity(new Intent(LoginActivity.this,MainRoomActivity.class));
//                        Toast.makeText(LoginActivity.this, "record submited", Toast.LENGTH_SHORT).show();
//                    }
//                    else
//                    {
//                        Toast.makeText(LoginActivity.this, "record not submited", Toast.LENGTH_SHORT).show();
//                    }


                   new c_login().execute();

                }
                else
                {
                    AlertDialog.Builder alert=new AlertDialog.Builder(LoginActivity.this);
                    alert.setMessage("captcha mismatch try again!");
                    alert.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });


                    alert.show();

                }

            }

            class c_login extends AsyncTask<String,String,String>
            {
                protected  void onPreExecute()
                {
                    super.onPreExecute();
                    pdialog=new ProgressDialog(LoginActivity.this);
                    pdialog.setMessage("please wait...");
                    pdialog.setIndeterminate(false);
                    pdialog.setCancelable(true);
                    pdialog.show();
                }

                @Override
                protected String doInBackground(String... strings)
                {
                    List<NameValuePair> params=new ArrayList<NameValuePair>();
                    params.add(new BasicNameValuePair("user",username));
                    params.add(new BasicNameValuePair("password",password));
                    try
                    {
                        JSONObject obj=jsonp.makeHttpRequest(Util.loginUrl,"GET",params);
                        array=obj.getJSONArray(Tag_array);
                        int ans=obj.getInt("success");

                        if(ans==1)
                        {
                            cnt=1;
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject ob = array.getJSONObject(i);
                                uid = ob.getString(Tag_uid);
                                user=ob.getString(Tag_user);
                            }
                        }
                        else
                        {
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
                        Toast.makeText(LoginActivity.this, "please register you Username!", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        idx=0;
                        idx3=0;
                        rno=0;
                        SharedPreferences.Editor obj=ses.edit();
                        obj.putString("uid", uid);
                        obj.putString("username",user);
                        obj.commit();
                        Intent i = new Intent(LoginActivity.this, MainRoomActivity.class);
                        startActivity(i);
                        new login_attemp().execute();
                        Toast.makeText(LoginActivity.this, "data store in session+="+uid, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }

    class login_attemp extends AsyncTask<String,String,String>
    {

        protected  void onPreExecute()
        {
            super.onPreExecute();
            pdialog=new ProgressDialog(LoginActivity.this);
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
                Toast.makeText(LoginActivity.this, "Data not inserted!", Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(LoginActivity.this, "Data inserted.", Toast.LENGTH_SHORT).show();
                finish();
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
                monthname="April";
                break;
            case 5:
                monthname="May";
                break;
            case 6:
                monthname="June";

                break;
            case 7:
                monthname="July";
                break;
            case 8:
                monthname="August";
                break;
            case 9:
                monthname="Semptember";

                break;
            case 10:
                monthname="Octomber";

                break;
            case 11:
                monthname="November";

                break;
            case 12:
                monthname="December";

                break;

        }

    }
}
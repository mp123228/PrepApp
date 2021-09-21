package com.example.g_prepapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.g_prepapp.Common.Util;
import com.example.g_prepapp.my.JsonParser;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RegisterActivity extends AppCompatActivity
{

    EditText edname,eduser,edpass,secans;
    Button btnreg;
    ProgressDialog pdialog=null;
    int cnt=0;
    String uid="",name="",user="",security="",seque="",security_ans = "";
    JSONParser jsonp=new JSONParser();
    JsonParser sjsp=new JsonParser();
    int no=0;
    String bdate="";
    ArrayList<String> arr=new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        eduser=findViewById(R.id.uname);
        edname=findViewById(R.id.name);
        edpass=findViewById(R.id.upass);
        btnreg=findViewById(R.id.btnupdate);
        secans=findViewById(R.id.secans);


        //        Add Security Questions ?
        arr.add("What was your childhood nickname?");
        arr.add("In what city does your nearest sibling live?");
        arr.add("In what city or town was your first job?");
        arr.add("What was your favorite place to visit as a child?");
        arr.add("What was your dream job ?");

        Spinner seq = findViewById(R.id.spinner_ques);

        ArrayAdapter adp=new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1,arr);
        adp.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        seq.setAdapter(adp);

        btnreg.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {

                int leftLimit = 97; // letter 'a'
                int rightLimit = 122; // letter 'z'
                int targetStringLength = 10;
                Random random = new Random();
                StringBuilder buffer = new StringBuilder(targetStringLength);
                for (int i = 0; i < targetStringLength; i++)
                {
                    int randomLimitedInt = leftLimit + (int) (random.nextFloat() * (rightLimit - leftLimit + 1));
                    buffer.append((char) randomLimitedInt);

                    if (i == 9) {
                        String generatedString = buffer.toString();

                        uid = generatedString;
                    }

                }

                name=edname.getText().toString();
                user=eduser.getText().toString();
                security=edpass.getText().toString();
                seque= seq.getSelectedItem().toString();
                security_ans = secans.getText().toString();

                new Insert_record().execute();
            }

            class Insert_record extends AsyncTask<String,String,String>
            {

                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                    pdialog=new ProgressDialog(RegisterActivity.this);
                    pdialog.setMessage("please wait...");
                    pdialog.setIndeterminate(false);
                    pdialog.setCancelable(true);
                    pdialog.show();
                }

                @Override
                protected String doInBackground(String... strings) {
                    List<NameValuePair> params=new ArrayList<>();
                    params.add(new BasicNameValuePair("uid",uid));
                    params.add(new BasicNameValuePair("name",name));
                    params.add(new BasicNameValuePair("user",user));
                    params.add(new BasicNameValuePair("pass",security));
                    params.add(new BasicNameValuePair("security",seque));
                    params.add(new BasicNameValuePair("ans",security_ans));

                    try {

                        JSONObject obj=jsonp.makeHttpRequest(Util.registerUrl,"POST",params);
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

                        Toast.makeText(RegisterActivity.this, "Record inserted", Toast.LENGTH_SHORT).show();
                        edname.setText("");
                        eduser.setText("");
                        edpass.setText("");
                        startActivity(new Intent(RegisterActivity.this,LoginActivity.class));

                    }
                    else
                    {

                        Toast.makeText(RegisterActivity.this, "Sorry Try Again", Toast.LENGTH_SHORT).show();

                    }

                }
            }
        });

    }
}
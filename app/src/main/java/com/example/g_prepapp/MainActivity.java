package com.example.g_prepapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.g_prepapp.Category.MainRoomActivity;

public class MainActivity extends AppCompatActivity {

    SharedPreferences ses=null;
    public static final String session_data="data";
    public static String uid=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        ses=getSharedPreferences(session_data,MODE_PRIVATE);
        uid=ses.getString("uid",null);

        Thread th=new Thread()
        {
            public void run()
            {
                try
                {
                    Thread.sleep(2000);
                }
                catch (Exception e)
                {

                }
                finally
                {

                    if(uid==null)
                    {
                        Intent i = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(i);
                    }
                    else
                    {
                        Intent i = new Intent(MainActivity.this, MainRoomActivity.class);
                        startActivity(i);
                    }

                }
            }
        };
        th.start();


    }
}
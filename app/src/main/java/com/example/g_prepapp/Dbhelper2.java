package com.example.g_prepapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Dbhelper2 extends SQLiteOpenHelper {

    public static final String dbname="mylearn.db";



    public Dbhelper2(@Nullable Context context)
    {
        super(context, dbname, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {

        String querylearn="create table tablelearn(lid integer primary key ,category text,question text,ans text)";
        db.execSQL(querylearn);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("drop table if exists tablelearn");
        onCreate(db);
    }


    public long setlearndetails(int id,String catname,String qes,String an)
    {
        SQLiteDatabase db1=this.getWritableDatabase();
        ContentValues cv1=new ContentValues();
        cv1.put("lid",id);
        cv1.put("category",catname);
        cv1.put("question",qes);
        cv1.put("ans",an);

        long result=db1.insert("tablelearn",null,cv1);

        return result;
    }


    public Cursor getleandetails()
    {
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor c=db.rawQuery("select *from tablelearn",null);
        return c;
    }

    public Cursor getlearningdetails(String cat)
    {

        String cate=cat;
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor ck=db.rawQuery("select *from tablelearn where category='"+cate+"'",null);
        return ck;

    }
}

package com.example.g_prepapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBhelper extends SQLiteOpenHelper {

    public static final String dbname="mylogin.db";


    public DBhelper(@Nullable Context context) {
        super(context, dbname, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String query="create table cattable(cid integer primary key AUTOINCREMENT ,catename text)";

        String querylearn="create table tablelearn(lid integer primary key ,category text,question text,ans text)";

        db.execSQL(query);
        db.execSQL(querylearn);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("drop table if exists cattable");
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

    public Cursor getlearningdetails(String cat)
    {
        String cate=cat;
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor c=db.rawQuery("select *from learntable where category='"+cate+"'",null);
        return c;
    }

    public Cursor getleandetails()
    {
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor c=db.rawQuery("select *from learntable",null);
        return c;
    }

    public long setdetails(String cname)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put("catename",cname);

        long result=db.insert("cattable",null,cv);
        return result;
    }

    public Cursor getdetails()
    {
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor c=db.rawQuery("select *from cattable",null);
        return c;
    }


    long deletedetail(int no)
    {

        SQLiteDatabase db=this.getWritableDatabase();
        int result=db.delete("logatp","uid=?",new String[]{Integer.toString(no)});
        return result;

    }

    long updatedetail(int no,String name)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put("uid",no);
        cv.put("sname",name);
        long result=db.update("logatp",cv,"uid=?",new String[]{Integer.toString(no)});
        return result;


    }



}

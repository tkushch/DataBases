package com.example.databases;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DBHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "MyDB";
    public static final String TABLE_MYTABLE = "MyTable";

    public static final String KEY_ID = "_id";
    public static final String KEY_A = "a";
    public static final String KEY_B = "b";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_MYTABLE + "(" + KEY_ID
        + " integer primary key," + KEY_A + " text," + KEY_B + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_MYTABLE);
        onCreate(db);
    }
}

package com.example.lenovo.application_1214.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.lenovo.application_1214.database.table.Account;
import com.example.lenovo.application_1214.database.table.Discuss;

import com.example.lenovo.application_1214.database.table.Friend;
import com.example.lenovo.application_1214.database.table.Letter;
import com.example.lenovo.application_1214.database.table.Topic;

/**
 * Created by Donvy_y on 2018/6/4.
 */
public class DatabaseHelper extends SQLiteOpenHelper{
    private static final int VERSION = 1;
    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context,name,factory,version);
    }

    public static interface TableCreateInterface{
        public void onCreate(SQLiteDatabase db);
        public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        Account.getInstance().onCreate(db);
        Topic.getInstance().onCreate(db);
        Letter.getInstance().onCreate(db);
        Friend.getInstance().onCreate(db);
        Discuss.getInstance().onCreate(db);

    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Account.getInstance().onUpgrade(db, oldVersion, newVersion);
        Topic.getInstance().onUpgrade(db, oldVersion, newVersion);
        Letter.getInstance().onUpgrade(db, oldVersion, newVersion);
        Friend.getInstance().onUpgrade(db, oldVersion, newVersion);
        Discuss.getInstance().onUpgrade(db, oldVersion, newVersion);

    }
}

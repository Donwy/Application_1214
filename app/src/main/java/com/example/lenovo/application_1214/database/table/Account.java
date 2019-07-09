package com.example.lenovo.application_1214.database.table;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.lenovo.application_1214.database.DatabaseHelper;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Donvy_y on 2018/6/4.
 */
public class Account implements DatabaseHelper.TableCreateInterface{
    public static String tableName = "Account";
    public static String _id = "_id";
    public static String UID = "UID";
    public static String password = "password";
    public static String name = "Account_Name";
    public static String sex = "Account_Sex";

    private static Account account = new Account();

    public static Account getInstance() {
        return Account.account;
    }

    @Override
    public void onCreate( SQLiteDatabase db ) {

        String sql = "create table Account(_id integer primary key autoincrement,UID text," +
                "password text,name text,sex text)";

        db.execSQL( sql );
    }

    @Override
    public void onUpgrade( SQLiteDatabase db, int oldVersion, int newVersion ) {

        if ( oldVersion < newVersion ) {
            String sql = "DROP TABLE IF EXISTS " + Account.tableName;
            db.execSQL( sql );
            this.onCreate( db );
        }
    }

}

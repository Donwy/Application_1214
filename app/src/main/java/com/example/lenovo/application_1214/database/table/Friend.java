package com.example.lenovo.application_1214.database.table;

/**
 * Created by Donvy_y on 2018/6/4.
 */

import java.util.ArrayList;
import java.util.HashMap;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.lenovo.application_1214.database.DatabaseHelper;

public class Friend implements DatabaseHelper.TableCreateInterface {
    public static String tableName = "Friend";
    public static String _id = "_id";
    public static String UID = "UID";
    public static String ID = "Friend_ID";
    public static String name = "Friend_Name";
    public static String sex = "Friend_Sex";

    private static Friend friend = new Friend();

    public static Friend getInstance() {
        return Friend.friend;
    }

    @Override
    public void onCreate( SQLiteDatabase db ) {

        String sql = "create table Friend(_id integer primary key autoincrement,UID text," +
                "Friend_ID text,Friend_Name text,sex text)";
        db.execSQL( sql );
    }

    @Override
    public void onUpgrade( SQLiteDatabase db, int oldVersion, int newVersion ) {

        if ( oldVersion < newVersion ) {
            String sql = "DROP TABLE IF EXISTS " + Friend.tableName;
            db.execSQL( sql );
            this.onCreate( db );
        }
    }

    public static void deleteFriend( DatabaseHelper dbHepler,String _id ) {
        SQLiteDatabase db = dbHepler.getWritableDatabase();
        db.delete(  Friend.tableName, Friend.ID + "=?",new String[] { _id + "" }  );
        db.close();

    }

    public static boolean IsFriend(DatabaseHelper dbHepler, String uid) {

        boolean retVal = true;
        SQLiteDatabase db = dbHepler.getWritableDatabase();
        Cursor c = db.query(Friend.tableName, null, Friend.ID + "=?",
                new String[] {uid}, null, null, null);
        if(c == null || c.getCount() == 0) {
            retVal = false;
        }
        c.close();
        db.close();

        return retVal;

    }

}

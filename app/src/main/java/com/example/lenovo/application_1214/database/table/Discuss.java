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

public class Discuss implements DatabaseHelper.TableCreateInterface {
    // 定义表名
    public static String tableName = "Discuss";
    // 定义各字段名
    public static String _id = "_id"; // _id是SQLite中自动生成的主键，用语标识唯一的记录，为了方便使用，此处定义对应字段名
    public static String UID = "UID"; // 用户id
    public static String AName = "Account_Name"; // 话题的id
    public static String DContent = "Discuss_Content"; // 话题内容
    public static String TName = "Topic_Name"; // 话题的名字
    public static String time = "Discuss_Time"; // 话题的时间

    // 返回表的实例进行创建与更新
    private static Discuss discuss = new Discuss();

    public static Discuss getInstance() {
        return Discuss.discuss;
    }

    //建立数据表
    @Override
    public void onCreate(SQLiteDatabase db){

        String sql ="create table Discuss(_id integer primary key autoincrement,UID text," +
                        "Account_Name text,Topic_Name text,Discuss_Content text,Discuss_Time text)";
        db.execSQL( sql );
    }

    // 更新数据表
    @Override
    public void onUpgrade( SQLiteDatabase db, int oldVersion, int newVersion ) {

        if ( oldVersion < newVersion ) {
            String sql = "DROP TABLE IF EXISTS " + Discuss.tableName;
            db.execSQL( sql );
            this.onCreate( db );
        }
    }

    public static void deleteDiscuss( DatabaseHelper dbHelper, String tname ) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(  Discuss.tableName, Discuss.TName + "=?",new String[] { tname + "" }  );
        db.close();

    }

}

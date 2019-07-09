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

public class Topic implements DatabaseHelper.TableCreateInterface {
    // 定义表名
    public static String tableName = "Topic";
    // 定义各字段名
    public static String _id = "_id"; // _id是SQLite中自动生成的主键，用语标识唯一的记录，为了方便使用，此处定义对应字段名
    public static String UID = "UID"; // 用户id
    public static String ID = "Topic_ID"; // 话题的id
    public static String content = "Topic_Content"; // 话题内容
    public static String time = "Topic_Time"; // 话题的时间
    public static String name = "Topic_Name"; // 话题的名字

    // 返回表的实例进行创建与更新
    private static Topic topic = new Topic();

    public static Topic getInstance() {
        return Topic.topic;
    }

    //建立数据表
    @Override
    public void onCreate(SQLiteDatabase db){

        String sql = "create table Topic(_id integer primary key autoincrement,UID text," +
                "Topic_Name text,Topic_Content text,Topic_Time)";
        db.execSQL( sql );
    }

    // 更新数据表
    @Override
    public void onUpgrade( SQLiteDatabase db, int oldVersion, int newVersion ) {

        if ( oldVersion < newVersion ) {
            String sql = "DROP TABLE IF EXISTS " + Topic.tableName;
            db.execSQL( sql );
            this.onCreate( db );
        }
    }

    // 插入话题
    public static void insertTopic( DatabaseHelper dbHelper, ContentValues userValues ) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.insert( Topic.tableName, null, userValues );
        db.close();
    }

    // 删除一条话题
    public static void deleteTopic( DatabaseHelper dbHelper, String tname ) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(  Topic.tableName, Topic.name + "=?",new String[] { tname + "" }  );
        db.close();

    }
}

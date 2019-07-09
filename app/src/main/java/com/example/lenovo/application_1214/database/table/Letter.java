package com.example.lenovo.application_1214.database.table;
/**
 * Created by Donvy_y on 2018/6/4.
 */
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import com.example.lenovo.application_1214.database.DatabaseHelper;


public class Letter implements DatabaseHelper.TableCreateInterface {
    public static String tableName = "Letter";
    public static String _id = "_id";
    public static String UID = "UID"; // 用户id
    public static String User_Name = " User_Name"; // 用户的名字
    public static String Letter_Content= "Letter_Content"; // 私信内容
    public static String Letter_Time = "Letter_Time"; // 私信的时间
    public static String Friend_Name = "Friend_Name"; //

    private static Letter Letter = new Letter();

    public static Letter getInstance() {
        return Letter.Letter;
    }

    @Override
    public void onCreate( SQLiteDatabase db ) {


        String sql ="create table Letter(_id integer primary key autoincrement,UID text," +
                "User_Name text,Friend_Name text,Letter_Content text,Letter_Time text)";
        db.execSQL( sql );
    }

    @Override
    public void onUpgrade( SQLiteDatabase db, int oldVersion, int newVersion ) {

        if ( oldVersion < newVersion ) {
            String sql = "DROP TABLE IF EXISTS " + Letter.tableName;
            db.execSQL( sql );
            this.onCreate( db );
        }
    }
}


package com.example.lenovo.application_1214.message;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;


import com.example.lenovo.application_1214.R;
import com.example.lenovo.application_1214.database.DatabaseHelper;
import com.example.lenovo.application_1214.friend.FriendActivity;


import java.util.ArrayList;

/**
 * Created by Donvy_y on 2018/6/4.
 */
public class LetterActivity extends Activity {


    private String User_Name;

    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.letter_layout);

        Button close = (Button) findViewById(R.id.button_letter_close);
        ImageView add = (ImageView) findViewById(R.id.letter_add);
        ImageView refresh = (ImageView) findViewById(R.id.letter_refresh);
        ListView listView = (ListView) findViewById(R.id.letter_list);
        //获得好友名
        Intent intent = getIntent();
        final String Friend_Name = intent.getStringExtra("Friend_Name");
        //获得用户账号
        SharedPreferences sharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE);
        String UID = sharedPreferences.getString("UID",null);
        //获取账号的用户名字
        DatabaseHelper db3 = new DatabaseHelper(LetterActivity.this,"Account",null,1);
        SQLiteDatabase db4 = db3.getReadableDatabase();
        Cursor cursor1 = db4.query("Account", new String[]{"UID","name"}, "UID=?", new String[]{UID}, null, null, null);
        while (cursor1.moveToNext()) {
            User_Name = cursor1.getString(cursor1.getColumnIndex("name"));
//            System.out.println(User_Name+"这是获取到自己的名字");
        }
        db3.close();
        db4.close();


        close.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                finish();
                Intent intent = new Intent(LetterActivity.this, FriendActivity.class);
                intent.putExtra("Friend_Name",Friend_Name);      //传递好友名字
                startActivity(intent);
            }
        });

        refresh.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                finish();
                Intent intent = new Intent(LetterActivity.this, LetterActivity.class);
                intent.putExtra("Friend_Name",Friend_Name);
                startActivity(intent);
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                finish();
                Intent intent = new Intent(LetterActivity.this, AddLetterActivity.class);
                intent.putExtra("Friend_Name",Friend_Name);
                startActivity(intent);
            }
        });

        ArrayList<String> arrayList = new ArrayList<>();
        DatabaseHelper db1 = new DatabaseHelper(LetterActivity.this, "Letter", null, 1);
        SQLiteDatabase db2 = db1.getReadableDatabase();
//        System.out.println(Friend_Name+"好友名字");                                                                  "User_Name=? or Friend_Name=?"，new String[]{User_Name,User_Name}
        Cursor cursor = db2.query("Letter", new String[]{"User_Name","Friend_Name", "Letter_Content",
                "Letter_Time"}, "User_Name=? or Friend_Name=?",new String[]{Friend_Name,Friend_Name}, null, null, null);
        while (cursor.moveToNext()) {
            String User_Name1 = cursor.getString(cursor.getColumnIndex("User_Name"));
            String Friend_Name1 = cursor.getString(cursor.getColumnIndex("Friend_Name"));
            String Letter_Content = cursor.getString(cursor.getColumnIndex("Letter_Content"));
            String Letter_Time = cursor.getString(cursor.getColumnIndex("Letter_Time"));
//            System.out.println("数据库给出的用户名字：" + User_Name1 + "数据库给出的朋友名字" + Friend_Name1 +"==不是数据库的朋友"+Friend_Name+ "这一句" + Letter_Content);
            if(User_Name1.equals(User_Name)||((Friend_Name1.equals(User_Name)&&User_Name1.equals(Friend_Name)))) {
                    arrayList.add(User_Name1 + " : " + Letter_Content + "\n" + Letter_Time);
            }
            }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>
                (this, R.layout.test, arrayList);
        listView.setAdapter(arrayAdapter);
        db1.close();
        db2.close();
        }
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = getIntent();
            final String Friend_Name = intent.getStringExtra("Friend_Name");
            finish();
            intent = new Intent(LetterActivity.this, FriendActivity.class);
            intent.putExtra("Friend_Name",Friend_Name);
            startActivity(intent);
        }
        return false;
    }
}

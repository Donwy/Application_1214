package com.example.lenovo.application_1214;

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
import android.widget.Button;
import android.widget.TextView;

import com.example.lenovo.application_1214.broadcast.BroadcastActivity;
import com.example.lenovo.application_1214.broadcast.MyTopicActivity;
import com.example.lenovo.application_1214.broadcast.TopicActivity;
import com.example.lenovo.application_1214.database.DatabaseHelper;
import com.example.lenovo.application_1214.loginSignin.RevisePasswordActivity;

/**
 * Created by Donvy_y on 2017/12/20.
 */
public class ProfileActivity extends Activity{
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.profile_layout);

        TextView tuid = (TextView)findViewById(R.id.text_profile_UID);
        TextView tname = (TextView)findViewById(R.id.text_profile_name);
        TextView tsex = (TextView)findViewById(R.id.text_profile_sex);
        Button close = (Button)findViewById(R.id.button_profile_close);
        Button revisepassword = (Button)findViewById(R.id.button_profile_revisepassword);
        Button mytopic = (Button)findViewById(R.id.button_MyTopic);

        SharedPreferences sharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE);
        String uid = sharedPreferences.getString("UID",null);
        DatabaseHelper db1 = new DatabaseHelper(this, "Account", null, 1);
        SQLiteDatabase db2 = db1.getReadableDatabase();
        Cursor cursor = db2.query("Account", new String[]{"UID","name","sex"}, "UID=?", new String[]{uid}, null, null, null);
        while (cursor.moveToNext()) {
            String A_UID = cursor.getString(cursor.getColumnIndex("UID"));
            String A_Name = cursor.getString(cursor.getColumnIndex("name"));
            String A_Sex = cursor.getString(cursor.getColumnIndex("sex"));
            tuid.setText(A_UID);
            tname.setText(A_Name);
            tsex.setText(A_Sex);
        }

        close.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                finish();
                Intent intent = new Intent(ProfileActivity.this,BroadcastActivity.class);
                startActivity(intent);
            }
        });

        revisepassword.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                finish();
                Intent intent = new Intent(ProfileActivity.this,RevisePasswordActivity.class);
                startActivity(intent);
            }
        });
        mytopic.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                finish();
                Intent intent = new Intent(ProfileActivity.this,MyTopicActivity.class);
                startActivity(intent);
            }
        });
    }
}

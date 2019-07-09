package com.example.lenovo.application_1214.broadcast;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lenovo.application_1214.R;
import com.example.lenovo.application_1214.database.DatabaseHelper;
import com.example.lenovo.application_1214.database.table.Topic;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Donvy_y on 2018/6/4.
 */
public class AddBroadcastActivity extends Activity{

    private EditText topic,content;
    private boolean a = false;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.addbroadcast_layout);

        Button add = (Button)findViewById(R.id.button_addbroad_add);
        Button close = (Button)findViewById(R.id.button_addbroad_close);
        topic = (EditText)findViewById(R.id.edit_addbroad_topic);
        content = (EditText)findViewById(R.id.edit_addbroad_content);

        close.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                finish();
                Intent intent = new Intent(AddBroadcastActivity.this,BroadcastActivity.class);
                startActivity(intent);
            }
        });

        add.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                if (TextUtils.isEmpty(topic.getText().toString())) {
                    topic.setError("话题题目不能为空");
                    return;
                }
                if (TextUtils.isEmpty(content.getText().toString())) {
                    content.setError("话题内容不能为空");
                    return;
                }
                else {
                    AddTopic();
                }
            }
        });
    }

    private void AddTopic(){
        IsExist();
        if (a){
            SimpleDateFormat formatter   =   new   SimpleDateFormat   ("yyyy年MM月dd日   HH:mm:ss");
            Date curDate =  new Date(System.currentTimeMillis());
            SharedPreferences sharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE);
            String UID = sharedPreferences.getString("UID",null);
            DatabaseHelper db = new DatabaseHelper(AddBroadcastActivity.this, "Topic", null, 1);
            ContentValues values = new ContentValues();
            values.put("UID",UID);
            values.put("Topic_Name",topic.getText().toString());
            values.put("Topic_Content",content.getText().toString());
            values.put("Topic_Time",formatter.format(curDate));
            Topic.insertTopic(db,values);
            db.close();
            finish();
            Intent intent = new Intent(AddBroadcastActivity.this,BroadcastActivity.class);
            startActivity(intent);
        }
    }

    private void IsExist(){
        Boolean b = true;
        String check = topic.getText().toString();
        DatabaseHelper db1 = new DatabaseHelper(AddBroadcastActivity.this, "Topic", null, 1);
        SQLiteDatabase db2 = db1.getReadableDatabase();
        Cursor cursor = db2.query("Topic", new String[]{"Topic_Name"}, "Topic_Name=?", new String[]{check}, null, null, null);
        while (cursor.moveToNext()) {
            String checkname = cursor.getString(cursor.getColumnIndex("Topic_Name"));
            if (checkname.equals(check)) {
                Toast.makeText(getApplicationContext(), "发表失败：该话题已存在",
                        Toast.LENGTH_LONG).show();
                b = false;
            }
        }
        if (b){
            a = true;
        }
    }
}

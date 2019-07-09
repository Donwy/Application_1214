package com.example.lenovo.application_1214.broadcast;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.example.lenovo.application_1214.R;
import com.example.lenovo.application_1214.database.DatabaseHelper;
import com.example.lenovo.application_1214.database.table.Discuss;
import com.example.lenovo.application_1214.database.table.Topic;

/**
 * Created by Donvy_y on 2018/6/4.
 */
public class TopicActivity extends Activity{

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.topic_layout);

        TextView time = (TextView)findViewById(R.id.text_topic_time);
        TextView uid = (TextView)findViewById(R.id.text_topic_UID);
        TextView name = (TextView)findViewById(R.id.text_topic_name);
        TextView content = (TextView)findViewById(R.id.text_topic_content);
        content.setMovementMethod(ScrollingMovementMethod.getInstance());
        Button close = (Button)findViewById(R.id.button_topic_close);
        Button discuss = (Button)findViewById(R.id.button_topic_discuss);

        Intent intent = getIntent();
        final String Topic_Name = intent.getStringExtra("Topic_Name");

        DatabaseHelper db1 = new DatabaseHelper(TopicActivity.this, "Topic", null, 1);
        SQLiteDatabase db2 = db1.getReadableDatabase();
        Cursor cursor = db2.query("Topic", new String[]{"UID", "Topic_Time", "Topic_Name", "Topic_Content"}, "Topic_Name=?", new String[]{Topic_Name}, null, null, null);
        while (cursor.moveToNext()) {
            String UID = cursor.getString(cursor.getColumnIndex("UID"));
            String T_Time = cursor.getString(cursor.getColumnIndex("Topic_Time"));
            String T_Name = cursor.getString(cursor.getColumnIndex("Topic_Name"));
            String T_Content = cursor.getString(cursor.getColumnIndex("Topic_Content"));
            time.setText(T_Time);
            uid.setText(UID);
            name.setText(T_Name);
            content.setText(T_Content);
            db1.close();
            db2.close();
        }

        close.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                finish();
                Intent intent = new Intent(TopicActivity.this,BroadcastActivity.class);
                startActivity(intent);
            }
        });

        discuss.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                finish();
                Intent intent = new Intent(TopicActivity.this,DiscussActivity.class);
                intent.putExtra("Topic_Name",Topic_Name);
                startActivity(intent);
            }
        });
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            Intent intent = new Intent(TopicActivity.this, BroadcastActivity.class);
            startActivity(intent);
        }
        return false;
    }
}

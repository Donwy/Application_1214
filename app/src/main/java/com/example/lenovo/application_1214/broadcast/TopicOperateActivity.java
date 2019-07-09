package com.example.lenovo.application_1214.broadcast;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.example.lenovo.application_1214.R;
import com.example.lenovo.application_1214.database.DatabaseHelper;
import com.example.lenovo.application_1214.database.table.Discuss;
import com.example.lenovo.application_1214.database.table.Friend;
import com.example.lenovo.application_1214.database.table.Topic;
import com.example.lenovo.application_1214.friend.FriendActivity;

/**
 * Created by Donvy_y on 2018/6/4.
 */
public class TopicOperateActivity extends Activity{
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.topic_operate_layout);

        TextView ttime = (TextView)findViewById(R.id.text_topic_operate_time);
        TextView tname = (TextView)findViewById(R.id.text_topic_operate_name);
        TextView tcontent = (TextView)findViewById(R.id.text_topic_operate_content);
        tcontent.setMovementMethod(ScrollingMovementMethod.getInstance());
        Button close = (Button)findViewById(R.id.button_topic_operate_close);
        Button delete = (Button)findViewById(R.id.button_topic_operate_delete);

        Intent intent = getIntent();
        final String Topic_Name = intent.getStringExtra("Topic_Name");

        DatabaseHelper db1 = new DatabaseHelper(TopicOperateActivity.this, "Topic", null, 1);
        SQLiteDatabase db2 = db1.getReadableDatabase();
        Cursor cursor = db2.query("Topic", new String[]{"Topic_Time","Topic_Name","Topic_Content"}, "Topic_Name=?", new String[]{Topic_Name}, null, null, null);
        while (cursor.moveToNext()) {
            String T_Time = cursor.getString(cursor.getColumnIndex("Topic_Time"));
            String T_Name = cursor.getString(cursor.getColumnIndex("Topic_Name"));
            String T_Content = cursor.getString(cursor.getColumnIndex("Topic_Content"));
            ttime.setText(T_Time);
            tname.setText(T_Name);
            tcontent.setText(T_Content);
            db1.close();
            db2.close();;
        }

        close.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                finish();
                Intent intent = new Intent(TopicOperateActivity.this,MyTopicActivity.class);
                startActivity(intent);
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                DatabaseHelper db1 = new DatabaseHelper(TopicOperateActivity.this, "Topic", null, 1);
                Topic.deleteTopic(db1, Topic_Name);
                DatabaseHelper db2 = new DatabaseHelper(TopicOperateActivity.this, "Discuss", null, 1);
                Discuss.deleteDiscuss(db2, Topic_Name);
                finish();
                Intent intent = new Intent(TopicOperateActivity.this,MyTopicActivity.class);
                startActivity(intent);
            }
        });
    }
}

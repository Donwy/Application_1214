package com.example.lenovo.application_1214.broadcast;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.lenovo.application_1214.ProfileActivity;
import com.example.lenovo.application_1214.R;
import com.example.lenovo.application_1214.database.DatabaseHelper;
import com.example.lenovo.application_1214.friend.FriendActivity;

import java.util.ArrayList;

/**
 * Created by Donvy_y on 2018/6/4.
 */
public class MyTopicActivity extends Activity{
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.mytopic_layout);

        Button close = (Button) findViewById(R.id.button_MyTopic_close);
        ListView listView = (ListView) findViewById(R.id.mytopic_list);
        SharedPreferences sharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE);
        String Uid = sharedPreferences.getString("UID", null);

        ArrayList<String> arrayList = new ArrayList<>();
        DatabaseHelper db1 = new DatabaseHelper(MyTopicActivity.this, "Topic", null, 1);
        SQLiteDatabase db2 = db1.getReadableDatabase();
        Cursor cursor = db2.query("Topic", new String[]{"UID", "Topic_Name"}, "UID=?", new String[]{Uid}, null, null, null);
        while (cursor.moveToNext()) {
            String T_Name = cursor.getString(cursor.getColumnIndex("Topic_Name"));
            arrayList.add(T_Name);
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>
                (this, R.layout.test, arrayList);
        listView.setAdapter(arrayAdapter);
        db1.close();
        db2.close();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                finish();
                String result = parent.getItemAtPosition(position).toString();
                Intent intent = new Intent(MyTopicActivity.this,TopicOperateActivity.class);
                intent.putExtra("Topic_Name",result);
                startActivity(intent);
            }
        });
        close.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                finish();
                Intent intent = new Intent(MyTopicActivity.this,ProfileActivity.class);
                startActivity(intent);
            }
        });
    }

}

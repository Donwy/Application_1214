package com.example.lenovo.application_1214.broadcast;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.lenovo.application_1214.R;
import com.example.lenovo.application_1214.database.DatabaseHelper;

import java.util.ArrayList;

/**
 * Created by Donvy_y on 2018/6/4.
 */
public class DiscussActivity extends Activity{

    private int id = 0;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.discuss_layout);

        Button close = (Button)findViewById(R.id.button_discuss_close);
        ImageView add = (ImageView)findViewById(R.id.discuss_add);
        ImageView refresh = (ImageView)findViewById(R.id.discuss_refresh);
        ListView listView = (ListView) findViewById(R.id.discuss_list);

        Intent intent = getIntent();
        final String Topic_Name = intent.getStringExtra("Topic_Name");

        close.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                finish();
                Intent intent = new Intent(DiscussActivity.this,TopicActivity.class);
                intent.putExtra("Topic_Name",Topic_Name);
                startActivity(intent);
            }
        });

        refresh.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                finish();
                Intent intent = new Intent(DiscussActivity.this, DiscussActivity.class);
                intent.putExtra("Topic_Name",Topic_Name);
                startActivity(intent);
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                finish();
                Intent intent = new Intent(DiscussActivity.this, AddDiscussActivity.class);
                intent.putExtra("Topic_Name",Topic_Name);
                startActivity(intent);
            }
        });

        ArrayList<String> arrayList = new ArrayList<>();
        DatabaseHelper db1 = new DatabaseHelper(DiscussActivity.this, "Discuss", null, 1);
        SQLiteDatabase db2 = db1.getReadableDatabase();
        Cursor cursor = db2.query("Discuss",new String[]{"UID", "Account_Name","Topic_Name", "Discuss_Content", "Discuss_Time"}, "Topic_Name=?", new String[]{Topic_Name}, null, null, null);
        while (cursor.moveToNext()) {
            id++;
            String UID = cursor.getString(cursor.getColumnIndex("UID"));
            String D_AName = cursor.getString(cursor.getColumnIndex("Account_Name"));
            String D_Content = cursor.getString(cursor.getColumnIndex("Discuss_Content"));
            String D_Time = cursor.getString(cursor.getColumnIndex("Discuss_Time"));
            arrayList.add("ID:" + UID + "   " + D_AName + "\n" + id + "楼：" + D_Content + "\n" + D_Time);
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
            final String Topic_Name = intent.getStringExtra("Topic_Name");
            finish();
            intent = new Intent(DiscussActivity.this, TopicActivity.class);
            intent.putExtra("Topic_Name",Topic_Name);
            startActivity(intent);
        }
        return false;
    }
}

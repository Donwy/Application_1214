package com.example.lenovo.application_1214.broadcast;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;


import com.example.lenovo.application_1214.ProfileActivity;
import com.example.lenovo.application_1214.R;
import com.example.lenovo.application_1214.database.DatabaseHelper;
import com.example.lenovo.application_1214.friend.FriendActivity;
import com.example.lenovo.application_1214.loginSignin.LoginActivity;


import java.util.ArrayList;
/**
 * Created by Donvy_y on 2018/6/4.
 */
public class BroadcastActivity extends Activity {
    RadioGroup radioGroup = null;
    RadioButton broadcast = null;
    RadioButton friend = null;
    RadioButton letter = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.broadcast_layout);

        ImageView add = (ImageView) findViewById(R.id.broadcast_add);
        ImageView refresh = (ImageView) findViewById(R.id.broadcast_refresh);
        radioGroup = (RadioGroup)findViewById(R.id.radioGroup_broadcast);
        broadcast = (RadioButton)findViewById(R.id.radio_button_broadcast_broadcast);
        friend = (RadioButton)findViewById(R.id.radio_button_broadcast_friend);
        letter = (RadioButton)findViewById(R.id.radio_button_broadcast_letter);
        broadcast.setChecked(true);

        radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(RadioGroup group,int checkedId){
                if(checkedId==broadcast.getId()){

                }
                if(checkedId==friend.getId()){
                    finish();
                    Intent intent = new Intent(BroadcastActivity.this,FriendActivity.class);
                    startActivity(intent);
                }
                if (checkedId==letter.getId()){
                    Intent intent = new Intent(BroadcastActivity.this,ProfileActivity.class);
                    startActivity(intent);
                }
            }
        });

        refresh.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                finish();
                Intent intent = new Intent(BroadcastActivity.this, BroadcastActivity.class);
                startActivity(intent);
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                finish();
                Intent intent = new Intent(BroadcastActivity.this, AddBroadcastActivity.class);
                startActivity(intent);

            }
        });

        ListView listView = (ListView) findViewById(R.id.broadcast_list);

        SharedPreferences sharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE);
        String Uid = sharedPreferences.getString("UID", null);

        ArrayList<String> arrayList = new ArrayList<>();
        DatabaseHelper db1 = new DatabaseHelper(BroadcastActivity.this, "Topic", null, 1);
        SQLiteDatabase db2 = db1.getReadableDatabase();
        Cursor cursor = db2.query("Topic", new String[]{"UID", "Topic_Name"}, null, null, null, null, null);
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
                String result = parent.getItemAtPosition(position).toString();
                Intent intent = new Intent(BroadcastActivity.this,TopicActivity.class);
                intent.putExtra("Topic_Name",result);
                finish();
                startActivity(intent);
            }
        });
    }

    public boolean onKeyDown(int keyCode,KeyEvent event){
        if (keyCode ==KeyEvent.KEYCODE_BACK){
            new AlertDialog.Builder(BroadcastActivity.this)
                    .setTitle("注意")
                    .setMessage("确定要退出？")
                    .setPositiveButton("确定",new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    })
                    .setNegativeButton("取消",new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    })
                    .create().show();
        }
        return false;
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.menu_profile:
                startActivity(new Intent(this, ProfileActivity.class));
                break;
            case R.id.menu_topic:
                startActivity(new Intent(this,MyTopicActivity.class));
                break;
            case  R.id.menu_logout:
                finish();
                startActivity(new Intent(this,LoginActivity.class));
                break;
            case R.id.menu_exit:
                finish();
        }
        return super.onOptionsItemSelected(item);
    }



}


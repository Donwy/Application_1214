package com.example.lenovo.application_1214.friend;

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


import com.example.lenovo.application_1214.ProfileActivity;
import com.example.lenovo.application_1214.R;
import com.example.lenovo.application_1214.broadcast.BroadcastActivity;
import com.example.lenovo.application_1214.broadcast.MyTopicActivity;
import com.example.lenovo.application_1214.database.DatabaseHelper;
import com.example.lenovo.application_1214.loginSignin.LoginActivity;

import java.util.ArrayList;

/**
 * Created by Donvy_y on 2018/6/4.
 */
public class FriendActivity extends Activity {

    RadioGroup radioGroup = null;
    RadioButton broadcast = null;
    RadioButton friend = null;
    RadioButton letter = null;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.friend_layout);

        radioGroup = (RadioGroup)findViewById(R.id.radioGroup_friend);
        broadcast = (RadioButton)findViewById(R.id.radio_button_friend_broadcast);
        friend = (RadioButton)findViewById(R.id.radio_button_friend_friend);
        letter = (RadioButton)findViewById(R.id.radio_button_friend_letter);
        friend.setChecked(true);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(RadioGroup group,int checkedId){
                if(checkedId==broadcast.getId()){
                    finish();
                    Intent intent = new Intent(FriendActivity.this,BroadcastActivity.class);
                    startActivity(intent);
                }
                if(checkedId==friend.getId()){

                }
                if (checkedId==letter.getId()){
                    finish();
                    Intent intent = new Intent(FriendActivity.this,ProfileActivity.class);
                    startActivity(intent);
                }
            }
        });

        ImageView add = (ImageView) findViewById(R.id.friend_add);
        ImageView refresh = (ImageView) findViewById(R.id.friend_refresh);

        add.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(FriendActivity.this, FindFriendActivity.class);
                startActivity(intent);

            }
        });

        refresh.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                finish();
                Intent intent = new Intent(FriendActivity.this, FriendActivity.class);
                startActivity(intent);

            }
        });

        ListView listView = (ListView) findViewById(R.id.friend_list);

        SharedPreferences sharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE);
        String Uid = sharedPreferences.getString("UID", null);

        ArrayList<String> arrayList = new ArrayList<>();
        DatabaseHelper db1 = new DatabaseHelper(FriendActivity.this, "Friend", null, 1);
        SQLiteDatabase db2 = db1.getReadableDatabase();
        Cursor cursor = db2.query("Friend", new String[]{"UID", "Friend_ID", "Friend_Name", "sex"}, "UID=?", new String[]{Uid}, null, null, null);
        while (cursor.moveToNext()) {
            String F_ID = cursor.getString(cursor.getColumnIndex("Friend_ID"));
            String F_Name = cursor.getString(cursor.getColumnIndex("Friend_Name"));
            arrayList.add(F_Name);
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>
                (this, R.layout.test, arrayList);
        listView.setAdapter(arrayAdapter);
        db1.close();
        db2.close();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String friendname = parent.getItemAtPosition(position).toString();
                Intent intent = new Intent(FriendActivity.this, FriendOperateActivity.class);
                intent.putExtra("Friend_Name",friendname);
                finish();
                startActivity(intent);
            }
        });
    }
    public boolean onKeyDown(int keyCode,KeyEvent event){
        if (keyCode ==KeyEvent.KEYCODE_BACK){
            new AlertDialog.Builder(FriendActivity.this)
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
package com.example.lenovo.application_1214.friend;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import com.example.lenovo.application_1214.R;
import com.example.lenovo.application_1214.database.DatabaseHelper;

/**
 * Created by Donvy_y on 2018/6/4.
 */
public class AddFriendActivity extends Activity {
    Button add;
    Button close;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.addfriend_layout);

        TextView tuid = (TextView)findViewById(R.id.text_addfriend_UID);
        TextView tname = (TextView)findViewById(R.id.text_addfriend_name);
        TextView tsex = (TextView)findViewById(R.id.text_addfriend_sex);
        Intent intent = getIntent();
        final String Friend_ID = intent.getStringExtra("UID");
        final String name = intent.getStringExtra("name");
        final String sex = intent.getStringExtra("sex");
        tuid.setText(Friend_ID);
        tname.setText(name);
        tsex.setText(sex);

        close = (Button)findViewById(R.id.button_addfriend_close);
        close.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                finish();
                Intent intent = new Intent(AddFriendActivity.this,FindFriendActivity.class);
                startActivity(intent);
            }
        });

        add = (Button)findViewById(R.id.button_addfriend_add);
        add.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE);
                String UID = sharedPreferences.getString("UID",null);

                ContentValues values = new ContentValues();
                values.put("UID",UID);
                values.put("Friend_ID",Friend_ID);
                values.put("Friend_Name",name);
                values.put("sex",sex);

                DatabaseHelper db1 = new DatabaseHelper(AddFriendActivity.this,"Friend",null,1);
                SQLiteDatabase db2 = db1.getWritableDatabase();
                db2.insert("Friend", null, values);
                db1.close();
                db2.close();

                finish();
                Intent intent = new Intent(AddFriendActivity.this,FriendActivity.class);
                startActivity(intent);
            }
        });

    }
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            Intent intent = new Intent(AddFriendActivity.this, FindFriendActivity.class);
            startActivity(intent);
        }
        return false;
    }
}


package com.example.lenovo.application_1214.friend;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.example.lenovo.application_1214.R;
import com.example.lenovo.application_1214.database.DatabaseHelper;
import com.example.lenovo.application_1214.database.table.Friend;
import com.example.lenovo.application_1214.message.LetterActivity;

/**
 * Created by Donvy_y on 2018/6/4.
 */
public class FriendOperateActivity extends Activity {

    String F_ID;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.friend_operate_layout);

        Intent intent = getIntent();
        final String Friend_Name = intent.getStringExtra("Friend_Name");
        System.out.println(Friend_Name+"345");

        TextView ID = (TextView)findViewById(R.id.text_operate_ID);
        TextView Name = (TextView)findViewById(R.id.text_operate_name);
        TextView Sex = (TextView)findViewById(R.id.text_operate_sex);
        Button letter = (Button)findViewById(R.id.button_operate_letter);
        Button close = (Button)findViewById(R.id.button_operate_close);
        Button delete = (Button)findViewById(R.id.button_operate_delete);

        DatabaseHelper db1 = new DatabaseHelper(FriendOperateActivity.this, "Friend", null, 1);
        SQLiteDatabase db2 = db1.getReadableDatabase();
        Cursor cursor = db2.query("Friend", new String[]{"UID", "Friend_ID", "Friend_Name", "sex"}, "Friend_Name=?", new String[]{Friend_Name}, null, null, null);
        while (cursor.moveToNext()) {
            F_ID = cursor.getString(cursor.getColumnIndex("Friend_ID"));
            String F_Name = cursor.getString(cursor.getColumnIndex("Friend_Name"));
            String F_Sex = cursor.getString(cursor.getColumnIndex("sex"));
            ID.setText(F_ID);
            Name.setText(F_Name);
            Sex.setText(F_Sex);
            db1.close();
            db2.close();
        }

        letter.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                finish();
                Intent intent = new Intent(FriendOperateActivity.this,LetterActivity.class);
                intent.putExtra("Friend_Name",Friend_Name);
                startActivity(intent);
            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                finish();
                Intent intent = new Intent(FriendOperateActivity.this,FriendActivity.class);
                startActivity(intent);
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                DatabaseHelper db = new DatabaseHelper(FriendOperateActivity.this, "Friend", null, 1);
                Friend.deleteFriend(db, F_ID);
                finish();
                Intent intent = new Intent(FriendOperateActivity.this,FriendActivity.class);
                startActivity(intent);
            }
        });
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            Intent intent = new Intent(FriendOperateActivity.this, FriendActivity.class);
            startActivity(intent);
        }
        return false;
    }
}

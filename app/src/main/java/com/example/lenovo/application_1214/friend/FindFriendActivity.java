package com.example.lenovo.application_1214.friend;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lenovo.application_1214.R;
import com.example.lenovo.application_1214.broadcast.BroadcastActivity;
import com.example.lenovo.application_1214.database.DatabaseHelper;

/**
 * Created by Donvy_y on 2018/6/4.
 */
public class FindFriendActivity extends Activity {

    EditText findfriend = null;
    Button search;
    Button close;
    Boolean no = true;
    boolean non = true;
    String F_Name;
    String F_ID;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.findfriend_layout);

        findfriend = (EditText) findViewById(R.id.edit_findfriend);
        search = (Button) findViewById(R.id.button_findfriend_search);
        close = (Button) findViewById(R.id.button_findfriend_close);

        search.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                IsEmptyEditText();
                IsSelf();
                if (IsEmptyEditText() == false && IsSelf()==false) {
                    IsFriend();
                    if (IsFriend() == false){
                        //如果是好友就进入好友操作界面
                        finish();
                        Intent intent = new Intent(FindFriendActivity.this,FriendOperateActivity.class);
                        intent.putExtra("Friend_Name",F_Name);
                        startActivity(intent);
                    }
                    if (IsFriend() == true){
                        checkID();
                    }
                }
            }
        });

        close.setOnClickListener(new View.OnClickListener() {
              public void onClick(View view) {
               finish();
                  Intent intent = new Intent(FindFriendActivity.this,FriendActivity.class);
                  startActivity(intent);
                }
        });
    }

    //判断输入是否为空
    private boolean IsEmptyEditText() {
        if (TextUtils.isEmpty(findfriend.getText().toString())) {
            Toast.makeText(getApplicationContext(), "查询账号不能为空",
                    Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }

    //判断查询对象是否为自己
    private boolean IsSelf() {
        SharedPreferences sharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE);
        String Uid = sharedPreferences.getString("UID", null);
        if (Uid.equals(findfriend.getText().toString())) {
            Toast.makeText(getApplicationContext(), "查询对象不能为自己",
                    Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }

    //判断帐号是否存在，存在就进入添加好友界面
    public void checkID() {
        non = true;
        String check = findfriend.getText().toString();
        DatabaseHelper db1 = new DatabaseHelper(FindFriendActivity.this, "Account", null, 1);
        SQLiteDatabase db2 = db1.getReadableDatabase();
        Cursor cursor = db2.query("Account", new String[]{"UID","name","sex"}, "UID=?", new String[]{check}, null, null, null);
        while (cursor.moveToNext()) {
            String checknumber = cursor.getString(cursor.getColumnIndex("UID"));
            if (checknumber.equals(check)) {
                non = false;
                String name = cursor.getString(cursor.getColumnIndex("name"));
                String sex = cursor.getString(cursor.getColumnIndex("sex"));
                db1.close();
                db2.close();
                finish();
                Intent intent = new Intent(FindFriendActivity.this, AddFriendActivity.class);
                intent.putExtra("UID",check);
                intent.putExtra("name",name);
                intent.putExtra("sex",sex);
                startActivity(intent);
            }
        }
        if (non){
            db1.close();
            db2.close();
            Toast.makeText(getApplicationContext(), "该用户不存在",
                    Toast.LENGTH_SHORT).show();
        }

    }

    //判断是否是好友
    public boolean IsFriend(){
        SharedPreferences sharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE);
        String Uid = sharedPreferences.getString("UID",null);
        DatabaseHelper db1 = new DatabaseHelper(FindFriendActivity.this, "Friend", null, 1);
        SQLiteDatabase db2 = db1.getReadableDatabase();
        Cursor cursor = db2.query("Friend", new String[]{"UID","Friend_ID","Friend_Name"}, "UID=?", new String[]{Uid}, null, null, null);
        while (cursor.moveToNext()) {
            F_ID = cursor.getString(cursor.getColumnIndex("Friend_ID"));
            if (F_ID.equals(findfriend.getText().toString())){
                F_Name = cursor.getString(cursor.getColumnIndex("Friend_Name"));
                db1.close();
                db2.close();
                return false;
            }

        }
        db1.close();
        db2.close();
        return  true;
    }
}

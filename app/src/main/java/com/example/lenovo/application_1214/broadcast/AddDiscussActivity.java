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
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import com.example.lenovo.application_1214.R;
import com.example.lenovo.application_1214.database.DatabaseHelper;


import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Lenovo on 2017/12/20.
 */
public class AddDiscussActivity extends Activity {
    private EditText addDiscuss = null;
    private String A_Name;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.adddiscuss_layout);

        addDiscuss = (EditText)findViewById(R.id.edit_adddiscuss);
        Button add = (Button)findViewById(R.id.button_adddiscuss_add);
        Button close = (Button)findViewById(R.id.button_adddiscuss_close);

        //获得话题题目
        Intent intent = getIntent();
        final String Topic_Name = intent.getStringExtra("Topic_Name");

        close.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                finish();
                Intent intent = new Intent(AddDiscussActivity.this,DiscussActivity.class);
                intent.putExtra("Topic_Name",Topic_Name);
                startActivity(intent);
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                IsEmptyEditText();
                if(IsEmptyEditText() == false){
                    //获得时间
                    SimpleDateFormat formatter   =   new   SimpleDateFormat   ("yyyy年MM月dd日   HH:mm:ss");
                    Date curDate =  new Date(System.currentTimeMillis());
                    //获得用户账号
                    SharedPreferences sharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE);
                    String UID = sharedPreferences.getString("UID",null);
                    //获取账号的用户名字
                    DatabaseHelper db1 = new DatabaseHelper(AddDiscussActivity.this,"Account",null,1);
                    SQLiteDatabase db2 = db1.getReadableDatabase();
                    Cursor cursor = db2.query("Account", new String[]{"UID","name"}, "UID=?", new String[]{UID}, null, null, null);
                    while (cursor.moveToNext()) {
                        A_Name = cursor.getString(cursor.getColumnIndex("name"));
                    }

                    //插入评论
                    ContentValues values = new ContentValues();
                    values.put("UID",UID);
                    values.put("Account_Name",A_Name);
                    values.put("Topic_Name",Topic_Name);
                    values.put("Discuss_Content",addDiscuss.getText().toString());
                    values.put("Discuss_Time",formatter.format(curDate));

                    DatabaseHelper db3 = new DatabaseHelper(AddDiscussActivity.this,"Discuss",null,1);
                    SQLiteDatabase db4 = db3.getWritableDatabase();
                    db4.insert("Discuss", null, values);
                    db3.close();
                    db4.close();

                    finish();
                    Intent intent0 = new Intent(AddDiscussActivity.this,DiscussActivity.class);
                    intent0.putExtra("Topic_Name",Topic_Name);
                    startActivity(intent0);
                }
            }
        });
    }

    private boolean IsEmptyEditText() {
        if (TextUtils.isEmpty(addDiscuss.getText().toString())) {
            addDiscuss.setError("账号不能为空");
            return true;
        }
        return false;
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = getIntent();
            final String Topic_Name = intent.getStringExtra("Topic_Name");
            finish();
            intent = new Intent(AddDiscussActivity.this, DiscussActivity.class);
            intent.putExtra("Topic_Name",Topic_Name);
            startActivity(intent);
        }
        return false;
    }
}

package com.example.lenovo.application_1214.message;

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
 * Created by Donvy_y on 2018/6/3.
 */
public class AddLetterActivity extends Activity {
    private EditText addLetter = null;
    private String User_Name;
    private String self;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.addletter_layout);

        addLetter = (EditText) findViewById(R.id.edit_addletter);
        Button add = (Button) findViewById(R.id.button_addletter_add);
        Button close = (Button) findViewById(R.id.button_addletter_close);



        //获得好友名
        Intent intent = getIntent();
        final String Friend_Name = intent.getStringExtra("Friend_Name");
        System.out.print(Friend_Name+"123");

        close.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                finish();
                Intent intent = new Intent(AddLetterActivity.this, LetterActivity.class);
                intent.putExtra("Friend_Name",Friend_Name);
                startActivity(intent);
            }
        });
        add.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                IsEmptyEditText();
                if(IsEmptyEditText() == false ){
                    //获得时间
                    SimpleDateFormat formatter   =   new   SimpleDateFormat   ("yyyy年MM月dd日   HH:mm:ss");
                    Date curDate =  new Date(System.currentTimeMillis());
                    //获得用户账号
                    SharedPreferences sharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE);
                    String UID = sharedPreferences.getString("UID",null);
                    //获取账号的用户名字
                    DatabaseHelper db1 = new DatabaseHelper(AddLetterActivity.this,"Account",null,1);
                    SQLiteDatabase db2 = db1.getReadableDatabase();
                    Cursor cursor = db2.query("Account", new String[]{"UID","name"}, "UID=?", new String[]{UID}, null, null, null);
                    while (cursor.moveToNext()) {
                        User_Name = cursor.getString(cursor.getColumnIndex("name"));
                    }

                    //插入私信
                    ContentValues values = new ContentValues();
                    values.put("User_Name",User_Name);
                    values.put("Friend_Name",Friend_Name);
                    values.put("Letter_Content",addLetter.getText().toString());
                    values.put("Letter_Time",formatter.format(curDate));
                    System.out.println("User_Name:"+User_Name+"Friend:"+Friend_Name+addLetter.getText().toString());

                    DatabaseHelper db3 = new DatabaseHelper(AddLetterActivity.this,"Letter",null,1);
                    SQLiteDatabase db4 = db3.getWritableDatabase();
                    db4.insert("Letter", null, values);
                    db3.close();
                    db4.close();

                    finish();
                    Intent intent0 = new Intent(AddLetterActivity.this,LetterActivity.class);
                    intent0.putExtra("Friend_Name",Friend_Name);
                    startActivity(intent0);
                }



            }
        });
    }
        private boolean IsEmptyEditText() {
            if (TextUtils.isEmpty(addLetter.getText().toString())) {
            addLetter.setError("账号不能为空");
            return true;
           }
            return false;
        }
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = getIntent();
            final String Friend_Name = intent.getStringExtra("Friend_Name");
            finish();
            intent = new Intent(AddLetterActivity.this, LetterActivity.class);
            intent.putExtra("Friend_Name",Friend_Name);
            startActivity(intent);
        }
        return false;
    }

}

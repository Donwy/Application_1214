package com.example.lenovo.application_1214.loginSignin;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lenovo.application_1214.ProfileActivity;
import com.example.lenovo.application_1214.R;
import com.example.lenovo.application_1214.database.DatabaseHelper;
import com.example.lenovo.application_1214.friend.FriendActivity;

/**
 * Created by Donvy_y on 2018/6/4.
 */
public class RevisePasswordActivity extends Activity {
    private EditText revisepassword = null;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.revisepassword_layout);

        Button sure = (Button) findViewById(R.id.button_revisepassword_sure);
        Button close = (Button) findViewById(R.id.button_revisepassword_close);
        revisepassword = (EditText)findViewById(R.id.edit_revisepassword);

        close.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                finish();
                Intent intent = new Intent(RevisePasswordActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });

        sure.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                IsEmptyEditText();
                if (IsEmptyEditText() == false) {
                    SharedPreferences sharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE);
                    String UID = sharedPreferences.getString("UID", null);
                    DatabaseHelper db1 = new DatabaseHelper(RevisePasswordActivity.this, "Account", null, 1);
                    SQLiteDatabase db2 = db1.getReadableDatabase();
                    ContentValues values = new ContentValues();
                    values.put("password", revisepassword.getText().toString());
                    db2.update("Account", values, "UID=?", new String[]{UID});
                    db1.close();
                    db2.close();
                    finish();
                    Intent intent = new Intent(RevisePasswordActivity.this, LoginActivity.class);
                    startActivity(intent);
                }

            }
        });

    }

    //判断输入是否为空
    private boolean IsEmptyEditText() {
        if (TextUtils.isEmpty(revisepassword.getText().toString())) {
            Toast.makeText(getApplicationContext(), "新密码不能为空",
                    Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            Intent intent = new Intent(RevisePasswordActivity.this, ProfileActivity.class);
            startActivity(intent);
        }
        return false;
    }

}

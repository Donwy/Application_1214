package com.example.lenovo.application_1214.loginSignin;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lenovo.application_1214.R;
import com.example.lenovo.application_1214.broadcast.BroadcastActivity;
import com.example.lenovo.application_1214.database.DatabaseHelper;
import com.example.lenovo.application_1214.database.table.Account;

/**
 * Created by Donvy_y on 2018/6/4.
 */
public class LoginActivity extends Activity {
    private EditText account, password;
    Account db;

    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //取消标题栏
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //取消状态栏
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        //      WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.login_layout);
        account = (EditText) findViewById(R.id.account);
        password = (EditText) findViewById(R.id.password);
        TextView signin = (TextView)findViewById(R.id.signin);
        signin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(LoginActivity.this,SigninActivity.class);
                startActivity(intent);

            }
        });
        Button button_login = (Button)findViewById(R.id.button_login);
        button_login.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                if (TextUtils.isEmpty(account.getText().toString())) {
                    account.setError(getString(R.string.no_empty_account));
                    return;
                }
                if (TextUtils.isEmpty(password.getText().toString())) {
                    password.setError(getString(R.string.no_empty_password));
                    return;
                }
                else {
                    checkPassword();
                }
            }
        });
    }

    public void checkPassword() {
        String check = account.getText().toString();
        String editpassword = password.getText().toString();
        DatabaseHelper db1 = new DatabaseHelper(LoginActivity.this,"Account",null,1);
        SQLiteDatabase db2 = db1.getReadableDatabase();
        Cursor cursor = db2.query("Account", new String[]{"UID","password"}, "UID=?", new String[]{check}, null, null, null);
        while (cursor.moveToNext()){
            String checkpassword = cursor.getString(cursor.getColumnIndex("password"));
            if (checkpassword.equals(editpassword)){
                //保存用户登陆id，MODE_PRIVATE 每次重新登陆都会重写
                SharedPreferences sharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("UID",account.getText().toString());
                //保存数据
                editor.commit();
                db1.close();
                db2.close();
                Toast.makeText(getApplicationContext(),"登陆成功",
                        Toast.LENGTH_SHORT).show();
                finish();
                Intent intent = new Intent(LoginActivity.this,BroadcastActivity.class);
                startActivity(intent);
            }
            else{
                password.setError(getString(R.string.error_password));
                db1.close();
                db2.close();
            }
        }
    }
}

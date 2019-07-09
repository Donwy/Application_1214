package com.example.lenovo.application_1214.loginSignin;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.lenovo.application_1214.R;
import com.example.lenovo.application_1214.broadcast.BroadcastActivity;
import com.example.lenovo.application_1214.database.DatabaseHelper;
import com.example.lenovo.application_1214.database.table.Account;
import com.example.lenovo.application_1214.friend.AddFriendActivity;

/**
 * Created by Donvy_y on 2018/6/4.
 */
public class SigninActivity extends Activity {
    EditText account = null;
    EditText password = null;
    EditText name = null;
    RadioGroup sex = null;
    RadioButton man = null;
    RadioButton women = null;
    RadioButton c = null;
    String sexStr = "男";
    Button signin;
    Account db;
    Boolean a = false;
    Boolean b = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.signin_layout);

        account = (EditText)findViewById(R.id.signin_account);
        password = (EditText)findViewById(R.id.signin_password);
        name = (EditText)findViewById(R.id.signin_name);
        sex = (RadioGroup)findViewById(R.id.signin_sex);
        man = (RadioButton)findViewById(R.id.signin_man);
        women = (RadioButton)findViewById(R.id.signin_women);
        signin = (Button)findViewById(R.id.button_signin);

        sex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(RadioGroup group,int checkedId){
                if(checkedId==man.getId()){
                    sexStr = "男";
                }
                if(checkedId==women.getId()){
                    sexStr = "女";
                }
            }
        });

        signin.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
              IsEmptyEditText();
                if (IsEmptyEditText()==false && a == true){
                    ContentValues values = new ContentValues();
                    values.put("UID",account.getText().toString());
                    values.put("password",password.getText().toString());
                    values.put("name",name.getText().toString());
                    values.put("sex",sexStr);

                    DatabaseHelper db1 = new DatabaseHelper(SigninActivity.this,"Account",null,1);
                    SQLiteDatabase db2 = db1.getWritableDatabase();
                    db2.insert("Account", null, values);
                    db1.close();
                    db2.close();

                    Intent intent = new Intent(SigninActivity.this,LoginActivity.class);
                    startActivity(intent);
                }
            }
        });
    }


    private void addAccount(){
        String insertSrt = "insert into Account(UID,pasword,name,sex)" +
                "values(" + account.getText() + "," + password.getText() + "," +
                name.getText() + "," + sexStr + ")";

    }

    private boolean IsEmptyEditText() {
        if(TextUtils.isEmpty(account.getText().toString())) {
            account.setError("账号不能为空");
            return true;
        }
        if(TextUtils.isEmpty(password.getText().toString())) {
            password.setError("密码不能为空");
            return true;
        }
        if(TextUtils.isEmpty(name.getText().toString())) {
            name.setError("用户名不能为空");
            return true;
        }
        IsExist();
        return false;
    }

    private void IsExist(){
        b = true;
        String check = account.getText().toString();
        DatabaseHelper db1 = new DatabaseHelper(SigninActivity.this, "Account", null, 1);
        SQLiteDatabase db2 = db1.getReadableDatabase();
        Cursor cursor = db2.query("Account", new String[]{"UID"}, "UID=?", new String[]{check}, null, null, null);
        while (cursor.moveToNext()) {
            String checknumber = cursor.getString(cursor.getColumnIndex("UID"));
            if (checknumber.equals(check)) {
                Toast.makeText(getApplicationContext(), "该账号已存在",
                        Toast.LENGTH_SHORT).show();
                b = false;
            }
        }
        if (b){
            a = true;
        }
    }



}

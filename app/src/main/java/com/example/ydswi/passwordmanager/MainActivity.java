package com.example.ydswi.passwordmanager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.ydswi.passwordmanager.utils.InfoHelper;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Map;

import static com.example.ydswi.passwordmanager.R.id.et_password;

/**
 * 密码设置界面
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText etPassword;//密码编辑框
    private Button btnSignIn;//登录按钮
    private SharedPreferences modifyFirst;
    private SharedPreferences modifyPassword;
    private String password = null;//密码
    private boolean isFirst = true;
    private InfoHelper infoHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        modifyFirst = getSharedPreferences("isFirst",Context.MODE_PRIVATE);
        modifyPassword = getSharedPreferences("password",Context.MODE_PRIVATE);
        initView();
        isFirst = modifyFirst.getBoolean("isFirst",true);
        if (!isFirst){
            startActivity(new Intent(this,SignInActivity.class));
            finish();
        }else{
            try {
                this.openFileOutput("info",Context.MODE_PRIVATE);//初始化数据文件;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

    }

    public void initView(){
        etPassword = (EditText) findViewById(R.id.et_password);
        btnSignIn = (Button) findViewById(R.id.btn_set);
        btnSignIn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_set:
                password = etPassword.getText().toString();
                if (password!=null&&!password.equals("")){
                    SharedPreferences.Editor passwordEditor = modifyPassword.edit();
                    passwordEditor.putString("password",password);
                    Map<String, ?> map = modifyPassword.getAll();
                    passwordEditor.commit();
                    SharedPreferences.Editor firstEditor = modifyFirst.edit();
                    firstEditor.putBoolean("isFirst",false);
                    firstEditor.commit();
                    startActivity(new Intent(this,ShowPasswordActivity.class));
                    finish();
                }
                break;
        }
    }
}

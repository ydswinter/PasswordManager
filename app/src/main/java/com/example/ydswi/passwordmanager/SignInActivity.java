package com.example.ydswi.passwordmanager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener{
    private SharedPreferences getPassword;
    private EditText etPassword;
    private Button btnSignIn;//登录按钮

    private String yourPassword;//你输入的密码
    private String storePassword;//存储的密码
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        getPassword = getSharedPreferences("password", Context.MODE_PRIVATE);
        initView();
    }

    public void initView(){
        etPassword = (EditText) findViewById(R.id.et_password);
        btnSignIn = (Button) findViewById(R.id.btn_sign_in);

        btnSignIn.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_sign_in:
                yourPassword = etPassword.getText().toString();
                if (yourPassword!=null&&!yourPassword.equals("")){
                    storePassword = getPassword.getString("password","");
                    if (storePassword!=null&&!storePassword.equals("")){
                        if (yourPassword.equals(storePassword)){
                            startActivity(new Intent(this,ShowPasswordActivity.class));
                            finish();
                        }else{
                            Toast.makeText(this,"密码错误",Toast.LENGTH_SHORT).show();
                        }


                    }
                }else
                    Toast.makeText(this,"请输入密码",Toast.LENGTH_SHORT).show();
                break;
        }
    }
}

package com.example.ydswi.passwordmanager;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ydswi.passwordmanager.bean.Info;
import com.example.ydswi.passwordmanager.utils.InfoHelper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AddActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView btnBack;//返回按钮
    private Button btnConfirm;//确定按钮
    private EditText etAccount;//账号编辑框
    private EditText etPassword;//密码编辑框
    private EditText etBelongTo;//所属编辑框

    private InfoHelper infoHelper = new InfoHelper(this);
    private Info info;
    private List<Info> infoList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        initView();
    }


    public void initView(){
        btnBack = (TextView) findViewById(R.id.btn_back);
        btnConfirm = (Button) findViewById(R.id.btn_confirm);
        etAccount = (EditText) findViewById(R.id.et_account);
        etPassword = (EditText) findViewById(R.id.et_password);
        etBelongTo = (EditText) findViewById(R.id.et_belong_to);

        btnBack.setOnClickListener(this);
        btnConfirm.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_back:
                finish();
                break;
            case R.id.btn_confirm:
                String account,password,belong;
                account = etAccount.getText().toString().trim();
                password = etPassword.getText().toString().trim();
                belong = etBelongTo.getText().toString().trim();
                if (account!=null&&password!=null&&belong!=null&&!account.equals("")&&!password.equals("")&&!belong.equals("")){
                    info = new Info(account,password,belong);
                    infoList.add(info);
                    try {
                        infoHelper.insert(info);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    finish();
                }else{
                    Toast.makeText(this,"请检查是否输入有误或输入为空",Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}

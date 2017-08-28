package com.example.ydswi.passwordmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.ydswi.passwordmanager.bean.Info;
import com.example.ydswi.passwordmanager.utils.InfoHelper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class EditActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView btnBack;//返回按钮
    private Button btnConfirm;//确定按钮
    private EditText etAccount;//账号编辑框
    private EditText etPassword;//密码编辑框
    private EditText etBelongTo;//所属编辑框

    private InfoHelper infoHelper = new InfoHelper(this);
    private Info info;
    private List<Info> infoList = new ArrayList<>();
    private int item;//前一个Activity的点击项
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        Intent i  = getIntent();
        item = i.getIntExtra("item",0);

        try {
            infoList = infoHelper.getAllInfo();
        } catch (IOException e) {
            e.printStackTrace();
        }
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

        etAccount.setText(infoList.get(item).getAccount());
        etPassword.setText(infoList.get(item).getPassword());
        etBelongTo.setText(infoList.get(item).getBelong());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_back:
                finish();
                break;
            case R.id.btn_confirm:
                String account = etAccount.getText().toString().trim();
                String password = etPassword.getText().toString().trim();
                String belong = etBelongTo.getText().toString().trim();
                info = new Info(account,password,belong);
                infoList.set(item,info);
                try {
                    infoHelper.rewrite(infoList);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                finish();
                break;
        }
    }
}

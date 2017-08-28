package com.example.ydswi.passwordmanager;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ydswi.passwordmanager.bean.Info;
import com.example.ydswi.passwordmanager.utils.InfoHelper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class ShowPasswordActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView btnAdd;//添加挑战按钮
    private ListView lvInfo;//
    private InfoHelper infoHelper;

    private Context context;
    private List<Info> infoList = null;
    private InfoAdapter infoAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_password);
        context = this;
        try {
            initView();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            initView();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public void initView() throws IOException {
        btnAdd = (TextView) findViewById(R.id.btn_add);
        btnAdd.setOnClickListener(this);
        lvInfo = (ListView) findViewById(R.id.lv_info);
        infoHelper = new InfoHelper(context);
        infoList = infoHelper.getAllInfo();

        if (infoList!=null){
            infoAdapter = new InfoAdapter(infoList);
            lvInfo.setAdapter(infoAdapter);
        }

        lvInfo.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                infoList.remove(position);
                try {
                    infoHelper.rewrite(infoList);
                    infoList = infoHelper.getAllInfo();
                    if (infoList!=null){
                        infoAdapter = new InfoAdapter(infoList);
                        lvInfo.setAdapter(infoAdapter);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
                return true;
            }
        });
        lvInfo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(ShowPasswordActivity.this,EditActivity.class);
                i.putExtra("item",position);
                startActivity(i);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_add:
                startActivity(new Intent(this,AddActivity.class));
                break;
        }
    }


    class InfoAdapter extends BaseAdapter{
        private List<Info> infoList;

        public  InfoAdapter(List<Info> infoList){
            this.infoList = infoList;
        }
        @Override
        public int getCount() {
            return infoList.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater layoutInflater = getLayoutInflater();
            ViewHolder viewHolder = null;
            if ((convertView==null)){
                convertView = layoutInflater.inflate(R.layout.item_info,parent,false);
                viewHolder = new ViewHolder();
                viewHolder.tvAccount = (TextView) convertView.findViewById(R.id.tv_account);
                viewHolder.tvPassword = (TextView) convertView.findViewById(R.id.tv_password);
                viewHolder.tvBelong = (TextView) convertView.findViewById(R.id.tv_belong);
                convertView.setTag(viewHolder);
            }else{
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.tvAccount.setText(infoList.get(position).getAccount());
            viewHolder.tvPassword.setText(infoList.get(position).getPassword());
            viewHolder.tvBelong.setText(infoList.get(position).getBelong());
            return convertView;
        }
    }
    class ViewHolder{
        TextView tvAccount;
        TextView tvPassword;
        TextView tvBelong;
    }

    class GetInfoThread implements Runnable{

        @Override
        public void run() {
            try {
                infoList = infoHelper.getAllInfo();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

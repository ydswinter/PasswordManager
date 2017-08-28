package com.example.ydswi.passwordmanager.utils;

import android.content.Context;


import com.example.ydswi.passwordmanager.bean.Info;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ydswi on 2017/8/27.
 */

public class InfoHelper {
    private Context context;

    public InfoHelper(Context context){
        this.context = context;
    }

    public synchronized void insert(Info info) throws IOException {
        FileInputStream input = context.openFileInput("info");
        StringBuilder sb = new StringBuilder("");
        byte[] temp = new byte[1024];
        int len = 0;
        while((len = input.read(temp))>0){
            sb.append(new String(temp,0,len));
        }
        input.close();
        String content = sb.toString();
        FileOutputStream output = context.openFileOutput("info",Context.MODE_PRIVATE );
        content =content+info.getAccount()+" "+info.getPassword()+" "+info.getBelong()+" ";
        output.write(content.getBytes());
        output.close();

    }
    public synchronized void rewrite(List<Info> changedList) throws IOException {
        FileOutputStream output = context.openFileOutput("info",Context.MODE_PRIVATE);
        String content="";
        for(int i = 0;i<changedList.size();i++){
            Info info = changedList.get(i);
            content += info.getAccount()+" "+info.getPassword()+" "+info.getBelong()+" ";
        }
        if (content!=null&&!content.equals("")){
            output.write(content.getBytes());
            output.close();
        }
    }

    public synchronized List<Info> getAllInfo()throws IOException {
        List<Info> infoList = new ArrayList<>();
        FileInputStream input = context.openFileInput("info");
        StringBuilder sb = new StringBuilder("");
        byte[] temp = new byte[1024];
        int len = 0;
        while ((len = input.read(temp)) > 0) {
            sb.append(new String(temp, 0, len));
        }
        input.close();
        if (!sb.equals("")){
            String content = sb.toString();
            String[] allContent = content.split(" ");
            if (allContent.length>=3){
                for (int i = 0; i < allContent.length; i += 3) {
                    Info info = new Info(allContent[i], allContent[i + 1], allContent[i + 2]);
                    infoList.add(info);
                }
            }
        }
        return infoList;
    }
}

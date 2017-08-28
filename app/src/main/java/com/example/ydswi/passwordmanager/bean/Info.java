package com.example.ydswi.passwordmanager.bean;

/**
 * Created by ydswi on 2017/8/27.
 */

public class Info {
    private String account;//账号
    private String password;//密码
    private String belong;//所属公司

    public Info(){

    }
    public Info(String account, String password, String belong) {
        this.account = account;
        this.password = password;
        this.belong = belong;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBelong() {
        return belong;
    }

    public void setBelong(String belong) {
        this.belong = belong;
    }
}

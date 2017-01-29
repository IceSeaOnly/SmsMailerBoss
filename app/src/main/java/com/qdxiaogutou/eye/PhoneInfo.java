package com.qdxiaogutou.eye;

/**
 * Created by Administrator on 2016/12/18.
 */
public class PhoneInfo {
    private String name;
    private String number;
    public PhoneInfo(String name, String number) {
        this.name = name;
        this.number = number;
    }
    public String getName() {
        return name;
    }
    public String getNumber() {
        return number;
    }
}
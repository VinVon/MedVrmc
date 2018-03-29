package com.medvision.vrmed.bean;

/**
 * 训练记录
 * Created by 向文圣 on 2018/3/24.
 */

public class RecordInfo {

    private String name;
    private String number;
    private String hops;
    private String date;
    private String name_2;

    public RecordInfo(String name, String number, String hops, String date, String name_2) {
        this.name = name;
        this.number = number;
        this.hops = hops;
        this.date = date;
        this.name_2 = name_2;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getHops() {
        return hops;
    }

    public void setHops(String hops) {
        this.hops = hops;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName_2() {
        return name_2;
    }

    public void setName_2(String name_2) {
        this.name_2 = name_2;
    }
}

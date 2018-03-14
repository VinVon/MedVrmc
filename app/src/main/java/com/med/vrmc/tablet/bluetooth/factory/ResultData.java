package com.med.vrmc.tablet.bluetooth.factory;

import java.io.Serializable;

/**
 * Created by raytine on 2018/1/24.
 */

public class ResultData implements Serializable{
    private int type;//指令类型 1handler 2 nothing 3 发送下一条指令
    private boolean su;//指令发送成功
    private boolean next;//是否有下一个指令发送
    private String  zl;//发送的数据指令
    private String dec ; //结果描述
    private CommandHolder commandHolder;
    public ResultData() {
    }

    public CommandHolder getCommandHolder() {
        return commandHolder;
    }

    public void setCommandHolder(CommandHolder commandHolder) {
        this.commandHolder = commandHolder;
    }

    public ResultData(int type, boolean su, boolean next, String zl, String dec) {
        this.type = type;
        this.su = su;
        this.next = next;
        this.zl = zl;
        this.dec = dec;
    }

    public String getDec() {
        return dec;
    }

    public void setDec(String dec) {
        this.dec = dec;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean getSu() {
        return su;
    }

    public void setSu(boolean su) {
        this.su = su;
    }

    public String getZl() {
        return zl;
    }

    public void setZl(String zl) {
        this.zl = zl;
    }

    public boolean getNext() {
        return next;
    }

    public void setNext(boolean next) {
        this.next = next;
    }
}

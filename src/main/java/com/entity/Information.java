package com.entity;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author mengtianluofan
 * @version 1.0
 * @description: 账户操作信息
 * TODO
 * @date 2023/6/15 18:46
 */
public class Information {
    private long id;
    private String fromaccount;
    private Date date;
    private String operate;
    private double amount;
    private String toaccount;

    public Information(long id, String ownerid, Date date, String operate, double amount, String toaccount) {
        this.id = id;
        this.fromaccount = ownerid;
        this.date = date;
        this.operate = operate;
        this.amount = amount;
        this.toaccount = toaccount;
    }

    public Information() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFromaccount() {
        return fromaccount;
    }

    public void setFromaccount(String fromaccount) {
        this.fromaccount = fromaccount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getOperate() {
        return operate;
    }

    public void setOperate(String operate) {
        this.operate = operate;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getToaccount() {
        return toaccount;
    }

    public void setToaccount(String toaccount) {
        this.toaccount = toaccount;
    }

    public String setDateAsString(){
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
    }
}

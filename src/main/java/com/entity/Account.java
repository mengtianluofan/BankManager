package com.entity;

import static java.lang.Math.abs;

/**
 * @author mengtianluofan
 * @version 1.0
 * @description: TODO
 * @date 2023/6/6 10:28
 */
public class Account {
    private long id;
    private String password;
    private double amount;
    private double limit;
    private int accountType;
    private String ownerID;
    private String ownerName;

    public Account() {
    }

    public Account(long id, String password, double amount, double limit, int accountType, String ownerID, String ownerName) {
        this.id = id;
        this.password = password;
        this.amount = amount;
        this.limit = limit;
        this.accountType = accountType;
        this.ownerID = ownerID;
        this.ownerName = ownerName;
    }

    public Account(Account oldaccount){
        id = oldaccount.getId();
        password = oldaccount.getPassword();
        amount = oldaccount.getAmount();
        limit = oldaccount.getLimit();
        accountType = oldaccount.getAccountType();;
        ownerID = oldaccount.getOwnerID();;
        ownerName = oldaccount.getOwnerName();
    }
    
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getLimit() {
        return limit;
    }

    public void setLimit(double limit) {
        this.limit = limit;
    }

    public int getAccountType() {
        return accountType;
    }

    public void setAccountType(int accountType) {
        this.accountType = accountType;
    }

    public String getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(String ownerID) {
        this.ownerID = ownerID;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public boolean withdraw(double money) {
        if (money < 0) return false;
        if (accountType == 1) {
            if (money <= amount) {
                amount -= money;
                return true;
            }
        } else{
            if (limit - money >= abs(amount)) {
                amount -= money;
                return true;
            }
        }
        return false;
    }

    public boolean saving(double money) {
        if (money < 0) return false;
        amount += money;
        if (accountType != 1 && amount > 0) {
            amount -= money;
            return false;
        }
        return true;
    }
}

package com.entity;

/**
 * @author mengtianluofan
 * @version 1.0
 * @description: TODO
 * @date 2023/6/6 09:18
 */
public class Admin {
    private long id;
    private String password;
    private int adminType;

    public Admin() {
    }

    public Admin(long id, String password, int adminType) {
        this.id = id;
        this.password = password;
        this.adminType = adminType;
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

    public int getAdminType() {
        return adminType;
    }

    public void setAdminType(int adminType) {
        this.adminType = adminType;
    }
}

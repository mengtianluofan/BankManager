package com.entity;

/**
 * @author mengtianluofan
 * @version 1.0
 * @description: TODO
 * @date 2023/6/6 10:04
 */
public class User {
    private long id;
    private String password;
    private String ownerID;

    public User() {
    }

    public User(long id, String password, String ownerID) {
        this.id = id;
        this.password = password;
        this.ownerID = ownerID;
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

    public String getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(String ownerID) {
        this.ownerID = ownerID;
    }
}

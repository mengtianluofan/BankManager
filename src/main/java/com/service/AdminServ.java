package com.service;

import com.entity.Account;
import com.entity.Admin;
import com.entity.User;

import java.util.List;

/**
 * @author mengtianluofan
 * @version 1.0
 * @description: 管理员功能接口
 * TODO
 * @date 2023/6/18 00:00
 */
public interface AdminServ {
    public Admin Login(String id, String password);
    
    public boolean deleteUser(long id);
    
    public boolean deleteAccountByID(long id);
    
    public boolean deleteAccountByOwnerID(String ownerID);
}

package com.service.impl;

import com.dao.AccountDao;
import com.dao.AdminDao;
import com.dao.UserDao;
import com.dao.impl.AccountDaoImpl;
import com.dao.impl.AdminDaoImpl;
import com.dao.impl.UserDaoImpl;
import com.entity.Account;
import com.entity.Admin;
import com.entity.User;
import com.service.AdminServ;

import java.util.List;

/**
 * @author mengtianluofan
 * @version 1.0
 * @description: 实现接口
 * TODO
 * @date 2023/6/18 00:04
 */
public class AdminServImpl implements AdminServ {
    @Override
    public Admin Login(String id, String password) {
        AdminDao adminDao = new AdminDaoImpl();

        List<Admin> adminList;
        String sql = "select * from admin where id = ? and password = ?";
        String[] param = {id, password};
        
        adminList = adminDao.getAdmin(sql, param);

        if (adminList.size() == 1) return adminList.get(0);
        else return null;
    }

    @Override
    public boolean deleteUser(long id) {
        String sql = "delete from user where id = ?";
        String[] param = {String.valueOf(id)};
        UserDao userDao = new UserDaoImpl();
        return userDao.updateUser(sql, param) > 0;
    }

    @Override
    public boolean deleteAccountByID(long id) {
        String sql = "delete from account where id = ?";
        String[] param = {String.valueOf(id)};
        AccountDao accountDao = new AccountDaoImpl();
        return accountDao.updateAccount(sql, param) > 0;
    }

    @Override
    public boolean deleteAccountByOwnerID(String ownerID) {
        String sql = "delete from account where ownerID = ?";
        String[] param = {ownerID};
        AccountDao accountDao = new AccountDaoImpl();
        return accountDao.updateAccount(sql, param) > 0;
    }
}

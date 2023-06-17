package com.service;

import com.entity.Account;
import com.entity.Person;
import com.entity.User;

/**
 * @author mengtianluofan
 * @version 1.0
 * @description: 账户操作接口类
 * TODO
 * @date 2023/6/7 10:26
 */

public interface AccountServ {
    public Account Register(Account account);

    public Account Login(String id, String password);
    
    //  取钱/信用卡借款
    public Account withdraw(double money, Account account);

    //  存钱/还款
    public Account saving(double money, Account account);
    

}

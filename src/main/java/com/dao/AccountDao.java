package com.dao;

import com.entity.Account;

import java.util.List;

/**
 * @author mengtianluofan
 * @version 1.0
 * @description: accountdao
 * TODO
 * @date 2023/6/12 20:39
 */
public interface AccountDao {
    public int updateAccount(String sql, Object[] param);

    public int insertAccount(Account account);

    public List<Account> getAccount(String sql, String[] param);
    
    public int updateAccountAmount(Account account);
}

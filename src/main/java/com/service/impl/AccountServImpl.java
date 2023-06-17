package com.service.impl;

import com.dao.AccountDao;
import com.dao.InfoDao;
import com.dao.PersonDao;
import com.dao.UserDao;
import com.dao.impl.AccountDaoImpl;
import com.dao.impl.InfoDaoImpl;
import com.dao.impl.PersonDaoImpl;
import com.dao.impl.UserDaoImpl;
import com.entity.Account;
import com.entity.Information;
import com.entity.Person;
import com.entity.User;
import com.service.AccountServ;

import java.util.Date;
import java.util.List;

/**
 * @author mengtianluofan
 * @version 1.0
 * @description: TODO
 * @date 2023/6/13 20:10
 */
public class AccountServImpl implements AccountServ {

    @Override
    public Account Register(Account account) {
        PersonDao personDao = new PersonDaoImpl();
        String sql1 = "select * from person where personid = ?";
        String[] param1 = {account.getOwnerID()};
        List<Person> personList = personDao.getPerson(sql1, param1);
        if (personList.size() == 0) return null;
        
        account.setOwnerName(personList.get(0).getName());
        AccountDao accountDao = new AccountDaoImpl();
        accountDao.insertAccount(account);
        
        String sql2 = "select * from account where ownerID = ?";
        String[] param2 = {account.getOwnerID()};
        List<Account> accountList = accountDao.getAccount(sql2, param2);
                
        return accountList.get(accountList.size() - 1);
    }

    @Override
    public Account Login(String id, String password) {
        AccountDao accountDao = new AccountDaoImpl();

        List<Account> accountList;
        String sql = "select * from account where id = ? and password = ?";
        String[] param = {id, password};

        accountList = accountDao.getAccount(sql, param);

        if (accountList.size() == 1) return accountList.get(0);
        else return null;
    }

    @Override
    public Account withdraw(double money, Account account) {
        String oper = "取款";
        if(account.getAccountType() != 1) oper = "借款";
        
        Date date = new Date();
        Information info = new Information(0, String.valueOf(account.getId()), date, oper, money, "");
        
        if(account.withdraw(money)){
            return setAccountAndInfo(account, info);
        }
        return null;
    }

    @Override
    public Account saving(double money, Account account) {
        String oper = "存款";
        if(account.getAccountType() != 1) oper = "还款";

        Date date = new Date();
        Information info = new Information(0, "", date, oper, money, String.valueOf(account.getId()));
        
        if(account.saving(money)){
            return setAccountAndInfo(account, info);
        }
        return null;
    }

    private Account setAccountAndInfo(Account account, Information info) {
        AccountDao accountDao = new AccountDaoImpl();
        String sql = "update account set amount = ? where id = ?";
        String[] param = {String.valueOf(account.getAmount()), String.valueOf(account.getId())};
        accountDao.updateAccount(sql, param);

        InfoDao infoDao = new InfoDaoImpl();
        infoDao.insertInfo(info);

        return account;
    }
}

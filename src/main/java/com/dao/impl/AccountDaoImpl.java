package com.dao.impl;

import com.dao.AccountDao;
import com.dao.BaseDao;
import com.entity.Account;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author mengtianluofan
 * @version 1.0
 * @description: TODO
 * @date 2023/6/7 10:38
 */
public class AccountDaoImpl extends BaseDao implements AccountDao {
    private Connection conn = null;
    private PreparedStatement pstmt = null;
    private ResultSet rs = null;

    public int updateAccount(String sql, Object[] param) {
        int count = executeSQL(sql, param);
        return count;
    }

    public int insertAccount(Account account) {
        String sql = "insert into Account (password, amount, limit, type, owner, ownerID) values (?, ?, ?, ?, ?)";
        String[] param = new String[6];
        param[0] = account.getPassword();
        param[1] = account.getAmount() + "";
        param[2] = account.getLimit() + "";
        param[3] = account.getAccountType() + "";
        param[4] = account.getOwnerName();
        param[5] = account.getOwnerID() + "";

        int count = executeSQL(sql, param);
        return count;
    }

    public List<Account> getAccount(String sql, String[] param) {
        List<Account> accountList = new ArrayList<>();

        try {
            conn = getConn();
            pstmt = conn.prepareStatement(sql);
            if (param != null) {
                for (int i = 0; i < param.length; i++) {
                    pstmt.setString(i + 1, param[i]);
                }
            }
            rs = pstmt.executeQuery();
            Account account = null;
            while (rs.next()) {
                account = new Account();
                account.setId(rs.getLong(1));
                account.setPassword(rs.getString(2));
                account.setAmount(rs.getDouble(3));
                account.setLimit(rs.getDouble(4));
                account.setAccountType(rs.getInt(5));
                account.setOwnerName(rs.getString(6));
                account.setOwnerID(rs.getLong(7));
                accountList.add(account);
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll(conn, pstmt, rs);
        }
        return accountList;
    }

}

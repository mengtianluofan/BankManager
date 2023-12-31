package com.dao.impl;

import com.dao.BaseDao;
import com.dao.UserDao;
import com.entity.User;

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
 * @date 2023/6/7 10:08
 */
public class UserDaoImpl extends BaseDao implements UserDao {

    private Connection conn = null;
    private PreparedStatement pstmt = null;
    private ResultSet rs = null;

    public int updateUser(String sql, Object[] param) {
        return executeSQL(sql, param);
    }

    public int insertUser(User user) {
        String sql = "insert into user (password, ownerid) values (?, ?)";
        String[] param = new String[2];
        param[0] = user.getPassword();
        param[1] = user.getOwnerID();

        return executeSQL(sql, param);
    }

    public List<User> getUser(String sql, String[] param) {
        List<User> userList = new ArrayList<>();

        try {
            conn = getConn();
            pstmt = conn.prepareStatement(sql);
            if (param != null) {
                for (int i = 0; i < param.length; i++) {
                    pstmt.setString(i + 1, param[i]);
                }
            }
            rs = pstmt.executeQuery();
            User user = null;
            while (rs.next()) {
                user = new User();
                user.setId(rs.getLong(1));
                user.setPassword(rs.getString(2));
                user.setOwnerID(rs.getString(3));
                userList.add(user);
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll(conn, pstmt, rs);
        }
        return userList;
    }
}

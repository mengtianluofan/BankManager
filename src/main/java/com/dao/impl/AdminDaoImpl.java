package com.dao.impl;

import com.dao.BaseDao;
import com.entity.Admin;

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
 * @date 2023/6/7 10:33
 */
public class AdminDaoImpl extends BaseDao {
    private Connection conn = null;
    private PreparedStatement pstmt = null;
    private ResultSet rs = null;

    public int updateAdmin(String sql, Object[] param) {
        int count = executeSQL(sql, param);
        return count;
    }

    public int insertAdmin(Admin admin) {
        String sql = "insert into admin (id, password, admintype, ownerid) values (?, ?, ?, ?)";
        String[] param = new String[4];
        param[0] = admin.getId() + "";
        param[1] = admin.getPassword();
        param[2] = admin.getAdminType() + "";
        param[3] = admin.getOwnerID() + "";

        int count = executeSQL(sql, param);
        return count;
    }

    public List<Admin> getAdmin(String sql, String[] param) {
        List<Admin> adminList = new ArrayList<>();

        try {
            conn = getConn();
            pstmt = conn.prepareStatement(sql);
            if (param != null) {
                for (int i = 0; i < param.length; i++) {
                    pstmt.setString(i + 1, param[i]);
                }
            }
            rs = pstmt.executeQuery();
            Admin admin = null;
            while (rs.next()) {
                admin = new Admin();
                admin.setId(rs.getLong(1));
                admin.setPassword(rs.getString(2));
                admin.setAdminType(rs.getInt(3));
                admin.setOwnerID(rs.getLong(4));
                adminList.add(admin);
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll(conn, pstmt, rs);
        }
        return adminList;
    }
}

package com.dao.impl;

import com.dao.BaseDao;
import com.dao.InfoDao;
import com.entity.Information;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author mengtianluofan
 * @version 1.0
 * @description: TODO
 * @date 2023/6/15 19:07
 */
public class InfoDaoImpl extends BaseDao implements InfoDao{
    private Connection conn = null;
    private PreparedStatement pstmt = null;
    private ResultSet rs = null;
    
    @Override
    public int updateInfo(String sql, Object[] param) {
        return executeSQL(sql, param);
    }

    @Override
    public int insertInfo(Information information) {
        String sql = "insert into information (fromaccount, time, operate, amount, toaccount) values (?, ?, ?, ?, ?)";
        
        long time = information.getDate().getTime();
        Timestamp timestamp = new Timestamp(time);

        String[] param = new String[5];
        param[0] = information.getFromaccount();
        param[1] = String.valueOf(timestamp);
        param[2] = information.getOperate();
        param[3] = String.valueOf(information.getAmount());
        param[4] = information.getToaccount();

        return executeSQL(sql, param);
    }

    @Override
    public List<Information> getInfo(String sql, String[] param) {
        List<Information> infoList = new ArrayList<>();

        try {
            conn = getConn();
            pstmt = conn.prepareStatement(sql);
            if (param != null) {
                for (int i = 0; i < param.length; i++) {
                    pstmt.setString(i + 1, param[i]);
                }
            }
            rs = pstmt.executeQuery();
            Information user = null;
            while (rs.next()) {
                user = new Information();
                user.setId(rs.getLong(1));
                user.setFromaccount(rs.getString(2));
                user.setDate(rs.getTimestamp(3));
                user.setOperate(rs.getString(4));
                user.setAmount(rs.getDouble(5));
                user.setToaccount(rs.getString(6));
                infoList.add(user);
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll(conn, pstmt, rs);
        }
        return infoList;
    }
}

package com.dao.impl;

import com.dao.BaseDao;
import com.dao.PersonDao;
import com.entity.Person;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author mengtianluofan
 * @version 1.0
 * @description: TODO
 * @date 2023/6/7 09:16
 */
public class PersonDaoImpl extends BaseDao implements PersonDao  {

    private Connection conn = null;
    private PreparedStatement pstmt = null;
    private ResultSet rs = null;

    public int updatePerson(String sql, Object[] param) {
        int count = executeSQL(sql, param);
        return count;
    }

    public int insertPerson(Person person) {
        String sql = "insert into person (personid, name, gender, tel) " +
                "values (?, ?, ?, ?)";
        String[] param = new String[4];
        param[0] = person.getPersonID();
        param[1] = person.getName();
        param[2] = person.getGender() + "";
        param[3] = person.getTel() + "";

        int count;
        count = executeSQL(sql, param);
        return count;
    }

    public List<Person> getPerson(String sql, String[] param) {
        List<Person> personList = new ArrayList<>();

        try {
            conn = getConn();
            pstmt = conn.prepareStatement(sql);
            if (param != null) {
                for (int i = 0; i < param.length; i++) {
                    pstmt.setString(i + 1, param[i]);
                }
            }
            rs = pstmt.executeQuery();
            Person person = null;
            while (rs.next()) {
                person = new Person();
                person.setPersonID(rs.getString(1));
                person.setName(rs.getString(2));
                person.setGender(rs.getInt(3));
                person.setTel(rs.getLong(4));
                personList.add(person);
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll(conn, pstmt, rs);
        }
        return personList;
    }
}

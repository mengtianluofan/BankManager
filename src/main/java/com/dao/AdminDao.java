package com.dao;

import com.entity.Admin;

import java.util.List;

/**
 * @author mengtianluofan
 * @version 1.0
 * @description: admindao
 * TODO
 * @date 2023/6/12 20:39
 */
public interface AdminDao {
    public int updateAdmin(String sql, Object[] param);

    public int insertAdmin(Admin admin);

    public List<Admin> getAdmin(String sql, String[] param);
}

package com.dao;

import com.entity.User;

import java.util.List;

/**
 * @author mengtianluofan
 * @version 1.0
 * @description: UserDao
 * TODO
 * @date 2023/6/12 20:39
 */
public interface UserDao {
    public int updateUser(String sql, Object[] param);

    public int insertUser(User user);

    public List<User> getUser(String sql, String[] param);
}

package com.dao;

import com.entity.Person;

import java.util.List;

/**
 * @author mengtianluofan
 * @version 1.0
 * @description: 个人信息JDBC接口
 * TODO
 * @date 2023/6/12 20:14
 */
public interface PersonDao {
    int updatePerson(String sql, Object[] param);

    int insertPerson(Person person);

    List<Person> getPerson(String sql, String[] param);
}

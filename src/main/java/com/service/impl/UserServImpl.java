package com.service.impl;

import com.dao.PersonDao;
import com.dao.UserDao;
import com.dao.impl.PersonDaoImpl;
import com.dao.impl.UserDaoImpl;
import com.entity.Person;
import com.entity.User;
import com.service.UserServ;

import java.util.List;

/**
 * @author mengtianluofan
 * @version 1.0
 * @description: 用户操作
 * TODO
 * @date 2023/6/12 18:42
 */
public class UserServImpl implements UserServ {

    @Override
    public User Register(User user, Person person) {
        PersonDao personDao = new PersonDaoImpl();
        personDao.insertPerson(person);

        UserDao userDao = new UserDaoImpl();
        userDao.insertUser(user);
        
        String sql = "select * from user where ownerid = ?";
        String[] param = {String.valueOf(user.getOwnerID())};
        List<User> userList = userDao.getUser(sql, param);
        
        return userList.get(0);
    }
}

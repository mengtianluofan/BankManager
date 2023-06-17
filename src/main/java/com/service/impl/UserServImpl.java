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
        String sql1 = "select * from person where personid = ?";
        String[] param1 = {person.getPersonID()};
        List<Person> personList = personDao.getPerson(sql1, param1);
        if (personList.size() != 0) return null;

        personDao.insertPerson(person);
        UserDao userDao = new UserDaoImpl();
        userDao.insertUser(user);

        String sql2 = "select * from user where ownerid = ?";
        String[] param = {user.getOwnerID()};
        List<User> userList = userDao.getUser(sql2, param);

        return userList.get(0);
    }

    @Override
    public User Login(String id, String password) {
        UserDao userDao = new UserDaoImpl();

        List<User> userList;
        String sql;
        String[] param = {id, password};
        
        if(id.length() == 18){
            sql = "select * from user where ownerid = ? and password = ?";
        }
        else{
            sql = "select * from user where id = ? and password = ?";
        }
        userList = userDao.getUser(sql, param);

        if (userList.size() == 1) return userList.get(0);
        else return null;
    }


}

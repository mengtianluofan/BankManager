package com.service;

import com.entity.Person;
import com.entity.User;

/**
 * @author mengtianluofan
 * @version 1.0
 * @description: 用户操作接口类
 * TODO
 * @date 2023/6/8 08:55
 */
public interface UserServ {
    public User Register(User user, Person person);
    
    public User Login(String id, String password);
}

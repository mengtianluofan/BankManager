package com.dao;

import com.entity.Information;
import com.entity.User;

import java.util.List;

/**
 * @author mengtianluofan
 * @version 1.0
 * @description: Information类数据库处理
 * TODO
 * @date 2023/6/15 18:53
 */
public interface InfoDao {
    public int updateInfo(String sql, Object[] param);

    public int insertInfo(Information information);

    public List<Information> getInfo(String sql, String[] param);
    
}

package com.zlzkj.app.mapper;

import com.zlzkj.app.model.User;

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    User selectByPrimaryKey(Integer id);
    
    int updateByPrimaryKey(User record);
}
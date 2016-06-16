package com.zlzkj.app.mapper;

import com.zlzkj.app.model.Level;

public interface LevelMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Level record);

    Level selectByPrimaryKey(Integer id);

    int updateByPrimaryKey(Level record);
}
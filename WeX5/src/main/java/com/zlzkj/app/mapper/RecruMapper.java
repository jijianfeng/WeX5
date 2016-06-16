package com.zlzkj.app.mapper;

import com.zlzkj.app.model.Recru;

public interface RecruMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Recru record);

    Recru selectByPrimaryKey(Integer id);

    int updateByPrimaryKey(Recru record);
}
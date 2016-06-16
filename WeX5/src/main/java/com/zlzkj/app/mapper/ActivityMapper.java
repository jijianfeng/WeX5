package com.zlzkj.app.mapper;

import com.zlzkj.app.model.Activity;

public interface ActivityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Activity record);

    Activity selectByPrimaryKey(Integer id);

    int updateByPrimaryKey(Activity record);
}
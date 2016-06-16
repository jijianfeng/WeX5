package com.zlzkj.app.mapper;

import com.zlzkj.app.model.News;

public interface NewsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(News record);

    News selectByPrimaryKey(Integer id);

    int updateByPrimaryKey(News record);
}
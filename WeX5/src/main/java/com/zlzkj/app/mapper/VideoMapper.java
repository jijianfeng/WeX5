package com.zlzkj.app.mapper;

import com.zlzkj.app.model.Video;

public interface VideoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Video record);

    Video selectByPrimaryKey(Integer id);

    int updateByPrimaryKey(Video record);
}
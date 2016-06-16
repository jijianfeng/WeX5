package com.zlzkj.app.mapper;

import com.zlzkj.app.model.Chapter;

public interface ChapterMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Chapter record);

    Chapter selectByPrimaryKey(Integer id);

    int updateByPrimaryKey(Chapter record);
}
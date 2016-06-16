package com.zlzkj.app.mapper;

import com.zlzkj.app.model.Resume;

public interface ResumeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Resume record);

    Resume selectByPrimaryKey(Integer id);

    int updateByPrimaryKey(Resume record);
}
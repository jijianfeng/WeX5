package com.zlzkj.app.mapper;

import com.zlzkj.app.model.Question;

public interface QuestionMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Question record);

    Question selectByPrimaryKey(Integer id);

    int updateByPrimaryKey(Question record);
}
package com.zlzkj.app.mapper;

import com.zlzkj.app.model.Text;

public interface TextMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Text record);

    int insertSelective(Text record);

    Text selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Text record);

    int updateByPrimaryKey(Text record);
}
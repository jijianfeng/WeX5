package com.zlzkj.app.mapper;

import com.zlzkj.app.model.Comment;

public interface CommentMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Comment record);

    Comment selectByPrimaryKey(Integer id);

    int updateByPrimaryKey(Comment record);
}
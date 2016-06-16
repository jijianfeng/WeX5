package com.zlzkj.app.mapper;

import com.zlzkj.app.model.ActionNode;
import java.util.List;

public interface ActionNodeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ActionNode record);

    ActionNode selectByPrimaryKey(Integer id);

    List<ActionNode> selectAll();

    int updateByPrimaryKey(ActionNode record);
}
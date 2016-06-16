package com.zlzkj.app.mapper;

import com.zlzkj.app.model.RoleNode;
import java.util.List;

public interface RoleNodeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(RoleNode record);

    RoleNode selectByPrimaryKey(Integer id);

    List<RoleNode> selectAll();

    int updateByPrimaryKey(RoleNode record);
}
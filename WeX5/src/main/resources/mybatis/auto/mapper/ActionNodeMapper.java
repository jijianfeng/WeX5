package mybatis.auto.mapper;

import mybatis.auto.model.ActionNode;

public interface ActionNodeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ActionNode record);

    int insertSelective(ActionNode record);

    ActionNode selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ActionNode record);

    int updateByPrimaryKey(ActionNode record);
}
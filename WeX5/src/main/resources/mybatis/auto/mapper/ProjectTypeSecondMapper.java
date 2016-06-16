package mybatis.auto.mapper;

import mybatis.auto.model.ProjectTypeSecond;

public interface ProjectTypeSecondMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ProjectTypeSecond record);

    int insertSelective(ProjectTypeSecond record);

    ProjectTypeSecond selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ProjectTypeSecond record);

    int updateByPrimaryKey(ProjectTypeSecond record);
}
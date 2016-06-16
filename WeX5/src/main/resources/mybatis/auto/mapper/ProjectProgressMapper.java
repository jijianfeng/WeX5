package mybatis.auto.mapper;

import mybatis.auto.model.ProjectProgress;
import mybatis.auto.model.ProjectProgressWithBLOBs;

public interface ProjectProgressMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ProjectProgressWithBLOBs record);

    int insertSelective(ProjectProgressWithBLOBs record);

    ProjectProgressWithBLOBs selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ProjectProgressWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(ProjectProgressWithBLOBs record);

    int updateByPrimaryKey(ProjectProgress record);
}
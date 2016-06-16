package mybatis.auto.mapper;

import mybatis.auto.model.ProjectLeader;
import mybatis.auto.model.ProjectLeaderWithBLOBs;

public interface ProjectLeaderMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ProjectLeaderWithBLOBs record);

    int insertSelective(ProjectLeaderWithBLOBs record);

    ProjectLeaderWithBLOBs selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ProjectLeaderWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(ProjectLeaderWithBLOBs record);

    int updateByPrimaryKey(ProjectLeader record);
}
package mybatis.auto.mapper;

import mybatis.auto.model.UserAll;

public interface UserAllMapper {
    int deleteByPrimaryKey(Integer id);

	int insert(UserAll record);

	int insertSelective(UserAll record);

	UserAll selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(UserAll record);

	int updateByPrimaryKey(UserAll record);

	int deleteByPrimaryKey(Integer id);

    int insert(UserAll record);

    int insertSelective(UserAll record);

    UserAll selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserAll record);

    int updateByPrimaryKey(UserAll record);
}
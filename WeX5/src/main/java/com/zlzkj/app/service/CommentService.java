package com.zlzkj.app.service;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zlzkj.app.mapper.CommentMapper;
import com.zlzkj.app.model.Comment;
import com.zlzkj.app.model.User;
import com.zlzkj.core.mybatis.SqlRunner;
import com.zlzkj.core.sql.Row;
import com.zlzkj.core.sql.SQLBuilder;
import com.zlzkj.core.util.Fn;

@Service
@Transactional
public class CommentService {

	@Autowired
	private CommentMapper mapper;
	
	@Autowired
	private SqlRunner sqlRunner;
	
	@Autowired
	private UserService userService;
	
	public Integer delete(Integer id){
		return mapper.deleteByPrimaryKey(id);
	}
	
	public Integer update(Comment entity) throws Exception{
		
		return mapper.updateByPrimaryKey(entity);
	}
	
	public Integer save(Comment entity) throws Exception{
		return mapper.insert(entity);
	}
	
	public Comment findById(Integer id){
		return mapper.selectByPrimaryKey(id);
	}
	
	/**
	 * 获取评论数据
	 * @param where
	 * @param pageMap
	 * @param number
	 * @return
	 */
	public List<Row> getCommentUIGridData(Map<String, Object> where,
			Map<String, String> pageMap,int number) {
		SQLBuilder sqlBuilder = SQLBuilder.getSQLBuilder(Comment.class);
		String sql = sqlBuilder
				.fields("id as FID,content as fContent,add_time as fTime,like_number as fNumber,user_id as userId ,user_type as userType")
				.where(where)
				.order("like_number", "desc")
				.page(1, number)
				.buildSql();
		//System.out.println("嵇建峰"+sql);
		List<Row> list = sqlRunner.select(sql);
		//{"fID":"001","fName":"道哥哥K41Q","fImg":"./img/audio1.png","fSource":"广东广州","fNumber":"29","fContent":"礼仪之邦有希望了。","fTime":"1小时"},
		for(Row row :list){
			//System.out.println(row.getString("userId")+":::"+row.getInt("userType"));
			User user = userService.findById(userService.findIdByThree(row.getString("userId"), row.getInt("userType")));
			row.put("fImg", user.getImageUrl());
			row.put("fName", user.getNickName());
			row.put("fTime", Fn.date(Integer.valueOf(row.getString("fTime")), "yyyy-MM-dd HH:mm:ss"));
			row.put("fIsChecked", "0");
		}
		return list;
	}
	
}

package com.zlzkj.app.service;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zlzkj.app.mapper.ResumeMapper;
import com.zlzkj.app.model.Resume;
import com.zlzkj.app.model.User;
import com.zlzkj.app.util.UIUtils;
import com.zlzkj.core.mybatis.SqlRunner;
import com.zlzkj.core.sql.Row;
import com.zlzkj.core.sql.SQLBuilder;
import com.zlzkj.core.util.Fn;

@Service
@Transactional
public class ResumeService {

	@Autowired
	private ResumeMapper mapper;
	
	@Autowired
	private SqlRunner sqlRunner;
	
	@Autowired
	private UserService userService;
	
	public Integer delete(Integer id){
		return mapper.deleteByPrimaryKey(id);
	}
	
	public Integer update(Resume entity) throws Exception{
		
		return mapper.updateByPrimaryKey(entity);
	}
	
	public Integer save(Resume entity) throws Exception{
		return mapper.insert(entity);
	}
	
	public Resume findById(Integer id){
		return mapper.selectByPrimaryKey(id);
	}
	
	/**
	 * 获取招聘数据
	 * @param where
	 * @param pageMap
	 * @param number
	 * @return
	 */
	public List<Row> getResumeUIGridData(Map<String, Object> where,
			Map<String, String> pageMap,int number) {
		SQLBuilder sqlBuilder = SQLBuilder.getSQLBuilder(Resume.class);
		String sql = sqlBuilder
				.fields("*")
				.where(where)
				.order("id", "desc")
				.page(1, number)
				.buildSql();
		List<Row> list = sqlRunner.select(sql);
		for(Row row :list){
			User user = userService.findById(row.getInt("addUser"));
			row.put("imageUrl", user.getImageUrl());
			row.put("addUser", user.getNickName());
			String content = UIUtils.Html2Text(row.getString("userIntro"));
			row.put("userIntro", content.length()>20?content.substring(0, 20)+"...":content);
			row.put("addTime", Fn.date(Integer.valueOf(row.getString("addTime")), "yyyy-MM-dd HH:mm:ss"));
		}
		return list;
	}
	
}

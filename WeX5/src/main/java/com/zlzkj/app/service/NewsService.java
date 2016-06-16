package com.zlzkj.app.service;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zlzkj.app.mapper.NewsMapper;
import com.zlzkj.app.model.News;
import com.zlzkj.app.model.User;
import com.zlzkj.app.util.CheckData;
import com.zlzkj.app.util.CommonUtil;
import com.zlzkj.app.util.UIUtils;
import com.zlzkj.core.mybatis.SqlRunner;
import com.zlzkj.core.sql.Row;
import com.zlzkj.core.sql.SQLBuilder;
import com.zlzkj.core.util.Fn;

@Service
@Transactional
public class NewsService {

	@Autowired
	private NewsMapper mapper;
	
	@Autowired
	private SqlRunner sqlRunner;
	
	@Autowired
	private UserService userService;
	
	public Integer delete(Integer id){
		return mapper.deleteByPrimaryKey(id);
	}
	
	public Integer update(News entity) throws Exception{
		
		return mapper.updateByPrimaryKey(entity);
	}
	
	public Integer save(News entity) throws Exception{
		return mapper.insert(entity);
	}
	
	public News findById(Integer id){
		return mapper.selectByPrimaryKey(id);
	}
	
	/**
	 * 获取资讯list
	 * @param where
	 * @param pageMap
	 * @return
	 */
	public Map<String, Object> getUIGridData(Map<String, Object> where,
			Map<String, String> pageMap) {
		SQLBuilder sqlBuilder = SQLBuilder.getSQLBuilder(News.class);
		String sql = sqlBuilder
				.fields("*")
				.where(where)
				.parseUIPageAndOrder(pageMap)
				.buildSql();
		List<Row> list = sqlRunner.select(sql);
		for (Row row:list) {
//			User user = userService.findById(Integer.valueOf(row.getString("userId")));
//			row.put("userId", user.getNickName());
			row.put("userId", userService.findNameByid(row.getString("userId")));
			row.put("addTime", Fn.date(Integer.valueOf(row.getString("addTime")), "yyyy-MM-dd HH:mm:ss"));
//			System.out.println(row.getString("content").replace("\"", ""));
//			System.out.println(UIUtils.Html2Text(row.getString("content").replace("\"", "")));
			row.put("title", row.getString("title").length()>20?row.getString("title").substring(0, 20)+"...":row.getString("title"));
			String content = UIUtils.Html2Text(row.getString("content"));
			row.put("content", content.length()>20?content.substring(0, 20)+"...":content);
		}
		//获取总条数
		String countSql = sqlBuilder.fields("count(*)").where(where).buildSql();
		Integer count = sqlRunner.count(countSql);
		return UIUtils.getGridData(count, list);
	}
	
	/**
	 * 获取图片资讯list
	 * @param where
	 * @param pageMap
	 * @return
	 */
	public Map<String, Object> getImageUIGridData(Map<String, Object> where,
			Map<String, String> pageMap) {
		SQLBuilder sqlBuilder = SQLBuilder.getSQLBuilder(News.class);
		String sql = sqlBuilder
				.fields("*")
				.where(where)
				.parseUIPageAndOrder(pageMap)
				.buildSql();
		List<Row> list = sqlRunner.select(sql);
		for (Row row:list) {
			row.put("userId", userService.findNameByid(row.getString("userId")));
			row.put("addTime", Fn.date(Integer.valueOf(row.getString("addTime")), "yyyy-MM-dd HH:mm:ss"));
			row.put("content", UIUtils.Html2Text(row.getString("content")));
		}
		//获取总条数
		String countSql = sqlBuilder.fields("count(*)").where(where).buildSql();
		Integer count = sqlRunner.count(countSql);
		return UIUtils.getGridData(count, list);
	}
}

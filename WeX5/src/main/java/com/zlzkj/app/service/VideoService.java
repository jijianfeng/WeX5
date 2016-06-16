package com.zlzkj.app.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zlzkj.app.mapper.VideoMapper;
import com.zlzkj.app.model.Video;
import com.zlzkj.app.util.UIUtils;
import com.zlzkj.core.mybatis.SqlRunner;
import com.zlzkj.core.sql.Row;
import com.zlzkj.core.sql.SQLBuilder;
import com.zlzkj.core.util.Fn;

@Service
@Transactional
public class VideoService {

	@Autowired
	private VideoMapper mapper;
	
	@Autowired
	private SqlRunner sqlRunner;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ChapterService chapterService;
	
	public Integer delete(Integer id){
		return mapper.deleteByPrimaryKey(id);
	}
	
	public Integer update(Video entity) throws Exception{
		
		return mapper.updateByPrimaryKey(entity);
	}
	
	public Integer save(Video entity) throws Exception{
		return mapper.insert(entity);
	}
	
	public Video findById(Integer id){
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
		SQLBuilder sqlBuilder = SQLBuilder.getSQLBuilder(Video.class);
		String sql = sqlBuilder
				.fields("*")
				.where(where)
				.parseUIPageAndOrder(pageMap)
				.buildSql();
		List<Row> list = sqlRunner.select(sql);
		for (Row row:list) {
			row.put("userId", userService.findNameByid(row.getString("userId")));
			row.put("addTime", Fn.date(Integer.valueOf(row.getString("addTime")), "yyyy-MM-dd HH:mm:ss"));
			row.put("videoIntro", UIUtils.Html2Text(row.getString("videoIntro")));
			row.put("videoType",chapterService.findById(row.getInt("videoType")).getTitle() );
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
		SQLBuilder sqlBuilder = SQLBuilder.getSQLBuilder(Video.class);
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

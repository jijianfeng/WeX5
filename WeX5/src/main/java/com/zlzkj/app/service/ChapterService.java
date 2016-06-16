package com.zlzkj.app.service;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.zlzkj.app.mapper.ChapterMapper;
import com.zlzkj.app.model.Chapter;
import com.zlzkj.app.util.UIUtils;
import com.zlzkj.core.mybatis.SqlRunner;
import com.zlzkj.core.sql.Row;
import com.zlzkj.core.sql.SQLBuilder;
import com.zlzkj.core.util.Fn;

@Service
@Transactional
public class ChapterService {

	@Autowired
	private ChapterMapper mapper;
	
	@Autowired
	private SqlRunner sqlRunner;
	
	@Autowired
	private UserService userService;
	
	public Integer delete(Integer id){
		return mapper.deleteByPrimaryKey(id);
	}
	
	public Integer update(Chapter entity) throws Exception{
		
		return mapper.updateByPrimaryKey(entity);
	}
	
	public Integer save(Chapter entity) throws Exception{
		return mapper.insert(entity);
	}
	
	public Chapter findById(Integer id){
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
		SQLBuilder sqlBuilder = SQLBuilder.getSQLBuilder(Chapter.class);
		String sql = sqlBuilder
				.fields("*")
				.where(where)
				.parseUIPageAndOrder(pageMap)
				.buildSql();
		List<Row> list = sqlRunner.select(sql);
		for (Row row:list) {
			row.put("userId", userService.findNameByid(row.getString("userId")));
			row.put("addTime", Fn.date(Integer.valueOf(row.getString("addTime")), "yyyy-MM-dd HH:mm:ss"));
		}
		//获取总条数
		String countSql = sqlBuilder.fields("count(*)").where(where).buildSql();
		Integer count = sqlRunner.count(countSql);
		return UIUtils.getGridData(count, list);
	}
	
	/**
	 * 章节列表
	 * @param where
	 * @return
	 */
	public List<Row> getList() {
		SQLBuilder sqlBuilder = SQLBuilder.getSQLBuilder(Chapter.class);
		String sql = sqlBuilder
				.fields("id,title as text")   //这里约定前端grid需要显示多少个具体列，也可以全部*
				.order("id", "asc")
				.buildSql();
		List<Row> list = sqlRunner.select(sql);
		Row node = new Row();
//		node.put("id", 0);
//		node.put("text", "[默认]研究生部");
//		list.add(0,node);
		return list;
	}
}

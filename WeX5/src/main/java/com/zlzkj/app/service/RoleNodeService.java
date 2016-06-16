package com.zlzkj.app.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zlzkj.app.mapper.RoleNodeMapper;
import com.zlzkj.app.model.ActionNode;
import com.zlzkj.app.model.RoleNode;
import com.zlzkj.app.util.CheckData;
import com.zlzkj.app.util.CommonUtil;
import com.zlzkj.app.util.StringUtil;
import com.zlzkj.app.util.UIUtils;
import com.zlzkj.core.mybatis.SqlRunner;
import com.zlzkj.core.sql.Row;
import com.zlzkj.core.sql.SQLBuilder;

@Service
@Transactional
public class RoleNodeService {

	@Autowired
	private RoleNodeMapper mapper;
	
	@Autowired
	private SqlRunner sqlRunner;
	
	@Autowired
	private ActionNodeService actionNodeService;
	
	public Integer delete(Integer id){
		return mapper.deleteByPrimaryKey(id);
	}
	
	public Integer deleteAllByRoleId(Integer id){
		SQLBuilder sqlBuilder = SQLBuilder.getSQLBuilder(RoleNode.class);
		String sql = sqlBuilder
				.fields("id")   //这里约定前端grid需要显示多少个具体列，也可以全部*
				.where("role_id="+id)
				.buildSql();
		List<Row> list = sqlRunner.select(sql);
		String pass = "";
		for (Map<String,Object> row : list){
			pass = row.get("id").toString();
			//System.out.println(pass);
			delete(Integer.valueOf(pass));
		}
		return mapper.deleteByPrimaryKey(id);
	}
	
	public Integer update(RoleNode entity) throws Exception{
		return mapper.updateByPrimaryKey(entity);
	}
	
	public Integer save(RoleNode entity) throws Exception{
		
		return mapper.insert(entity);
	}
	
	public List<RoleNode> findAll() {
		return mapper.selectAll();
	}
	
	public RoleNode findById(Integer id){
		return mapper.selectByPrimaryKey(id);
	}
	
	
	public Map<String, Object> getUIGridData(Map<String, Object> where, Map<String, String> pageMap) {
		SQLBuilder sqlBuilder = SQLBuilder.getSQLBuilder(RoleNode.class);
		String sql = sqlBuilder
				.fields("*")   //这里约定前端grid需要显示多少个具体列，也可以全部*
				.where(where)
				.parseUIPageAndOrder(pageMap)
				.order("id", "asc")
				.buildSql();
		List<Row> list = sqlRunner.select(sql);

		String countSql = sqlBuilder.fields("count(*)").where(where).buildSql();
		Integer count = sqlRunner.count(countSql);
		return UIUtils.getGridData(count, list);
	}
	
	public boolean findByRoleId(Integer roleId, Integer nodeId) {
		SQLBuilder sqlBuilder = SQLBuilder.getSQLBuilder(RoleNode.class);
		String sql = sqlBuilder
				.fields("*")
				.where("role_id="+roleId)
				.where("node_id="+nodeId)
				.buildSql();
		return sqlRunner.select(sql).isEmpty();
	}
	
//	public List<Row> findByRole(Integer roleId){
//		SQLBuilder sqlBuilder = SQLBuilder.getSQLBuilder(RoleNode.class);
//		String sql = sqlBuilder
//				.fields("*")   //这里约定前端grid需要显示多少个具体列，也可以全部*
//				.where("role_id = "+roleId)
//				.order("id", "asc")
//				.buildSql();
//		List<Row> list = sqlRunner.select(sql);
//		for(Row row : list){
//			ActionNode actionNode = actionNodeService.findById(Integer.valueOf(row.get("nodeId").toString()));
//			row.put("url", actionNode.getUrl());
//		}
//		return list;
//	}
}

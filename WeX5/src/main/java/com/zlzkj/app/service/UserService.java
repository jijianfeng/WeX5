package com.zlzkj.app.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.zlzkj.core.util.Fn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zlzkj.app.mapper.UserMapper;
//import com.zlzkj.app.model.Department;
import com.zlzkj.app.model.Role;
import com.zlzkj.app.model.User;
import com.zlzkj.app.util.CheckData;
import com.zlzkj.app.util.CommonUtil;
import com.zlzkj.app.util.JsonUtil;
import com.zlzkj.app.util.StringUtil;
import com.zlzkj.app.util.UIUtils;
import com.zlzkj.core.mybatis.SqlRunner;
import com.zlzkj.core.sql.Row;
import com.zlzkj.core.sql.SQLBuilder;

@Service
@Transactional
public class UserService {

	@Autowired
	private UserMapper mapper;
	
	@Autowired
	private SqlRunner sqlRunner;

	@Autowired
	private RoleService roleService;
	
	public Integer delete(Integer id){
		return mapper.deleteByPrimaryKey(id);
	}
	
	public Integer update(User entity) throws Exception{
		
		return mapper.updateByPrimaryKey(entity);
	}
	
	public Integer save(User entity) throws Exception{
		
		return mapper.insert(entity);
	}
	
	public Integer add(User entity) throws Exception{
		
		mapper.insert(entity);
		return (Integer)entity.getId();
	}
	
	
	public User findById(Integer id){
		return mapper.selectByPrimaryKey(id);
	}
	
	public Integer find(String name){
		SQLBuilder sqlBuilder = SQLBuilder.getSQLBuilder(User.class);
		String sql = sqlBuilder
				.fields("*")   //这里约定前端grid需要显示多少个具体列，也可以全部*
				.where("user_phone ='"+name+"'")
				.order("id", "asc")
				.buildSql();
		List<Row> list = sqlRunner.select(sql);
		Integer id = 0;
		for (Map<String,Object> row : list){
			id = Integer.valueOf(row.get("id").toString());
		}
		return id;
	}
	
	public boolean deleteWhere(Map<String, Object> where){
		SQLBuilder sqlBuilder = SQLBuilder.getSQLBuilder(User.class);
		String sql = sqlBuilder
				.fields("*")   //这里约定前端grid需要显示多少个具体列，也可以全部*
				.where(where)
				.order("id", "asc")
				.buildSql();
		List<Row> list = sqlRunner.select(sql);
		int i = 0;
		for (Map<String,Object> row : list){
			delete(Integer.valueOf(row.get("id").toString()));
			i++;
		}
		if(i==list.size()){
			return true;
		}
		else{
			return false;
		}
	}
	
	public Map<String, Object> getUIGridData(Map<String, Object> where,
			Map<String, String> pageMap,int userType) {
		SQLBuilder sqlBuilder = SQLBuilder.getSQLBuilder(User.class);
		String sql = sqlBuilder
				.fields("*")
//				.fields("distinct(User.university_id) AS universityId,User.id AS id , University.university_name AS universityName ,User.login_time As loginTime ,"
//						+ "User.user_name AS userName ,User.user_password AS userPassword ,User.nick_name AS nickName ,User.user_remark AS userRemark,"
//						+ "User.send_code AS sendCode, User.is_value AS isValue ,User.is_admin AS isAdmin ,User.user_sex AS userSex ,User.role_id AS roleId,"
//						+ "User.user_phone AS userPhone")
//				.join(University.class, "User.university_id = University.id","left")
//				.where("University.university_name like '%"+universityName+"%'")
				//.where("University.university_name like '%"+universityName+"%'")
				.where(where)
				.parseUIPageAndOrder(pageMap)
				.buildSql();
		List<Row> list = sqlRunner.select(sql);
		for (Row row :list) {
			if(row.getString("userSex").equals("1")){
				row.put("userSex", "男");
			}
			else{
				row.put("userSex", "女");
			}
			if(!StringUtil.isEmpty(row.get("loginTime"))){
				row.put("loginTime", Fn.date(Integer.valueOf(row.get("loginTime").toString()), "yyyy-MM-dd"));
			}
			else{
				row.put("loginTime", "尚无登录信息");
			}
			Role role = roleService.findById(Integer.valueOf(row.getString("roleId")));
			row.put("roleId", role.getName());
		}
		String countSql = sqlBuilder.fields("count(*)")
				//.join(University.class, "User.university_id = University.id","left")
				.where(where).buildSql();
		Integer count = sqlRunner.count(countSql);
		return UIUtils.getGridData(count, list);
	}
	
	public Map<String, Object> getDetailUIGridData(Integer id){
		SQLBuilder sqlBuilder = SQLBuilder.getSQLBuilder(User.class);
		String sql = sqlBuilder
				.fields("*")   
						//这里约定前端grid需要显示多少个具体列，也可以全部*
				/*.where("is_verify = 1")*/
				.where("User.id = " +id)
//				.order("id", "asc")
//				.join(AdminRole.class, "AdminRole.user_id = User.id","LEFT")
//				.join(Role.class, "Role.id = AdminRole.role_id","LEFT")
				.buildSql();
		List<Row> list = sqlRunner.select(sql);
		for (Map<String,Object> row : list) {
			if(!StringUtil.isEmpty(row.get("loginTime"))){
				row.put("loginTime", Fn.date(Integer.valueOf(row.get("loginTime").toString()), "yyyy-MM-dd"));
			}
			else{
				row.put("loginTime", "尚无登录信息");
			}
			row.put("userSex", row.get("userSex").toString().equals("0")?"女":"男");
			Role role = roleService.findById(Integer.valueOf(row.get("roleId").toString()));
			row.put("roleId", role.getName());
		}
		String[] nameArray = {"昵称","手机号码","年龄","性别","上次登录时间","身份","有无激活"};
		String[] valueArray = {"nickName","userPhone","userAge","userSex","loginTime","roleId","status"};
		List<Map<String, Object>> list2 = new ArrayList<Map<String,Object>>();
		for(int i = 0; i < valueArray.length; i++){
			Row map = new Row();
			map.put("name", nameArray[i]);
			map.put("value", list.get(0).getString(valueArray[i]));
			list2.add(map);
		}
		String countSql = sqlBuilder.fields("count(*)").buildSql();
		Integer count = sqlRunner.count(countSql);
		return UIUtils.getGridData(list2.size(),list2);
	}
	
	public List<Row> getList(Map<String, Object> where) {
		SQLBuilder sqlBuilder = SQLBuilder.getSQLBuilder(User.class);
		String sql = sqlBuilder
				.fields("id,name as text")   //这里约定前端grid需要显示多少个具体列，也可以全部*
				.where("type = 3 or type = 4")
				.order("type", "asc")
				.buildSql();
		List<Row> list = sqlRunner.select(sql);
		System.out.println(where);
		return list;
	}
	
	public User getObjByUserName(String userName){
		SQLBuilder sqlBuilder = SQLBuilder.getSQLBuilder(User.class);
		String sql = sqlBuilder
				.fields("*")   //这里约定前端grid需要显示多少个具体列，也可以全部*
				.where("name = '"+userName+"'")
				.buildSql();
		List<Row> list = sqlRunner.select(sql);
		User user = this.findById(list.get(0).getInt("id"));
		return user;
	}
	
	
	public User getObjByProperties(HashMap<String, Object> where) {
		SQLBuilder sqlBuilder = SQLBuilder.getSQLBuilder(User.class);
		String sql = sqlBuilder.fields("*").where(where).buildSql();
		List<Row> list = sqlRunner.select(sql);
		if (list.size() == 0)
			return null;
		else
			return this.findById(list.get(0).getInt("id"));
	}
	
	public int checkLogin(String loginName, String loginPwd){
		//登录检查
		HashMap<String, Object> where  = new HashMap<String,Object>();
		where.put("user_phone", loginName);
		where.put("user_password",loginPwd);
		SQLBuilder sb = SQLBuilder.getSQLBuilder(User.class);
		String sql = sb.fields("*").where(where).buildSql();
		List<Row> list = sqlRunner.select(sql);
		Integer id = 0;
		for(Row row : list){
			id = Integer.valueOf(row.get("id").toString());
		}
		return id;
	}
	
	public int checkLoginByPhone(String phone, String loginPwd){
		//登录检查
		HashMap<String, Object> where  = new HashMap<String,Object>();
		where.put("user_phone", phone);
		where.put("user_password",loginPwd);
		System.out.println(phone+"::"+loginPwd);
		SQLBuilder sb = SQLBuilder.getSQLBuilder(User.class);
		String sql = sb.fields("*").where(where).buildSql();
		List<Row> list = sqlRunner.select(sql);
		Integer id = 0;
		for(Row row : list){
			id = Integer.valueOf(row.get("id").toString());
		}
		return id;
	}
	
	public Integer loginId(String loginName, String loginPwd){
		String sql = SQLBuilder.getSQLBuilder(User.class)
				.fields("id")   //这里约定前端grid需要显示多少个具体列，也可以全部*
				.where("user_phone ='"+loginName+"' and user_password ='"+loginPwd+"'")
				.order("id", "asc")
				.buildSql();
		List<Row> list = sqlRunner.select(sql);
		Integer id = 0;
		for(Row row : list){
			id = Integer.valueOf(row.get("id").toString());
		}
		System.out.println("#######"+id);
		return id;
	}
	
	/**
	 * 寻找超级管理员
	 * @param where
	 * @return
	 */
	public Integer findAdmin(){
		SQLBuilder sqlBuilder = SQLBuilder.getSQLBuilder(User.class);
		String sql = sqlBuilder
				.fields("id")
				.where("user_type = 3")
				.buildSql();
		List<Row> list = sqlRunner.select(sql);
		Integer id = 0;
		for(Row row : list){
			id = Integer.valueOf(row.get("id").toString());
		}
		return id;
	}
	
	public int findByName(String name){
		SQLBuilder sqlBuilder = SQLBuilder.getSQLBuilder(User.class);
		String sql = sqlBuilder
				.fields("id")
				.where("user_phone = '"+name+"'")
				.buildSql();
		List<Row> list = sqlRunner.select(sql);
		Integer id = 0;
		for(Row row : list){
			id = Integer.valueOf(row.get("id").toString());
		}
		return id;
	}
	
	
	/**
	 * 检查手机号码有无重复
	 */
	public boolean checkPhone(String phone){
		SQLBuilder sqlBuilder = SQLBuilder.getSQLBuilder(User.class);
		Map<String, Object> where = new HashMap<String,Object>();
		where.put("user_phone", phone);
		String sql = sqlBuilder
				.fields("id as id")
				.where(where)
				.buildSql();
		List<Row> list = sqlRunner.select(sql);
		if(list.size()>0){
			return true;
		}
		return false;
	}
	
	/**
	 * 检查threeid
	 */
	public int checkUserId(String userId,String type){
		SQLBuilder sqlBuilder = SQLBuilder.getSQLBuilder(User.class);
		Map<String, Object> where = new HashMap<String,Object>();
		where.put("three_id", userId);
		where.put("three_type", type);
		String sql = sqlBuilder
				.fields("id as id")
				.where(where)
				.buildSql();
		List<Row> list = sqlRunner.select(sql);
		for(Row row :list){
			return row.getInt("id");
		}
		return 0;
	}
	
	public String findNameByid(Object id){
		SQLBuilder sqlBuilder = SQLBuilder.getSQLBuilder(User.class);
		Map<String, Object> where = new HashMap<String,Object>();
		where.put("id", String.valueOf(id));
		String sql = sqlBuilder
				.fields("nick_name as nickName")
				.where(where)
				.buildSql();
		List<Row> list = sqlRunner.select(sql);
		if(list.size()>0){
			return list.get(0).getString("nickName");
		}
		return "";
	}
	
	public int findIdByThree(String userId,int type){
		SQLBuilder sqlBuilder = SQLBuilder.getSQLBuilder(User.class);
		Map<String, Object> where = new HashMap<String,Object>();
		where.put("three_id", userId);
		where.put("three_type", type);
		String sql = sqlBuilder
				.fields("id as id")
				.where(where)
				.buildSql();
		List<Row> list = sqlRunner.select(sql);
		if(list.size()>0){
			return list.get(0).getInt("id");
		}
		return 0;
	}
	
	public Integer findByPushId(String pushId){
		SQLBuilder sqlBuilder = SQLBuilder.getSQLBuilder(User.class);
		String sql = sqlBuilder
				.fields("*")   //这里约定前端grid需要显示多少个具体列，也可以全部*
				.where("registration_id ='"+pushId+"'")
				.buildSql();
		List<Row> list = sqlRunner.select(sql);
		for (Map<String,Object> row : list){
			return Integer.valueOf(row.get("id").toString());
		}
		return 0;
	}
	
}

package com.zlzkj.app.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zlzkj.app.mapper.ActionNodeMapper;
import com.zlzkj.app.model.ActionNode;
import com.zlzkj.app.util.CheckData;
import com.zlzkj.app.util.CommonUtil;
import com.zlzkj.app.util.CustomerException;
import com.zlzkj.app.util.StringUtil;
import com.zlzkj.app.util.UIUtils;
import com.zlzkj.core.mybatis.SqlRunner;
import com.zlzkj.core.sql.Row;
import com.zlzkj.core.sql.SQLBuilder;

@Service
@Transactional
public class ActionNodeService {

	@Autowired
	private ActionNodeMapper mapper;

	@Autowired
	private SqlRunner sqlRunner;
	@Autowired
	private RoleNodeService roleNodeService;
	

//	public Integer delete(Integer id){
//		return mapper.deleteByPrimaryKey(id);
//	}
//
//	public Integer update(ActionNode entity) throws Exception{
//
//		return mapper.updateByPrimaryKey(entity);
//	}
//
//	public Integer save(ActionNode entity) throws Exception{
//
//		return mapper.insert(entity);
//	}
//
//	public List<ActionNode> findAll() {
//		return mapper.selectAll();
//	}
//
//	public ActionNode findById(Integer id){
//		return mapper.selectByPrimaryKey(id);
//	}


//	public Map<String, Object> getUIGridData(Map<String, Object> where, Map<String, String> pageMap) {
//		SQLBuilder sqlBuilder = SQLBuilder.getSQLBuilder(ActionNode.class);
//		String sql = sqlBuilder
//				.fields("*")   //这里约定前端grid需要显示多少个具体列，也可以全部*
//				.where(where)
//				.parseUIPageAndOrder(pageMap)
//				.order("id", "asc")
//				.buildSql();
//		List<Row> list = sqlRunner.select(sql);
//
//		String countSql = sqlBuilder.fields("count(*)").where(where).buildSql();
//		Integer count = sqlRunner.count(countSql);
//		return UIUtils.getGridData(count, list);
//	}

	/**
	 * 获取指定条件数据
	 * @param field
	 * @param value
	 * @param id
	 * @return
	 */
	public List<Row> findByProperty(String whereString,Integer id){
		SQLBuilder sqlBuilder = SQLBuilder.getSQLBuilder(ActionNode.class);
		if(id>0){
			whereString += " and id<>"+id;
		}
		String sql = sqlBuilder.fields("*")
				.where(whereString)
				.buildSql();
		List<Row> list = sqlRunner.select(sql);
		return list;
	}
	
	public Boolean findByNode(String uri,Integer roleId){
		String sql = SQLBuilder.getSQLBuilder(ActionNode.class)
				.fields("id")
				.where("url = '"+uri+"'")
				.order("id", "ASC")
				.buildSql();
		List<Row> list = sqlRunner.select(sql);
		Boolean t = false;
		for(Row row : list){
			t = roleNodeService.findByRoleId(roleId, Integer.valueOf(row.get("id").toString()));
			//System.out.println("roleid:"+roleId+"----id:"+row.get("id"));
		}
//		System.out.println(!t);
		return t;
	}
	
	/**
	 * 获取指定条件数量
	 * @param field
	 * @param value
	 * @param id
	 * @return
	 */
	public int count(String whereString,Integer id){
		SQLBuilder sqlBuilder = SQLBuilder.getSQLBuilder(ActionNode.class);

		if(id>0){
			whereString += " and id<>"+id;
		}
		String sql = sqlBuilder.fields("count(*)")
				.where(whereString)
				.buildSql();
		int count = sqlRunner.count(sql);
		return count;
	}

	public Integer delete(Integer id){
		return mapper.deleteByPrimaryKey(id);
	}

	public Integer update(ActionNode entity){
		return mapper.updateByPrimaryKey(entity);
	}
	
	public int save(ActionNode entity) {
//		int count = this.count("name", entity.getName(), 0);
//		if(count>0)
//			return -1;

		if(entity.getPid()!=0){
			ActionNode actionNode = mapper.selectByPrimaryKey(entity.getPid());
			short level = actionNode.getLevel();
			entity.setLevel((byte) (level+1));
		}else{
			entity.setLevel((byte) 1);
		}
		entity.setIconClass("");
		entity.setIsMenu((byte) 1);
		return mapper.insert(entity);
	}
	public ActionNode findById(Integer id){
		return mapper.selectByPrimaryKey(id);
	}
	/**
	 * 获取顶部导航
	 * @return
	 */
	public List<Row> getTopNodeList(int roleId) {
		String sql = SQLBuilder.getSQLBuilder(ActionNode.class)
				.fields("*")
				.where("pid=0 and is_menu=1 and is_show=1")
				.order("sort_id", "ASC")
				.buildSql();
		List<Row> list = sqlRunner.select(sql);
		//判断是否显示此顶级菜单
		int max = 0;
		if(max==0){
			max = list.size();
		}
		int i=0;
		for (int j=0;j<max;j++) {
			Row row = list.get(i);
			List<Row> leftlist  = getLeftNodeList(Integer.valueOf(row.getInt("id")));
			int xxx = 0;
			int yyy = 0;
			for(Row leftrow : leftlist){
				//System.out.println(row.get("name")+"-"+leftrow.getString("name")+"-"+roleId+"-"+leftrow.getString("id"));
				List<Row> downlist =sqlRunner.select(SQLBuilder.getSQLBuilder(ActionNode.class)
						.fields("*")
						.where("pid="+leftrow.get("id")+" and level=3 and is_menu=1 and is_show=1")
						.order("sort_id", "ASC")
						.buildSql());
				for(Row downrow : downlist){
					if(findByNode(downrow.getString("url"),roleId)){
						xxx++;
					}
					yyy++;
				}
			}
			if(xxx==yyy){
				list.remove(row);
				if(list.size()==0){
					break;
				}
			}
			else{
				i++;
			}
		}
		return list;
	}
	/**
	 * 获取左侧导航
	 * @param topNodeId
	 * @return
	 */
	public List<Row> getLeftNodeList(Integer topNodeId) {
		SQLBuilder sb = SQLBuilder.getSQLBuilder(ActionNode.class);
		String sql = sb.fields("*")
					 .where("pid="+topNodeId+" and level=2 and is_menu=1 and is_show=1")
				     .order("sort_id", "ASC")
					 .buildSql();
		List<Row> list = sqlRunner.select(sql);
		for(Map<String, Object> one:list){
			one.put("children", sqlRunner.select(sb
					.fields("*")
					.where("pid="+one.get("id")+" and level=3 and is_menu=1 and is_show=1")
					.order("sort_id", "ASC")
					.buildSql()));
		}
		return list;
	}

	public Map<String, Object> getUIGridData() {
		SQLBuilder sb = SQLBuilder.getSQLBuilder(ActionNode.class);
		String sql = sb.fields("id,name,url,icon_class,sort_id,is_menu,is_show,pid AS _parentId")
				.order("sort_id", "ASC")
				.buildSql();
//		System.out.println("treeGrid:"+sql);
		//总记录数
		int count = sqlRunner.count(sb.fields("count(*)").buildSql());
//		System.out.println("treeGridCountSQL:"+sb.fields("count(*)").buildSql());
//		System.out.println("treeGridCount:"+count);
		return UIUtils.getGridData(count, sqlRunner.select(sql));
	}

	public List<Row> getUIComboData() {
		return getNodeTree("id,name AS text,pid,level,sort_id", 0, (byte) 3);
	}

	public List<Row> getNodeTree(String fields,Integer pid,Byte maxLevel){
		//查询条件
		String sql = SQLBuilder.getSQLBuilder(ActionNode.class).fields(fields).where("pid="+pid).order("sort_id", "ASC").buildSql();
		List<Row> list = sqlRunner.select(sql);
		for(Map<String, Object> one:list){
			if(Byte.parseByte(one.get("level").toString())<maxLevel){
				one.put("children",getNodeTree(fields,Integer.parseInt(one.get("id").toString()), maxLevel));
			}
		}

		return list;
	}
	
	public List<Map<String, Object>> getNodeList(Integer roleId) {
		return getNode("id,name,pid,level", 0, 4,roleId);
	}
	public List<Map<String, Object>> getNode(String fields,int pid,int maxLevel, Integer roleId){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pid", pid);
		String sql = SQLBuilder.getSQLBuilder(ActionNode.class)
				.fields(fields)
				.where(map)
				.order("sort_id", "ASC")
				.buildSql();
		List<Row> cateList = sqlRunner.select(sql);
		List<Map<String, Object>> res = new ArrayList<Map<String, Object>>();
		for(int i=0;i<cateList.size();i++){
			Map<String, Object> node = cateList.get(i); //赋给新的map
			node.put("isCheck", false);
			if (roleId!=null) {
				boolean bool =roleNodeService.findByRoleId(roleId,Integer.valueOf(node.get("id").toString()));
				if (!bool) {
					node.put("isCheck", true);
				}
			}
			if(Integer.valueOf(cateList.get(i).get("level").toString()) < maxLevel){
				node.put("children",getNode(fields,Integer.valueOf(node.get("id").toString()), maxLevel,roleId));
			}
			res.add(node);
		}
		return res;
	}
}

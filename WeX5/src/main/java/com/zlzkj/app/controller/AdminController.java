package com.zlzkj.app.controller;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.spel.ast.Assign;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.zlzkj.app.model.ActionNode;
import com.zlzkj.app.model.Role;
import com.zlzkj.app.model.RoleNode;
import com.zlzkj.app.service.ActionNodeService;
import com.zlzkj.app.service.UserService;
import com.zlzkj.app.service.RoleNodeService;
import com.zlzkj.app.service.RoleService;
import com.zlzkj.app.util.StringUtil;
import com.zlzkj.app.util.UIUtils;
import com.zlzkj.core.base.BaseController;
import com.zlzkj.core.util.Fn;

/**
 * 管理员控制器
 */
@Controller
@RequestMapping(value={"admin"})
public class AdminController extends BaseController{
	
	
	@Autowired
	private UserService userService;
	@Autowired
	private ActionNodeService actionNodeService;
	@Autowired
	private RoleService roleServcie;
	@Autowired
	private RoleNodeService roleNodeService;
	
	@RequestMapping(value={"/"})
	public String index(Model model,HttpServletRequest request,HttpServletResponse response) {
		return "admin/index";
	}
	
	/**
	 * 权限节点列表
	 * @param model
	 * @return
	 */
	@RequestMapping(value={"action_node"})
	public String actionNode(HttpServletRequest request, HttpServletResponse response) {
		if(request.getMethod().equals("POST")){
			if("combo".equals(request.getParameter("ui"))){
				List<Map<String, Object>> res = new ArrayList<Map<String, Object>>();
				Map<String, Object> node = new HashMap<String, Object>();
				node.put("id", 0);
				node.put("text", "--顶级节点--");
				res.add(node);
				res.addAll(actionNodeService.getUIComboData());
				//返回父子类节点
//				System.out.println("if");
				return ajaxReturn(response, res);
			}else{
				//System.out.println("else"+actionNodeService.getUIGridData().toString());
				return ajaxReturn(response, actionNodeService.getUIGridData());
			}
		}else{
			return "admin/action_node";
		}
	}
	
	/**
	 * 新增权限节点
	 * @param model
	 * @return
	 */
	@RequestMapping(value={"action_node_add"})
	public String actionNodeAdd(HttpServletRequest request, HttpServletResponse response,
			ActionNode actionNode) {
		
		if(request.getMethod().equals("POST")){
//			try {
//				actionNode.setName(new String(actionNode.getName().getBytes("ISO-8859-1"), "UTF-8"));
//			} catch (UnsupportedEncodingException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			actionNodeService.save(actionNode);
			return ajaxReturn(response, null,"添加成功",1);
		}else{
			return "admin/action_node_add";
		}
	}
	
	/**
	 * 修改权限节点
	 * @param model
	 * @return
	 */
	@RequestMapping(value={"action_node_edit"})
	public String actionNodeEdit(Model model,HttpServletRequest request, HttpServletResponse response,
			ActionNode actionNode) {
		
		if(request.getMethod().equals("POST")){
			actionNodeService.update(actionNode);
			return ajaxReturn(response, null,"修改成功",1);
		}else{
			model.addAttribute("actionNode",actionNodeService.findById(actionNode.getId()));
			return "admin/action_node_edit";
		}
	}
	
	/**
	 * 删除权限节点
	 * @param model
	 * @return
	 */
	@RequestMapping(value={"action_node_delete"},method=RequestMethod.POST)
	public String actionNodeDelete(HttpServletResponse response,Integer id) {
		actionNodeService.delete(id);
		return ajaxReturn(response, null,"删除成功",1);
	}
	
	/**
     * 角色管理
     * @param model
     * @return
     */
	@RequestMapping(value={"role"})
	public String role(Model model, HttpServletRequest request, HttpServletResponse response) {
		if(request.getMethod().equals("POST")){
			Map<String, Object> where = new HashMap<String, Object>();
			return ajaxReturn(response, roleServcie.getUIGridData(where,UIUtils.getPageParams(request)));
		}else{
			return "admin/role";
		}
	}

	/**
     * 角色添加
     * @param model
     * @return
     * @throws Exception 
     */
    @RequestMapping(value={"role_add"})
    public String roleAdd(Model model,HttpServletRequest request, HttpServletResponse response
    		,Role entity) throws Exception {
    	if(request.getMethod().equals("POST")){
    		System.out.println(entity.getName()+" "+entity.getStatus()+" "+entity.getRemark());
    		int count = roleServcie.save(entity);
    		if(count == 1){
				return ajaxReturn(response, null,"添加成功",1);
			}else {
				return ajaxReturn(response, null,"发生错误，请重试！",0);
			}
    	}
    	return "admin/role_add";
    }

    /**
     * 角色编辑
     * @param model
     * @return
     * @throws Exception 
     */
    @RequestMapping(value={"role_edit"})
    public String roleEdit(Model model,HttpServletRequest request, HttpServletResponse response
    		,Role entity) throws Exception {
    	
    		if(entity.getId() != null){
    			model.addAttribute("role",roleServcie.findById(entity.getId()));
    		}
    	
    		if(request.getMethod().equals("POST")){
    			System.out.println(entity.getId()+" "+entity.getName()+" "+entity.getStatus()+" "+entity.getRemark());
    			int flag = roleServcie.update(entity);
	    		if(flag == 1){
					return ajaxReturn(response, null,"添加成功",1);
				}else {
					return ajaxReturn(response, null,"发生错误，请重试！",0);
				}
    		}
        return "admin/role_edit";
    }
    
    @RequestMapping(value={"role_delete"})
	public String roleDel(HttpServletRequest request,HttpServletResponse response,Integer id) {
//		if(request.getMethod().equals("POST")){
			System.out.println(id);
			roleServcie.delete(id);
			return ajaxReturn(response, null,"删除成功",1);
    }
    
    /**
     * 设置角色权限
     * @param model
     * @return
     * @throws Exception 
     */
    @RequestMapping(value={"role_access"})
	public String roleAccess(Model model,HttpServletRequest request,HttpServletResponse response
			,Integer id) throws Exception {
		if(request.getMethod().equals("POST")){
			String[] nodeIds= request.getParameterValues("nodeId");
			//roleNodeService.delete(id);
			roleNodeService.deleteAllByRoleId(id);
			if (nodeIds!=null) {
				for (String nodeId : nodeIds) {
					RoleNode roleNode = new RoleNode();
					roleNode.setRoleId(id);
					roleNode.setNodeId(Integer.valueOf(nodeId));
					roleNode.setAddTime(Fn.time());
					roleNodeService.save(roleNode);
				}
			}
			return ajaxReturn(response, null,"授权成功~",1);
		}else {
			List<Map<String, Object>> node = actionNodeService.getNodeList(id);
			System.out.println(node.toString());
			model.addAttribute("node", node);
			return "admin/role_access";
		}
	}
}

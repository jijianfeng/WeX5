package com.zlzkj.app.controller;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
//import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.zlzkj.app.model.Recru;
import com.zlzkj.app.model.Recru;
import com.zlzkj.app.model.User;
import com.zlzkj.app.service.RecruService;
//import com.zlzkj.app.model.User;
import com.zlzkj.app.service.RoleService;
import com.zlzkj.app.service.UserService;
import com.zlzkj.app.util.UIUtils;
//import com.zlzkj.app.service.UserService;
//import com.zlzkj.app.util.StringUtil;
//import com.zlzkj.app.util.UIUtils;
import com.zlzkj.app.util.UploadUtils;
import com.zlzkj.core.base.BaseController;
import com.zlzkj.core.util.Fn;

/**
 * 招聘控制器
 * @author JianfengJi
 *
 */
@Controller
@RequestMapping(value={"recru"})
public class RecruController extends BaseController{
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RecruService recruService;
	
	
	@RequestMapping(value={"list"})
	public String list(Model model, HttpServletRequest request, HttpServletResponse response){
		if(request.getMethod()=="POST"){
			String title = request.getParameter("title");
			String status = request.getParameter("status");
			Map<String, Object> where = new HashMap<String,Object>();
			if(title!=null&&(!title.equals(""))){
				where.put("question_title",new String[]{"like","%"+title+"%"});
			}
			if(status!=null){
				if(status.equals("1")){
					//未解决
					where.put("is_solved",0);
				}
				if(status.equals("2")){
					//已结解决
					where.put("is_solved",1);
				}
			}
			return ajaxReturn(response, recruService.getRecruUIGridData(where,UIUtils.getPageParams(request),10));
		}
		else{
			return "recru/list";
		}
	}

	@RequestMapping(value={"list_detail"})
	public String listDetail(Model model, HttpServletRequest request, HttpServletResponse response,int id){
		Recru recru = recruService.findById(id);
		model.addAttribute("Recru", recru);
		User user = userService.findById(recru.getAddUser());
		model.addAttribute("nickName", user.getNickName());
		return "recru/list_detail";
	}
	
	@RequestMapping(value={"list_add"})
	public String listAdd(Model model, HttpServletRequest request, HttpServletResponse response,Recru recru) throws Exception{
		if(request.getMethod()=="POST"){
			recru.setAddTime(Fn.time());
			recru.setStatus(0);
			int i = recruService.save(recru);
			if(i==1){
				return ajaxReturn(response,null,"删除成功",1);
			}
			else{
				return ajaxReturn(response,null,"删除失败",0);
			}
		}
		else{
			return "recru/list_add";
		}
	}
	
	@RequestMapping(value={"list_accept"})
	public String listAccept(Model model, HttpServletRequest request, HttpServletResponse response,int id) throws Exception{
		if(request.getMethod()=="POST"){
			Recru recru = recruService.findById(id);
			recru.setStatus(1);
			int i = recruService.update(recru);
			if(i==1){
				return ajaxReturn(response,null,"审核成功",1);
			}
			else{
				return ajaxReturn(response,null,"审核失败",0);
			}
		}
		else{
			return "recru/list";
		}
	}
	
	@RequestMapping(value={"list_refuse"})
	public String listRefuse(Model model, HttpServletRequest request, HttpServletResponse response,int id) throws Exception{
		if(request.getMethod()=="POST"){
			Recru recru = recruService.findById(id);
			recru.setStatus(0);
			int i = recruService.update(recru);
			if(i==1){
				return ajaxReturn(response,null,"禁用成功",1);
			}
			else{
				return ajaxReturn(response,null,"禁用失败",0);
			}
		}
		else{
			return "recru/list";
		}
	}
	
	@RequestMapping(value={"list_delete"})
	public String listDelete(Model model, HttpServletRequest request, HttpServletResponse response){
		if(request.getMethod()=="POST"){
			int id = Integer.valueOf(request.getParameter("id"));
			int i = recruService.delete(id);
			if(i==1){
				return ajaxReturn(response,null,"删除成功",1);
			}
			else{
				return ajaxReturn(response,null,"删除失败",0);
			}
		}
		else{
			return "recru/list";
		}
	}
}

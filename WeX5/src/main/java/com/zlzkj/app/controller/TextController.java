package com.zlzkj.app.controller;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.zlzkj.app.model.Text;
import com.zlzkj.app.model.User;
import com.zlzkj.app.service.TextService;
import com.zlzkj.app.service.RoleService;
import com.zlzkj.app.service.UserService;
import com.zlzkj.app.util.UIUtils;
import com.zlzkj.app.util.UploadUtils;
import com.zlzkj.core.base.BaseController;
import com.zlzkj.core.util.Fn;

/**
 * 文档控制器
 */
@Controller
@RequestMapping(value={"text"})
public class TextController extends BaseController{
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private TextService textService;
	
	@RequestMapping(value={"list"})
	public String list(Model model, HttpServletRequest request, HttpServletResponse response){
		if(request.getMethod()=="POST"){
			String title = request.getParameter("title");
			String status = request.getParameter("status");
			Map<String, Object> where = new HashMap<String,Object>();
			if(title!=null&&(!title.equals(""))){
				where.put("title",new String[]{"like","%"+title+"%"});
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
			User user = userService.findById(Integer.valueOf(request.getSession().getAttribute("userId").toString()));
			return ajaxReturn(response, textService.getUIGridData(where,UIUtils.getPageParams(request)));
		}
		else{
			return "text/list";
		}
	}
	
	@RequestMapping(value={"list_add"})
	public String listAdd(Model model, HttpServletRequest request, HttpServletResponse response,Text text) throws Exception{
		if(request.getMethod()=="POST"){
			text.setAddTime(Fn.time());
			text.setAddUser(Integer.valueOf(request.getSession().getAttribute("userId").toString()));
			if(textService.save(text)==1){
				return ajaxReturn(response, null,"添加成功",1);
			}
			else{
				return ajaxReturn(response, null,"添加失败",0);
			}
		}
		else{
			return "text/list_add";
		}
	}
	
	@RequestMapping(value={"list_edit"})
	public String listEdit(Model model, HttpServletRequest request, HttpServletResponse response,Text text) throws Exception{
		if(request.getMethod()=="POST"){
			text.setAddTime(Fn.time());
			text.setAddUser(Integer.valueOf(request.getSession().getAttribute("userId").toString()));
			if(textService.update(text)==1){
				return ajaxReturn(response, null,"编辑成功",1);
			}
			else{
				return ajaxReturn(response, null,"编辑失败",0);
			}
		}
		else{
			model.addAttribute("text", textService.findById(Integer.valueOf(request.getParameter("id"))));
			return "text/list_edit";
		}
	}
	
	@RequestMapping(value={"list_delete"})
	public String listDelete(Model model, HttpServletRequest request, HttpServletResponse response){
		if(request.getMethod()=="POST"){
			int i = textService.delete(Integer.valueOf(request.getParameter("id").toString()));
			if(i==1){
				return ajaxReturn(response, null,"删除成功",1);
			}
			else{
				return ajaxReturn(response, null,"删除失败",0);
			}
		}
		else{
			return "text/list";
		}
	}
	
	@RequestMapping(value={"text_list"},method=RequestMethod.POST)
	public String textList(Model model, HttpServletRequest request, HttpServletResponse response){
		
		return ajaxReturn(response,textService.getList());
	}
}

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
import com.zlzkj.app.model.Chapter;
import com.zlzkj.app.model.User;
import com.zlzkj.app.service.ChapterService;
import com.zlzkj.app.service.RoleService;
import com.zlzkj.app.service.UserService;
import com.zlzkj.app.util.UIUtils;
import com.zlzkj.app.util.UploadUtils;
import com.zlzkj.core.base.BaseController;
import com.zlzkj.core.util.Fn;

/**
 * 公共控制器
 */
@Controller
@RequestMapping(value={"chapter"})
public class ChapterController extends BaseController{
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ChapterService chapterService;
	
	@RequestMapping(value={"list"})
	public String list(Model model, HttpServletRequest request, HttpServletResponse response){
		if(request.getMethod()=="POST"){
			String title = request.getParameter("title");
			String status = request.getParameter("status");
			Map<String, Object> where = new HashMap<String,Object>();
			if(title!=null&&(!title.equals(""))){
				where.put("chapter_title",new String[]{"like","%"+title+"%"});
			}
			if(status!=null){
				if(status.equals("1")){
					//未解决
					where.put("status",0);
				}
				if(status.equals("2")){
					//已结解决
					where.put("status",1);
				}
			}
			User user = userService.findById(Integer.valueOf(request.getSession().getAttribute("userId").toString()));
			return ajaxReturn(response, chapterService.getUIGridData(where,UIUtils.getPageParams(request)));
		}
		else{
			return "chapter/list";
		}
	}
	
	@RequestMapping(value={"list_add"})
	public String listAdd(Model model, HttpServletRequest request, HttpServletResponse response,Chapter chapter) throws Exception{
		if(request.getMethod()=="POST"){
			chapter.setAddTime(Fn.time());
			//User user = userService.findById(Integer.valueOf(request.getSession().getAttribute("userId").toString()));
			chapter.setUserId(Integer.valueOf(request.getSession().getAttribute("userId").toString()));
			if(chapterService.save(chapter)==1){
				return ajaxReturn(response, null,"添加成功",1);
			}
			else{
				return ajaxReturn(response, null,"添加失败",0);
			}
		}
		else{
			return "chapter/list_add";
		}
	}
	
	@RequestMapping(value={"list_edit"})
	public String listEdit(Model model, HttpServletRequest request, HttpServletResponse response,Chapter chapter) throws Exception{
		if(request.getMethod()=="POST"){
			chapter.setAddTime(Fn.time());
			chapter.setUserId(Integer.valueOf(request.getSession().getAttribute("userId").toString()));
			if(chapterService.update(chapter)==1){
				return ajaxReturn(response, null,"编辑成功",1);
			}
			else{
				return ajaxReturn(response, null,"编辑失败",0);
			}
		}
		else{
			model.addAttribute("chapter", chapterService.findById(Integer.valueOf(request.getParameter("id"))));
			return "chapter/list_edit";
		}
	}
	
	@RequestMapping(value={"list_delete"})
	public String listDelete(Model model, HttpServletRequest request, HttpServletResponse response){
		if(request.getMethod()=="POST"){
			int i = chapterService.delete(Integer.valueOf(request.getParameter("id").toString()));
			if(i==1){
				return ajaxReturn(response, null,"删除成功",1);
			}
			else{
				return ajaxReturn(response, null,"删除失败",0);
			}
		}
		else{
			return "chapter/list";
		}
	}
	
	@RequestMapping(value={"chapter_list"},method=RequestMethod.POST)
	public String chapterList(Model model, HttpServletRequest request, HttpServletResponse response){
		
		return ajaxReturn(response,chapterService.getList());
	}
}

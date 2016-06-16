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
import com.zlzkj.app.model.Level;
import com.zlzkj.app.model.Question;
import com.zlzkj.app.model.User;
import com.zlzkj.app.service.ChapterService;
import com.zlzkj.app.service.ConfirmService;
import com.zlzkj.app.service.QuestionService;
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
@RequestMapping(value={"confirm"})
public class ConfirmController extends BaseController{
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ConfirmService confirmService;
	
	@Autowired
	private QuestionService questionService;
	
	@RequestMapping(value={"list"})
	public String list(Model model, HttpServletRequest request, HttpServletResponse response){
		if(request.getMethod()=="POST"){
			String title = request.getParameter("title");
			String status = request.getParameter("status");
			Map<String, Object> where = new HashMap<String,Object>();
			if(title!=null&&(!title.equals(""))){
				where.put("chapter_title",new String[]{"like","%"+title+"%"});
			}
			//User user = userService.findById(Integer.valueOf(request.getSession().getAttribute("userId").toString()));
			return ajaxReturn(response, confirmService.getUIGridData(where,UIUtils.getPageParams(request)));
		}
		else{
			return "confirm/list";
		}
	}
	
	@RequestMapping(value={"list_add"})
	public String listAdd(Model model, HttpServletRequest request, HttpServletResponse response,Level level) throws Exception{
		if(request.getMethod()=="POST"){
			level.setAddTime(Fn.time());
			//User user = userService.findById(Integer.valueOf(request.getSession().getAttribute("userId").toString()));
			level.setUserId(Integer.valueOf(request.getSession().getAttribute("userId").toString()));
			if(confirmService.save(level)==1){
				return ajaxReturn(response, null,"添加成功",1);
			}
			else{
				return ajaxReturn(response, null,"添加失败",0);
			}
		}
		else{
			return "confirm/list_add";
		}
	}
	
	@RequestMapping(value={"list_edit"})
	public String listEdit(Model model, HttpServletRequest request, HttpServletResponse response,Level level) throws Exception{
		if(request.getMethod()=="POST"){
			level.setAddTime(Fn.time());
			level.setUserId(Integer.valueOf(request.getSession().getAttribute("userId").toString()));
			if(confirmService.update(level)==1){
				return ajaxReturn(response, null,"编辑成功",1);
			}
			else{
				return ajaxReturn(response, null,"编辑失败",0);
			}
		}
		else{
			model.addAttribute("level", confirmService.findById(Integer.valueOf(request.getParameter("id"))));
			return "confirm/list_edit";
		}
	}
	
	@RequestMapping(value={"list_delete"})
	public String listDelete(Model model, HttpServletRequest request, HttpServletResponse response){
		if(request.getMethod()=="POST"){
			int i = confirmService.delete(Integer.valueOf(request.getParameter("id").toString()));
			if(i==1){
				return ajaxReturn(response, null,"删除成功",1);
			}
			else{
				return ajaxReturn(response, null,"删除失败",0);
			}
		}
		else{
			return "confirm/list";
		}
	}
	
	@RequestMapping(value={"level_list"},method=RequestMethod.POST)
	public String chapterList(Model model, HttpServletRequest request, HttpServletResponse response){
		return ajaxReturn(response,confirmService.getList());
	}
	
	@RequestMapping(value={"question"})
	public String question(Model model, HttpServletRequest request, HttpServletResponse response){
		if(request.getMethod()=="POST"){
			String title = request.getParameter("title");
			String status = request.getParameter("status");
			Map<String, Object> where = new HashMap<String,Object>();
			if(title!=null&&(!title.equals(""))){
				where.put("question_name",new String[]{"like","%"+title+"%"});
			}
			//User user = userService.findById(Integer.valueOf(request.getSession().getAttribute("userId").toString()));
			return ajaxReturn(response, confirmService.getQuestionUIGridData(where,UIUtils.getPageParams(request)));
		}
		else{
			//System.out.println("很稳");
			return "confirm/question";
		}
	}
	
	@RequestMapping(value={"question_add"})
	public String questionAdd(Model model, HttpServletRequest request, HttpServletResponse response,Question question) throws Exception{
		if(request.getMethod()=="POST"){
			question.setAddTime(Fn.time());
			//User user = userService.findById(Integer.valueOf(request.getSession().getAttribute("userId").toString()));
			//question.setUserId(Integer.valueOf(request.getSession().getAttribute("userId").toString()));
//			System.out.println(request.getParameter("choice_namea"));
//			System.out.println(request.getParameter("choice_nameb"));
//			System.out.println(request.getParameter("choice_namec"));
//			System.out.println(request.getParameter("choice_named"));
			question.setChoiceNamea(request.getParameter("choice_namea"));
			question.setChoiceNameb(request.getParameter("choice_nameb"));
			question.setChoiceNamec(request.getParameter("choice_namec"));
			question.setChoiceNamed(request.getParameter("choice_named"));
			if(questionService.save(question)==1){
				return ajaxReturn(response, null,"添加成功",1);
			}
			else{
				return ajaxReturn(response, null,"添加失败",0);
			}
		}
		else{
			return "confirm/question_add";
		}
	}
	
	@RequestMapping(value={"question_edit"})
	public String questionEdit(Model model, HttpServletRequest request, HttpServletResponse response,Question question) throws Exception{
		if(request.getMethod()=="POST"){
			question.setAddTime(Fn.time());
			//question.setUserId(Integer.valueOf(request.getSession().getAttribute("userId").toString()));
			if(questionService.update(question)==1){
				return ajaxReturn(response, null,"编辑成功",1);
			}
			else{
				return ajaxReturn(response, null,"编辑失败",0);
			}
		}
		else{
			model.addAttribute("level", confirmService.findById(Integer.valueOf(request.getParameter("id"))));
			return "confirm/question_edit";
		}
	}
	
	@RequestMapping(value={"question_delete"})
	public String questionDelete(Model model, HttpServletRequest request, HttpServletResponse response){
		if(request.getMethod()=="POST"){
			int i = questionService.delete(Integer.valueOf(request.getParameter("id").toString()));
			if(i==1){
				return ajaxReturn(response, null,"删除成功",1);
			}
			else{
				return ajaxReturn(response, null,"删除失败",0);
			}
		}
		else{
			return "confirm/list";
		}
	}
	
}

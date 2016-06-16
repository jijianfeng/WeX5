package com.zlzkj.app.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.zlzkj.app.model.User;
import com.zlzkj.app.service.UserService;
import com.zlzkj.app.util.ExcelTransport;
import com.zlzkj.app.util.JsonUtil;
import com.zlzkj.app.util.StringUtil;
import com.zlzkj.app.util.UIUtils;
import com.zlzkj.app.util.UploadUtils;
import com.zlzkj.app.util.sendsms;
import com.zlzkj.core.base.BaseController;
import com.zlzkj.core.util.Fn;

/**
 * 平台用户控制器
 */
@Controller
@RequestMapping(value={"user"})
public class UserController extends BaseController{

	@Autowired
	private UserService userService;
 
	@RequestMapping(value={"/"})
	public String index(Model model,HttpServletRequest request,HttpServletResponse response) {

		return "user/user";
	}

	@RequestMapping(value={"find_by_id"},method = RequestMethod.POST)
	public String findById(Integer id, HttpServletRequest request, HttpServletResponse response) {
		User user = userService.findById(id);
		return ajaxReturn(response, user);
	}
	
	@RequestMapping(value={"list"})
	public String list(Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		if(request.getMethod()=="POST"){
			Map<String, Object> where = new HashMap<String,Object>();
			return ajaxReturn(response, userService.getUIGridData(where,UIUtils.getPageParams(request),1));
			//return null;
		}
		
		else{
			return "user/list";
		}
	}
	

	@RequestMapping(value={"save"})
	public String save(User entity, HttpServletRequest request, HttpServletResponse response){
        Integer count = null;
			try {
				count = userService.save(entity);
			} catch (Exception e) {
				return ajaxReturn(response,null,e.getLocalizedMessage(),-1);
			}

        if(count==0)
        	return ajaxReturn(response,null,"添加失败",0);
        else
        	return ajaxReturn(response, null,"添加成功",1);
	}


	@RequestMapping(value={"update"})
	public String update(User entity, HttpServletRequest request, HttpServletResponse response) {
		Integer count = null;
		try {
			count = userService.update(entity);
		} catch (Exception e) {
			return ajaxReturn(response,null,e.getLocalizedMessage(),-1);
		}

    if(count==0)
    	return ajaxReturn(response,null,"更新失败",0);
    else
    	return ajaxReturn(response, null,"更新成功",1);

	}
	
	@RequestMapping(value={"detail"})
	public String detail(Model model, HttpServletRequest request, HttpServletResponse response
			,User entity,Integer id) {
		if(request.getMethod().equals("POST")){
			//System.out.println("post");
			if(id != null){
				return ajaxReturn(response,userService.getDetailUIGridData(id));
			}
			return "user/detail";
		}else{
			//System.out.println("get");
			model.addAttribute("id",id);
			return "user/detail";
		}
	}
	
	/**
	 * 
	 * @param model
	 * @param request
	 * @param response
	 * @param entity
	 * @param id
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value={"add"})
	public String add(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		if(request.getMethod().equals("POST")){
			return "user/add";
		}else{
			return "user/add";
		}
	}
	
	@RequestMapping(value={"change_pass"})
	public String change(Model model, HttpServletRequest request,HttpServletResponse response,String name,String OPass, String NPass) throws Exception {
		if(request.getMethod()=="POST"){
			int  id = Integer.valueOf(request.getSession().getAttribute("userId").toString());
			User user = userService.findById(id);
			if(String.valueOf(user.getUserPassword()).equals(OPass)){
				user.setUserPassword(NPass);
				if(userService.update(user)==1){
					return ajaxReturn(response, null,"修改成功",1);
				}
				else{
					return ajaxReturn(response, null,"修改失败",0);
				}
			}else{
				return ajaxReturn(response, null,"原密码错误",0);
			}
		}
		else{
			User user = userService.findById(Integer.valueOf(request.getSession().getAttribute("userId").toString()));
			model.addAttribute("user", user);
			return "user/change_pass";
		}
	}
	
	
	
	/**
	 * 用户备注
	 * @param model
	 * @param request
	 * @param response
	 * @param entity
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value={"edit"})
	public String edit(Model model, HttpServletRequest request, HttpServletResponse response,User entity) throws Exception {
		if(request.getMethod()=="POST"){
			String id = request.getParameter("id");
			User user = userService.findById(Integer.valueOf(id));
			int i = (userService.update(user));
			if(i==1){
				return ajaxReturn(response, null,"修改成功",1);
			}
			else{
				return ajaxReturn(response, null,"修改失败",0);
			}
			//return ajaxReturn(response, null,"修改成功",1);
		}
		else{
			String id = request.getParameter("id");
			User user = userService.findById(Integer.valueOf(id));
			model.addAttribute("user", user);
			return "user/edit";
		}
	}
	
	/**
	 * 删除
	 * @param model
	 * @param request
	 * @param response
	 * @param id
	 * @return
	 */
	@RequestMapping(value={"deletes"})
	public String listDelete(Model model,HttpServletRequest request, HttpServletResponse response
			,Integer id) {
		if(request.getMethod().equals("POST")){
			userService.delete(id);
//			int  adminId = userService.findId(id);
//			userService.delete(adminId);
			return ajaxReturn(response, null,"删除成功",1);
		}else{
			return "user/list";
		}
	}
	
	/**
	 * 显示
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
//	@RequestMapping(value={"select"})
//	public String enterpriseSelect(Model model, HttpServletRequest request, HttpServletResponse response
//			,String types) {
//		System.out.println(types);
//		if(types != null){
//			request.getSession().setAttribute("typessss", types);
//		}
//		if(request.getMethod().equals("POST")){
//			String status = (String) request.getSession().getAttribute("typessss");
////			request.getSession().removeAttribute("types");	
////			System.out.println("ty"+ty);
//			if(status.equals("1")){
//				return ajaxReturn(response, enterpriseService.getUIGridData(null, UIUtils.getPageParams(request)));
//			} else if(status.equals("2")){
//				return ajaxReturn(response, departmentService.getGridData("> 0", UIUtils.getPageParams(request)));
//			} else {
//				return ajaxReturn(response, departmentService.getGridData("= 0", UIUtils.getPageParams(request)));
//			}
//		
//		}
//			return "user/select";
//	}
	
	
//	@RequestMapping(value={"roleList"})
//	public String roleList(Model model, HttpServletRequest request, HttpServletResponse response) {
//		if(request.getMethod().equals("POST")){
//			Map<String, Object>	userList = userService.getUIGridData(null,UIUtils.getPageParams(request),1);
//			model.addAttribute("userList",userList);
//			return ajaxReturn(response, userList);
//		}else{
//			return "user/list";
//		}
//	}
	
	@RequestMapping(value={"accept"})
	public String accept(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		if(request.getMethod().equals("POST")){
			String id = request.getParameter("id");
			User user = userService.findById(Integer.valueOf(id));
			user.setStatus(1);
			int i = userService.update(user);
			if(i==1){
				return ajaxReturn(response,null,"启用成功",1);
			}
			else{
				return ajaxReturn(response,null,"启用失败",0);
			}
		}else{
			return "user/list";
		}
	}
	
	@RequestMapping(value={"cancel"})
	public String cancel(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		if(request.getMethod().equals("POST")){
			String id = request.getParameter("id");
			User user = userService.findById(Integer.valueOf(id));
			user.setStatus(0);
			int i = userService.update(user);
			if(i==1){
				return ajaxReturn(response,null,"禁用成功",1);
			}
			else{
				return ajaxReturn(response,null,"禁用失败",0);
			}
		}else{
			return "user/list";
		}
	}
	
	@RequestMapping(value={"restart"})
	public String restart(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		if(request.getMethod().equals("POST")){
			String id = request.getParameter("id");
			User user = userService.findById(Integer.valueOf(id));
			if(user.getUserType()==3){
				return ajaxReturn(response,null,"你没有权限",0);
			}
			User admin = userService.findById(Integer.valueOf(request.getSession().getAttribute("userId").toString()));
			if(admin.getUserType()!=3){
				return ajaxReturn(response,null,"非法访问,你没有权限",0);
			}
			else{
				int mobile_code = (int)((Math.random()*9+1)*100000);
				user.setUserPassword(String.valueOf(mobile_code));
				int i = userService.update(user);
				if(i==1){
					return ajaxReturn(response,null,"修改成功，新密码为 "+mobile_code,1);
				}
				else{
					return ajaxReturn(response,null,"修改失败，请稍后再试",0);
				}
			}
		}else{
			return "user/list";
		}
	}
	
	@RequestMapping(value={"send_code"})
	public String sendCode(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		if(request.getMethod().equals("POST")){
			String id = request.getParameter("id");
			User user = userService.findById(Integer.valueOf(id));
			int mobile_code = (int)((Math.random()*9+1)*100000);
			String content = new String("您的验证码是：" + mobile_code + "。请不要把验证码泄露给其他人。"); 
			//int number = sendMessage(content,"17826800420");
			if(Fn.time()-Integer.valueOf(user.getCheckCodeTime())<=60){
				System.out.println("呵呵");
				return ajaxReturn(response,null,"您的请求，过于频繁，请稍后再试！",0);
			}
			if(user.getUserPhone().length()==11){
				int number = sendsms.sendMessage(content, user.getUserPhone());
				System.out.println("number"+number);
				if(number==1){
					user.setCheckCode(String.valueOf(mobile_code));
					user.setCheckCodeTime(String.valueOf(Fn.time()));
					userService.update(user);
					return ajaxReturn(response,null,"发送成功！",1);
				}
				else{
					return ajaxReturn(response,null,"发送失败！每个手机每天最多发五条短信",0);
				}
			}
			else{
				String phone = request.getParameter("userPhone");
				if(phone.length()!=11){
					return ajaxReturn(response,null,"您输入的手机号码错误！",0);
				}
				int number = sendsms.sendMessage(content,phone);
				System.out.println("number"+number);
				if(number==1){
					user.setCheckCode(String.valueOf(mobile_code));
					user.setCheckCodeTime(String.valueOf(Fn.time()));
					userService.update(user);
					return ajaxReturn(response,null,"发送成功！",1);
					//return ajaxReturn(response,null,"发送失败！",0);
				}
				else{
					return ajaxReturn(response,null,"发送失败！每个手机每天最多发五条短信",0);
				}
				
			}
		}else{
			return ajaxReturn(response,null,"非法访问！",0);
		}
	}
	
	
	@RequestMapping(value={"reset_phone"})
	public String resetPhone(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		if(request.getMethod().equals("POST")){
			String id = request.getParameter("id");
			User user = userService.findById(Integer.valueOf(id));
			if(user.getUserType()==3){
				return ajaxReturn(response,null,"你没有权限",0);
			}
			User admin = userService.findById(Integer.valueOf(request.getSession().getAttribute("userId").toString()));
			if(admin.getUserType()!=3){
				return ajaxReturn(response,null,"非法访问,你没有权限",0);
			}
			else{
				user.setUserPhone("");
				int i = userService.update(user);
				if(i==1){
					return ajaxReturn(response,null,"重置成功，请尽快绑定手机！",1);
				}
				else{
					return ajaxReturn(response,null,"重置失败，请稍后再试",0);
				}
			}
		}else{
			return "user/list";
		}
	}


}

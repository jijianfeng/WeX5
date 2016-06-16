package com.zlzkj.app.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
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
import com.zlzkj.app.model.User;
import com.zlzkj.app.service.RoleService;
import com.zlzkj.app.service.UserService;
import com.zlzkj.app.util.StringUtil;
import com.zlzkj.app.util.UIUtils;
//import com.zlzkj.app.util.StringUtil;
//import com.zlzkj.app.util.UIUtils;
import com.zlzkj.app.util.UploadUtils;
import com.zlzkj.app.util.sendsms;
import com.zlzkj.core.base.BaseController;
import com.zlzkj.core.util.Fn;

/**
 * 公共控制器
 */
@Controller
@RequestMapping(value={"public"})
public class PublicController extends BaseController{

	@Autowired
	private UserService userService;

	@Autowired
	private RoleService roleService;
	
//	@Autowired
//	private AreaService areaService;
	
	
	@RequestMapping(value = "upload",method=RequestMethod.POST)
	public String upload(HttpServletRequest request,
						 HttpServletResponse response,
						 @RequestParam("imgFile") MultipartFile picWebFile){
		System.out.println("upload");
		Map<String, Object> data = new HashMap<String, Object>();
		String picWeb = ""; //图片保存名
		System.out.println("aaa");
		Map<String,Object> picWebInfo = UploadUtils.saveMultipartFile(picWebFile);
		System.out.println("bbb");
		if((Integer)picWebInfo.get("status")>0){ //上传完成
			picWeb =  UploadUtils.parseFileUrl(picWebInfo.get("saveName").toString());
		}else{ //上传出错
			return ajaxReturn(response,null,picWebInfo.get("errorMsg").toString(),0);
		}
		data.put("url", picWeb);
		data.put("alt", "");
		return ajaxReturn(response,data,null,1);
	}

	/**
	 * 登录
	 * @throws Exception 
	 */
	@RequestMapping(value={"login"})
	public String login(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		//网址里敲是get
		if(request.getMethod()=="POST"){
			String loginName = request.getParameter("loginName");
			String loginPwd = request.getParameter("loginPass");
			int i = userService.checkLogin(loginName,loginPwd);
			if(i==0){
				 i = userService.checkLoginByPhone(loginName, loginPwd);
			}
			if(i!=0){
				User user = userService.findById(i);
				//超过五分钟就刷新
				if(((Fn.time()-user.getTryLoginTime())/1)>300){
					user.setTryLoginTime(Fn.time());
					user.setTyrLogin(0);
				}
				if(user.getTyrLogin()>5){
					return ajaxReturn(response,null,"您已经多次输入密码！，请五分钟后重试！",0);
				}
				if(user.getStatus()==0){
					return ajaxReturn(response,null,"登录失败,该用户尚未通过审核",0);
				}
				request.getSession().setAttribute("loginName", user.getNickName() );//登录成功放入session的内容，供前端页面访问
				//设置最后一次记登录时间
				user.setLoginTime(Fn.time());
				userService.update(user);
				//类型 校区 属性
//				System.out.println("我的id"+user.getId());
				request.getSession().setAttribute("userId", user.getId());
				request.getSession().setAttribute("userType",user.getUserType());
				request.getSession().setAttribute("roleId", user.getRoleId());
				return ajaxReturn(response,null,"登录成功",1);
			}
			else{
				int id =userService.findByName(loginName);
				if(id ==0){
					return ajaxReturn(response,null,"尚无此账号信息",0);
				}
				else{
					User user = userService.findById(id);
					if(StringUtil.isEmpty(user.getTryLoginTime())||user.getTryLoginTime()==0){
						user.setTryLoginTime(Fn.time());
						user.setTyrLogin(user.getTyrLogin()+1);
					}
					else{
						if(((Fn.time()-user.getTryLoginTime())/1)>300){
							user.setTryLoginTime(Fn.time());
							user.setTyrLogin(0);
						}
						else{
							if(user.getTyrLogin()>5){
								return ajaxReturn(response,null,"您已经多次输入密码！，请五分钟后重试！",0);
							}
							user.setTyrLogin(user.getTyrLogin()+1);
						}
					}
					userService.update(user);
				}
				return ajaxReturn(response,null,"账号或密码错误",0);
			}
		}else{
			return "public/login";
		}
	}
	
	/**
	 * 注册
	 * @throws Exception 
	 */
//	@RequestMapping(value={"register"}) 
//	public String register(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
//		if(request.getMethod()=="POST"){
//			User user = new User();
//			//判断用户名有无被
//			Integer id = userService.find(request.getParameter("userName"));
//			if(id!=0){
//				return ajaxReturn(response,null,"注册失败,该用户名已经被注册",0);
//			}
//			user.setUserName(request.getParameter("userName"));
//			user.setUserPassword(request.getParameter("userPassword"));
//			if((!request.getParameter("userSex").equals(""))||(request.getParameter("userSex")!=null)){
//				user.setUserSex(Integer.valueOf(request.getParameter("userSex")));
//			}
//			user.setUserPhone(request.getParameter("userPhone"));
//			user.setTitlesId(Integer.valueOf(request.getParameter("titlesId")));
//			user.setNickName(request.getParameter("nickName"));
//			user.setIdentifyNo(request.getParameter("identifyNo"));
//			//生日处理时间戳
//			String times = request.getParameter("userBirthday");
//			SimpleDateFormat format =  new SimpleDateFormat("yyyy-MM-dd");
//			Date time = format.parse(times);
//			//user.setUserBirthday((int)(time.getTime()/1000));
//			user.setVisaCard(request.getParameter("visaCard"));
//			user.setVisaBank(request.getParameter("visaBank"));
////			user.setAreaId(Integer.valueOf(request.getParameter("areaId")));
//			user.setUniversityId(Integer.valueOf(request.getParameter("universityId")));
//			user.setCollegeId(Integer.valueOf(request.getParameter("collegeId")));
//			//user.setMajorId(Integer.valueOf(request.getParameter("majorId")));
//			user.setUserType(Integer.valueOf(request.getParameter("userType")));
//			user.setRoleId(Integer.valueOf(request.getParameter("roleId")));
//			if(userService.save(user)==1){
//				return ajaxReturn(response,null,"注册成功",1);
//			}
//			else{
//				return ajaxReturn(response,null,"注册失败",0);
//			}
//		}
//		else{
//			return "public/register";
//		}
//	}
	
	@RequestMapping(value={"logout"})
	public String logout(Model model, HttpServletRequest request, HttpServletResponse response){
		Enumeration<String> em = request.getSession().getAttributeNames();
//		System.out.println(request.getSession().getAttributeNames());
		while(em.hasMoreElements()){
			request.getSession().removeAttribute(em.nextElement());
		}
		request.getSession().invalidate();
		return "redirect:/public/login";
	}
	
	@RequestMapping(value={"contact"})
	public String contactlist(Model model, HttpServletRequest request, HttpServletResponse response){
		return ajaxReturn(response,userService.getList(null));
	}
	@RequestMapping(value={"rolelists"})
	public String rolelists(Model model, HttpServletRequest request, HttpServletResponse response){
		return ajaxReturn(response,roleService.getRoleLists(null));
	}
	
	/*
	 ** 忘记密码
	 */
	@RequestMapping(value={"forgot_pass"})
	public String forgotPass(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		if(request.getMethod().equals("POST")){
			String name = request.getParameter("user");
			String checkCode = request.getParameter("checkCode");
			String code = request.getSession().getAttribute("rand").toString();
			System.out.println(checkCode+":::::"+code);
			if(!checkCode.equals(code)){
				return ajaxReturn(response,null,"验证码错误,请检查",0);
			}
			if(name==null||name==""){
				return ajaxReturn(response,null,"无该用户信息，请检查",0);
			}
			int number = userService.findByName(name);
			User user;
			if(number==0){
				return ajaxReturn(response,null,"无该用户信息，请检查",0);
			}
			else{
				user = userService.findById(number);
			}
			int mobile_code = (int)((Math.random()*9+1)*100000);
			String content = new String("您已经成功修改密码，您的密码为"+mobile_code+"，请妥善保管并修改！");
			if(user.getUserPhone().length()!=11){
				return ajaxReturn(response,null,"改用户暂无正确的绑定手机信息！请检查",0);
			}
			int status =sendsms.sendMessage(content, user.getUserPhone());
			if(status==1){
				user.setUserPassword(String.valueOf(mobile_code));
				userService.update(user);
				System.out.println("密码发送成功！");
				return ajaxReturn(response,null,"密码已经发送到绑定的手机上！",1);
			}
			else{
				return ajaxReturn(response,null,"发送失败,请联系管理员！",0);
			}
		}else{
			return "public/login";
		}
	}
	
	
	
	@RequestMapping(value={"image"})
	public String image(Model model, HttpServletRequest request, HttpServletResponse response){
		return "public/image";
	}
	
}

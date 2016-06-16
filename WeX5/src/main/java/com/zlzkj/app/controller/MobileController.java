package com.zlzkj.app.controller;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSON;
import com.zlzkj.app.model.Activity;
import com.zlzkj.app.model.Comment;
import com.zlzkj.app.model.News;
import com.zlzkj.app.model.Recru;
import com.zlzkj.app.model.Resume;
import com.zlzkj.app.model.Text;
import com.zlzkj.app.model.User;
import com.zlzkj.app.model.Video;
import com.zlzkj.app.service.ActivityService;
import com.zlzkj.app.service.ChapterService;
import com.zlzkj.app.service.CommentService;
import com.zlzkj.app.service.MobileService;
import com.zlzkj.app.service.NewsService;
import com.zlzkj.app.service.RecruService;
import com.zlzkj.app.service.ResumeService;
import com.zlzkj.app.service.RoleService;
import com.zlzkj.app.service.TextService;
import com.zlzkj.app.service.UserService;
import com.zlzkj.app.service.VideoService;
import com.zlzkj.app.util.StringUtil;
import com.zlzkj.app.util.UIUtils;
import com.zlzkj.core.base.BaseController;
import com.zlzkj.core.util.Fn;


/**
 * 手机端控制器
 * @author JianfengJi
 *
 */
@Controller
@RequestMapping(value={"mobile"})
public class MobileController extends BaseController{
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private UserService userService;

	@Autowired
	private ChapterService chapterService;
	
	@Autowired
	private NewsService newsService;
	
	@Autowired
	private VideoService videoService;
	
	@Autowired
	private MobileService mobileService;
	
	@Autowired
	private ActivityService activityService;
	
	@Autowired
	private TextService textService;
	
	@Autowired
	private CommentService commentService;
	
	@Autowired
	private RecruService recruService;
	
	@Autowired
	private ResumeService resumeService;
	
	/**
	 * 登录
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value={"check_login"})
	public String checkLogin(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception{
		String loginName = request.getParameter("userPhone");
		String loginPwd = request.getParameter("password");
		String registrationId = request.getParameter("registrationId");
		int i = userService.checkLoginByPhone(loginName,loginPwd);
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Pragma","No-Cache");
		if(i==0){
			 //i = userService.checkLoginByPhone(loginName, loginPwd);
			return ajaxReturn(response,null,"账号密码错误",0);
		}
		else{
			User user = userService.findById(i);
			if(user.getStatus()==0){
				return ajaxReturn(response,null,"登录失败,该用户已被禁用！",0);
			}
			user.setRegistrationId(registrationId);
			//设置最后一次记登录时间
			user.setLoginTime(Fn.time());
			userService.update(user);
			Map<String, Object> data = Fn.modelToRow(user);
			//类型 校区 属性
			return ajaxReturn(response,data);
		}
	}
	
	@RequestMapping(value={"go_out"})
	public String goOut(Model model, HttpServletRequest request, HttpServletResponse response,String id) throws Exception{
		int userId = userService.findByPushId(id);
		if(userId==0){
			return ajaxReturn(response,null,"注销成功",1);
		}
		User user = userService.findById(userId);
		user.setRegistrationId("");
		int i = userService.update(user);
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Pragma","No-Cache");
		if(i==1){
			return ajaxReturn(response,null,"注销成功",1);
		}
		else{
			return ajaxReturn(response,null,"注销成功",0);
		}
	}
	
	/**
	 * 注册
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value={"register"})//,method = RequestMethod.POST)
	public String register(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception{
		String phone = request.getParameter("phone");
		String password = request.getParameter("password");
		String nickName = request.getParameter("nickName");
		String userSex = request.getParameter("usersex");
		String userAge = request.getParameter("userAge");
		String userType = request.getParameter("userType");
		String registrationId = request.getParameter("registrationId");
		User user = new User();
		user.setUserPhone(phone);
		if(userService.checkPhone(phone)){
			return ajaxReturn(response,null,"该手机已被注册！",0);
		}
		user.setUserPassword(password);
		user.setNickName(nickName);
		user.setUserSex(Integer.valueOf(userSex));
		user.setUserAge(Integer.valueOf(userAge));
		user.setUserType(Integer.valueOf(userType));
		user.setStatus(1);
		user.setImageUrl("http://42.96.159.122:8080/WeX5_data/default.jpg");
		user.setRegistrationId(registrationId);
		if(userType=="1"){
			user.setRoleId(5);
		}
		else{
			user.setRoleId(6);
		}
		int i = userService.save(user);
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Pragma","No-Cache");
		if(i ==1){
			user.setThreeId(String.valueOf(user.getId()));
			user.setLoginTime(Fn.time());
			userService.update(user);
			System.out.println(user.getId()+"bnm");
			return ajaxReturn(response,user.getId(),"注册成功",1);
		}
		return ajaxReturn(response,null,"注册失败，请稍后",0);
	}
	
	/**
	 * QQ添加新用户
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value={"add_new_three"})
	public String addNew(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception{
		String threeId = request.getParameter("threeId");  //第三方独有Id
		String threeType = request.getParameter("threeType");  //第三方类型
		String nickName = request.getParameter("nickName");
		String userSex = request.getParameter("gender");
		String imageUrl = request.getParameter("imageUrl");
		String registrationId = request.getParameter("registrationId");
		User user ;
		int id = userService.checkUserId(threeId,threeType);
		System.out.println(id);
		if(id!=0){
			//return ajaxReturn(response,null,"该手机已被注册！",0);
			System.out.println("奇怪");
			user = userService.findById(id);
		}
		else{
			user = new User();
			user.setRoleId(5);
		}
		user.setThreeId(threeId);
		user.setThreeType(Integer.valueOf(threeType));
		user.setImageUrl(imageUrl);
		user.setNickName(nickName);
		//user.setUserSex(Integer.valueOf(userSex));
		user.setUserSex(userSex.equals("男")?1:0);
		user.setStatus(1);
		user.setLoginTime(Fn.time());
		user.setRegistrationId(registrationId);
		int i=0;
		if(id==0){
			i = userService.save(user);
		}
		else{
			i = userService.update(user);
		}
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Pragma","No-Cache");
		if(i ==1){
			return ajaxReturn(response,null,"登录成功",1);
		}
		return ajaxReturn(response,null,"注册失败，请稍后",0);
	}
	
	/**
	 * 技术资讯list 
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 *///{"fID":"001","fTitle":"解读习近平致金正恩亲署函","fImg":"./images/a1.jpg","fOmit":"借劳动党成立周年致亲署函，从公开报道看并无先例。","fPostNumber":"3"},
	@RequestMapping(value={"tech_list"})
	public String techList(Model model, HttpServletRequest request, HttpServletResponse response){
//		if(request.getMethod()=="POST"){
			Map<String, Object> where = new HashMap<String,Object>();
			//判断条件约束
			String title = request.getParameter("title");
			if(!StringUtil.isEmpty(title)){
				where.put("News.title",new String[]{"like","%"+title+"%"});
			}
			where.put("News.is_image", "0");
			where.put("News.is_new", "1");
			//判断身份
//			String id = request.getSession().getAttribute("userId").toString();
//			User user = userService.findById(Integer.valueOf(id));
			response.setHeader("Access-Control-Allow-Origin", "*");
			response.setHeader("Pragma","No-Cache");
			where.put("status", "1");
			return ajaxReturn(response, mobileService.getUIGridData(where,UIUtils.getPageParams(request)));
	}
	
	/**
	 * 技术资讯list 
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 *///{"fID":"001","fTitle":"解读习近平致金正恩亲署函","fImg":"./images/a1.jpg","fOmit":"借劳动党成立周年致亲署函，从公开报道看并无先例。","fPostNumber":"3"},
	@RequestMapping(value={"all_tech_list"})
	public String allTechList(Model model, HttpServletRequest request, HttpServletResponse response){
//		if(request.getMethod()=="POST"){
			Map<String, Object> where = new HashMap<String,Object>();
			//判断条件约束
			String title = request.getParameter("title");
			if(!StringUtil.isEmpty(title)){
				where.put("News.title",new String[]{"like","%"+title+"%"});
			}
			where.put("News.is_image", "0");
			where.put("News.is_new", "1");
			//判断身份
//			String id = request.getSession().getAttribute("userId").toString();
//			User user = userService.findById(Integer.valueOf(id));
			response.setHeader("Access-Control-Allow-Origin", "*");
			response.setHeader("Pragma","No-Cache");
			where.put("status", "1");
			return ajaxReturn(response, mobileService.getAllUIGridData(where,UIUtils.getPageParams(request)));
	}
	
	
	/**
	 * 技术资讯list 
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 *///{"fID":"001","fTitle":"解读习近平致金正恩亲署函","fImg":"./images/a1.jpg","fOmit":"借劳动党成立周年致亲署函，从公开报道看并无先例。","fPostNumber":"3"},
	@RequestMapping(value={"hot_list"})
	public String hotList(Model model, HttpServletRequest request, HttpServletResponse response){
//		if(request.getMethod()=="POST"){
			Map<String, Object> where = new HashMap<String,Object>();
			//判断条件约束
			String title = request.getParameter("title");
			if(!StringUtil.isEmpty(title)){
				where.put("News.title",new String[]{"like","%"+title+"%"});
			}
			where.put("News.is_image", "0");
			where.put("News.is_hot", "1");
			//判断身份
//			String id = request.getSession().getAttribute("userId").toString();
//			User user = userService.findById(Integer.valueOf(id));
			response.setHeader("Access-Control-Allow-Origin", "*");
			response.setHeader("Pragma","No-Cache");
			where.put("status", "1");
			return ajaxReturn(response, mobileService.getUIGridData(where,UIUtils.getPageParams(request)));
	}
	
	/**
	 * 技术资讯list 
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 *///{"fID":"001","fTitle":"解读习近平致金正恩亲署函","fImg":"./images/a1.jpg","fOmit":"借劳动党成立周年致亲署函，从公开报道看并无先例。","fPostNumber":"3"},
	@RequestMapping(value={"all_hot_list"})
	public String AllHotList(Model model, HttpServletRequest request, HttpServletResponse response){
//		if(request.getMethod()=="POST"){
			Map<String, Object> where = new HashMap<String,Object>();
			//判断条件约束
			String title = request.getParameter("title");
			if(!StringUtil.isEmpty(title)){
				where.put("News.title",new String[]{"like","%"+title+"%"});
			}
			where.put("News.is_image", "0");
			where.put("News.is_hot", "1");
			//判断身份
//			String id = request.getSession().getAttribute("userId").toString();
//			User user = userService.findById(Integer.valueOf(id));
			response.setHeader("Access-Control-Allow-Origin", "*");
			response.setHeader("Pragma","No-Cache");
			where.put("status", "1");
			return ajaxReturn(response, mobileService.getAllUIGridData(where,UIUtils.getPageParams(request)));
	}
	
	/**
	 * 新闻详情
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value={"news_detail"})
	public String newsDetail(Model model, HttpServletRequest request, HttpServletResponse response){
//		if(request.getMethod()=="POST"){
			Map<String, Object> where = new HashMap<String,Object>();
			//判断条件约束
			String id = request.getParameter("id");
//			FID	数据ID
//			fTitle	新闻标题
//			fOmit	新闻简介
//			fNumber	新闻访问量
//			fContent	新闻详细内容
//			fAddTime	新闻发布时间
//			fType	新闻类型
			News news = newsService.findById(Integer.valueOf(id));
			Map<String, Object> data = new HashMap<String,Object>(); ///= Fn.modelToRow(news);
			data.put("FID", String.valueOf(news.getId()));
			data.put("fTitle", news.getTitle());
//			data.put("fOmit", data.get("content"));
			data.put("fNumber", String.valueOf(news.getHadLook()));
			data.put("fImage", news.getImageUrl());
			data.put("fContent", news.getContent());
			data.put("fAddTime", Fn.date(news.getAddTime(), "yyyy-MM-dd HH:mm:ss"));
//			data.put("fType",chapterService.findById(news).getTitle() );
			response.setHeader("Access-Control-Allow-Origin", "*");
			response.setHeader("Pragma","No-Cache");
			
			//处理json
			response.setContentType("text/html");
			response.setCharacterEncoding("UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0);
			PrintWriter pw = null;
			try {
				pw = response.getWriter();
				pw.write("["+JSON.toJSONString(data)+"]");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{  
		        pw.close();
		    } 
			return null;//ajaxReturn(response,data);
	}
	
	/**
	 * 首页轮播图
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value={"image_list"})
	public String image_list(Model model, HttpServletRequest request, HttpServletResponse response){
//		if(request.getMethod()=="POST"){
			Map<String, Object> where = new HashMap<String,Object>();
			//判断条件约束
			String title = request.getParameter("title");
			if(!StringUtil.isEmpty(title)){
				where.put("News.title",new String[]{"like","%"+title+"%"});
			}
			where.put("is_image", "1");
			//判断身份
//			String id = request.getSession().getAttribute("userId").toString();
//			User user = userService.findById(Integer.valueOf(id));
			response.setHeader("Access-Control-Allow-Origin", "*");
			response.setHeader("Pragma","No-Cache");
			where.put("status", "1");
			return ajaxReturn(response, mobileService.getImageUIGridData(where,UIUtils.getPageParams(request)));
	}
	
	/**
	 * 视频列表
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value={"video_list"})
	public String video_list(Model model, HttpServletRequest request, HttpServletResponse response){
//		if(request.getMethod()=="POST"){
			Map<String, Object> where = new HashMap<String,Object>();
			//判断条件约束
			String videoType = request.getParameter("videoType");
			String videoNumber = request.getParameter("videoNumber");
			if(!StringUtil.isEmpty(videoType)){
				where.put("Video.video_type",videoType);
			}
			if(StringUtil.isEmpty(videoNumber)||StringUtil.isNumeric(videoNumber)){
				videoNumber="3";
			}
			//判断身份
//			String id = request.getSession().getAttribute("userId").toString();
//			User user = userService.findById(Integer.valueOf(id));
			response.setHeader("Access-Control-Allow-Origin", "*");
			response.setHeader("Pragma","No-Cache");
			where.put("status", "1");
			return ajaxReturn(response, mobileService.getVideoUIGridData(where,UIUtils.getPageParams(request),Integer.valueOf(videoNumber)));
	}
	
	/**
	 * 视频详细
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value={"video_detail"})
	public String videoDetail(Model model, HttpServletRequest request, HttpServletResponse response){
//		if(request.getMethod()=="POST"){
			Map<String, Object> where = new HashMap<String,Object>();
			//判断条件约束
			String id = request.getParameter("id");
//			FID	数据ID
//			fVideoTitle	视频标题
//			fVideoImg	视频缩略图
//			fVideoOmit	视频简介
//			fCount	视频点击量
//			fAddTime	视频发布时间
//			fVideoPath	视频播放路径
//			fVideoTime	视频时长
//			fType	视频类型
			Video video = videoService.findById(Integer.valueOf(id));
			Map<String, Object> data = new HashMap<String,Object>(); ///= Fn.modelToRow(news);
			data.put("FID", String.valueOf(video.getId()));
			data.put("fVideoTitle", video.getTitle());
			data.put("fVideoImg", video.getVideoImage());
			data.put("fVideoOmit", video.getVideoIntro());
			data.put("fCount", String.valueOf(video.getClickCount()));
			data.put("fVideoPath", video.getVideoUrl());
			data.put("fVideoTime", video.getVideoTime());
			//System.out.println(video.getUserId()+"规划及半年卡没了");
			data.put("userName", userService.findNameByid(video.getUserId()));
			data.put("userImage", userService.findById(video.getUserId()).getImageUrl());
			data.put("fAddTime", Fn.date(video.getAddTime(), "yyyy-MM-dd"));
			data.put("fType",chapterService.findById(video.getVideoType()).getTitle() );
			response.setHeader("Access-Control-Allow-Origin", "*");
			response.setHeader("Pragma","No-Cache");
			//处理json
			response.setContentType("text/html");
			response.setCharacterEncoding("UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0);
			PrintWriter pw = null;
			try {
				pw = response.getWriter();
				pw.write("["+JSON.toJSONString(data)+"]");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{  
		        pw.close();
		    } 
			return null;//ajaxReturn(response,data);
	}
	
	/**
	 * 视频章节列表
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value={"chapter_list"})
	public String chapter_list(Model model, HttpServletRequest request, HttpServletResponse response){
//		if(request.getMethod()=="POST"){
			Map<String, Object> where = new HashMap<String,Object>();
			//判断条件约束
//			String videoType = request.getParameter("videoType");
//			String videoNumber = request.getParameter("videoNumber");
//			if(!StringUtil.isEmpty(videoType)){
//				where.put("Video.video_type",videoType);
//			}
//			if(StringUtil.isEmpty(videoNumber)||StringUtil.isNumeric(videoNumber)){
//				videoNumber="3";
//			}
			//判断身份
//			String id = request.getSession().getAttribute("userId").toString();
//			User user = userService.findById(Integer.valueOf(id));
			response.setHeader("Access-Control-Allow-Origin", "*");
			response.setHeader("Pragma","No-Cache");
			where.put("status", "1");
			return ajaxReturn(response, mobileService.getChapterUIGridData(where,UIUtils.getPageParams(request)));
	}
	
	/**
	 * 文档章节列表
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value={"chapter_text_list"})
	public String chapterTextList(Model model, HttpServletRequest request, HttpServletResponse response){
//		if(request.getMethod()=="POST"){
			Map<String, Object> where = new HashMap<String,Object>();
			//判断条件约束
//			String videoType = request.getParameter("videoType");
//			String videoNumber = request.getParameter("videoNumber");
//			if(!StringUtil.isEmpty(videoType)){
//				where.put("Video.video_type",videoType);
//			}
//			if(StringUtil.isEmpty(videoNumber)||StringUtil.isNumeric(videoNumber)){
//				videoNumber="3";
//			}
			//判断身份
//			String id = request.getSession().getAttribute("userId").toString();
//			User user = userService.findById(Integer.valueOf(id));
			response.setHeader("Access-Control-Allow-Origin", "*");
			response.setHeader("Pragma","No-Cache");
			where.put("status", "1");
			return ajaxReturn(response, mobileService.getChapterTextUIGridData(where,UIUtils.getPageParams(request)));
	}
	
	/**
	 * 视频首页列表
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value={"video_main_list"})
	public String video_main_list(Model model, HttpServletRequest request, HttpServletResponse response){
//		if(request.getMethod()=="POST"){
			Map<String, Object> where = new HashMap<String,Object>();
			//判断条件约束
			int number = 3;
			String chaptNumber = request.getParameter("chaptNumber");
			if(!StringUtil.isEmpty(chaptNumber)){
				if(StringUtil.isNumeric(chaptNumber)){
					number = Integer.valueOf(chaptNumber);
				}
			}
			//判断身份
			response.setHeader("Access-Control-Allow-Origin", "*");
			response.setHeader("Pragma","No-Cache");
			where.put("status", "1");
			return ajaxReturn(response, mobileService.getVideoMainUIGridData(where,UIUtils.getPageParams(request),number));
	}
	
	/**
	 * 评论的点赞
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value={"comment_like"})
	public String commentLike(Model model, HttpServletRequest request, HttpServletResponse response,int id) throws Exception{
		Comment comment = commentService.findById(id);
		comment.setLikeNumber(comment.getLikeNumber()+1);
		int i = commentService.update(comment);
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Pragma","No-Cache");
		if(i==1){
			return ajaxReturn(response, null,"点赞成功",1);
		}
		else{
			return ajaxReturn(response, null,"点赞失败",0);
		}
	}
	
	/**
	 * 点赞
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value={"comment_add"})
	public String commentAdd(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception{
		Comment comment = new Comment();
		comment.setAddTime(Fn.time());
		comment.setCommentId(Integer.valueOf(request.getParameter("id")));
		comment.setContent(String.valueOf(request.getParameter("content")));
		comment.setLikeNumber(0);
		comment.setType(Integer.valueOf(request.getParameter("type")));
		comment.setUserId(String.valueOf(request.getParameter("userId")));
		comment.setUserType(Integer.valueOf(request.getParameter("userType")));
		int i = commentService.save(comment);
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Pragma","No-Cache");
		if(i==1){
			if(Integer.valueOf(request.getParameter("type"))==0){ //是新闻
				News news = newsService.findById(Integer.valueOf(request.getParameter("id")));
				news.setHadLook(news.getHadLook()+1);
				newsService.update(news);
			}
			return ajaxReturn(response, comment.getId(),"添加成功",1);
		}
		else{
			return ajaxReturn(response, null,"添加失败",0);
		}
	}
	
	/**
	 * 评论列表
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value={"comment_list"})
	public String comment_list(Model model, HttpServletRequest request, HttpServletResponse response){
			Map<String, Object> where = new HashMap<String,Object>();
			//判断条件约束
			int number = 3;
			String listNumber = request.getParameter("listNumber");//需要多少条数据
			String type = request.getParameter("type"); //评论内容类型
			String commentId = request.getParameter("commentId"); //评论
			if(!StringUtil.isEmpty(listNumber)){
				if(StringUtil.isNumeric(listNumber)){
					number = Integer.valueOf(listNumber);
				}
			}
			if(!StringUtil.isEmpty(type)){
				where.put("type", type);
			}
			if(!StringUtil.isEmpty(commentId)){
				where.put("comment_id", commentId);
			}
			//判断身份
			response.setHeader("Access-Control-Allow-Origin", "*");
			response.setHeader("Pragma","No-Cache");
			//where.put("status", "1");
			return ajaxReturn(response, commentService.getCommentUIGridData(where,UIUtils.getPageParams(request),number));
	}
	
	@RequestMapping(value={"text_detail"})
	public String textDetail(Model model, HttpServletRequest request, HttpServletResponse response,int id){
		Text text = textService.findById(id);
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Pragma","No-Cache");
		Map<String, Object> data = Fn.modelToRow(text);
		//处理json
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		PrintWriter pw = null;
		try {
			pw = response.getWriter();
			pw.write("["+JSON.toJSONString(data)+"]");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{  
	        pw.close();
	    } 
		return null;//ajaxReturn(response,data);
	}
	
	/**
	 * 视频首页列表
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value={"activity_list"})
	public String activityList(Model model, HttpServletRequest request, HttpServletResponse response){
		Map<String, Object> where = new HashMap<String,Object>();
		//判断条件约束
		int number = 30;
		String Number = request.getParameter("Number");
		if(!StringUtil.isEmpty(Number)){
			if(StringUtil.isNumeric(Number)){
				number = Integer.valueOf(Number);
			}
		}
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Pragma","No-Cache");
		where.put("status", "1");
		return ajaxReturn(response, mobileService.getActivityUIGridData(where,UIUtils.getPageParams(request),number));
	}
	
	@RequestMapping(value={"activity_detail"})
	public String activityDetail(Model model, HttpServletRequest request, HttpServletResponse response,int id){
		Activity activity = activityService.findById(id);
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Pragma","No-Cache");
		Map<String, Object> data = Fn.modelToRow(activity);
		//处理json
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		PrintWriter pw = null;
		try {
			pw = response.getWriter();
			pw.write("["+JSON.toJSONString(data)+"]");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{  
	        pw.close();
	    } 
		return null;//ajaxReturn(response,data);
//		return ajaxReturn(response,data);
	}
	
	@RequestMapping(value={"add_registration_id"})
	public String addRegistrationId(Model model, HttpServletRequest request, HttpServletResponse response){
		String registrationId = request.getParameter("registrationId");
		User user = userService.findById(26934);
		user.setImageUrl(registrationId);
		int i=0;
		try {
			i = userService.update(user);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Pragma","No-Cache");
		if(i==1){
			ajaxReturn(response,null,"成功",1);
		}
		else{
			ajaxReturn(response,null,"失败",0);
		}
		return null;
	}
	
	@RequestMapping(value={"recru_list"})
	public String recruList(Model model, HttpServletRequest request, HttpServletResponse response){
		Map<String, Object> where = new HashMap<String,Object>();
		//判断条件约束
		int number = 30;
		String Number = request.getParameter("Number");
		if(!StringUtil.isEmpty(Number)){
			if(StringUtil.isNumeric(Number)){
				number = Integer.valueOf(Number);
			}
		}
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Pragma","No-Cache");
		where.put("status", "1");
		return ajaxReturn(response, recruService.getRecruUIGridData(where,UIUtils.getPageParams(request),number));
	}
	
	@RequestMapping(value={"recru_detail"})
	public String recruDetail(Model model, HttpServletRequest request, HttpServletResponse response,int id){
		Recru recru = recruService.findById(id);
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Pragma","No-Cache");
		Map<String, Object> data = Fn.modelToRow(recru);
		data.put("imageUrl", userService.findById(Integer.valueOf(data.get("addUser").toString())).getImageUrl());
		//处理json
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		PrintWriter pw = null;
		try {
			pw = response.getWriter();
			pw.write("["+JSON.toJSONString(data)+"]");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{  
	        pw.close();
	    } 
		return null;//ajaxReturn(response,data);
//		return ajaxReturn(response,data);
	}
	
	@RequestMapping(value={"resume_list"})
	public String resumeList(Model model, HttpServletRequest request, HttpServletResponse response){
		Map<String, Object> where = new HashMap<String,Object>();
		//判断条件约束
		int number = 30;
		String Number = request.getParameter("Number");
		if(!StringUtil.isEmpty(Number)){
			if(StringUtil.isNumeric(Number)){
				number = Integer.valueOf(Number);
			}
		}
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Pragma","No-Cache");
		where.put("status", "1");
		return ajaxReturn(response, resumeService.getResumeUIGridData(where,UIUtils.getPageParams(request),number));
	}
	
	@RequestMapping(value={"resume_detail"})
	public String resumeDetail(Model model, HttpServletRequest request, HttpServletResponse response,int id){
		Resume resume = resumeService.findById(id);
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Pragma","No-Cache");
		Map<String, Object> data = Fn.modelToRow(resume);
		data.put("imageUrl", userService.findById(Integer.valueOf(data.get("addUser").toString())).getImageUrl());
		//处理json
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		PrintWriter pw = null;
		try {
			pw = response.getWriter();
			pw.write("["+JSON.toJSONString(data)+"]");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{  
	        pw.close();
	    } 
		return null;//ajaxReturn(response,data);
//		return ajaxReturn(response,data);
	}
}

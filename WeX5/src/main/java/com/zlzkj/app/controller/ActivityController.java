package com.zlzkj.app.controller;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import com.zlzkj.app.model.Activity;
import com.zlzkj.app.model.News;
import com.zlzkj.app.model.User;
import com.zlzkj.app.service.ActivityService;
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
@RequestMapping(value={"activity"})
public class ActivityController extends BaseController{
	//嘿嘿嘿
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ActivityService activityService;
	
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
				if(status.equals("0")){
					//未解决
					where.put("status",0);
				}
				if(status.equals("1")){
					//已结解决
					where.put("status",1);
				}
			}
			//User user = userService.findById(Integer.valueOf(request.getSession().getAttribute("userId").toString()));
			return ajaxReturn(response, activityService.getUIGridData(where,UIUtils.getPageParams(request)));
		}
		else{
			return "activity/list";
		}
	}
	
	@RequestMapping(value={"list_add"},method=RequestMethod.GET)
	public String listAddGet(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception{
		return "activity/list_add";
	}
	
	@RequestMapping(value={"list_add"},method=RequestMethod.POST)
	public String listAdd(Model model, HttpServletRequest request, HttpServletResponse response,Activity activity,
			@RequestParam("imgFile") MultipartFile picWebFile ) throws Exception{
		System.out.println(":ddada");
		//保存图片
		String picWeb = ""; //图片保存名
		Map<String,Object> picWebInfo = UploadUtils.saveMultipartFile(picWebFile);
		if((Integer)picWebInfo.get("status")>0){ //上传完成
			picWeb =  UploadUtils.parseFileUrl(picWebInfo.get("saveName").toString());
		}else{ //上传出错
			return ajaxReturn(response,null,picWebInfo.get("errorMsg").toString(),0);
		}
		String endTime = request.getParameter("end");
		SimpleDateFormat format =  new SimpleDateFormat("yyyy-MM-dd");
		Date time = format.parse(endTime);
		endTime = String.valueOf((time.getTime()/1000));
		activity.setEndTime(Integer.valueOf(endTime));
		activity.setImageUrl(picWeb);
		activity.setAddTime(Fn.time());
		//User user = userService.findById(Integer.valueOf(request.getSession().getAttribute("userId").toString()));
		activity.setAddUser(Integer.valueOf(request.getSession().getAttribute("userId").toString()));
		if(activityService.save(activity)==1){
			return ajaxReturn(response, null,"添加成功",1);
		}
		else{
			return ajaxReturn(response, null,"添加失败",0);
		}
	}
	
	@RequestMapping(value={"list_edit"},method=RequestMethod.GET)
	public String listEditGet(Model model, HttpServletRequest request, HttpServletResponse response,int id) throws Exception{
		model.addAttribute("Activity",activityService.findById(id));
		model.addAttribute("endTime",Fn.date(activityService.findById(id).getEndTime(),"yyyy-MM-dd HH:mm:ss"));
		return "activity/list_edit";
	}
	
	@RequestMapping(value={"list_edit"},method=RequestMethod.POST)
	public String listEdit(Model model, HttpServletRequest request, HttpServletResponse response,Activity activity,
			@RequestParam("imgFile") MultipartFile picWebFile) throws Exception{
		if(request.getMethod()=="POST"){
			//保存图片
			String picWeb = ""; //图片保存名
			Map<String,Object> picWebInfo = UploadUtils.saveMultipartFile(picWebFile);
			if((Integer)picWebInfo.get("status")>0){ //上传完成
				picWeb =  UploadUtils.parseFileUrl(picWebInfo.get("saveName").toString());
			}else{ //上传出错
				return ajaxReturn(response,null,picWebInfo.get("errorMsg").toString(),0);
			}
			String endTime = request.getParameter("end");
			SimpleDateFormat format =  new SimpleDateFormat("yyyy-MM-dd");
			Date time = format.parse(endTime);
			endTime = String.valueOf((time.getTime()/1000));
			activity.setEndTime(Integer.valueOf(endTime));
			activity.setImageUrl(picWeb);
			activity.setAddTime(Fn.time());
			activity.setAddUser(Integer.valueOf(request.getSession().getAttribute("userId").toString()));
			if(activityService.update(activity)==1){
				return ajaxReturn(response, null,"编辑成功",1);
			}
			else{
				return ajaxReturn(response, null,"编辑失败",0);
			}
		}
		else{
			model.addAttribute("activity", activityService.findById(Integer.valueOf(request.getParameter("id"))));
			return "activity/list_edit";
		}
	}
	
	@RequestMapping(value={"list_delete"})
	public String listDelete(Model model, HttpServletRequest request, HttpServletResponse response){
		if(request.getMethod()=="POST"){
			int i = activityService.delete(Integer.valueOf(request.getParameter("id").toString()));
			if(i==1){
				return ajaxReturn(response, null,"删除成功",1);
			}
			else{
				return ajaxReturn(response, null,"删除失败",0);
			}
		}
		else{
			return "activity/list";
		}
	}

}

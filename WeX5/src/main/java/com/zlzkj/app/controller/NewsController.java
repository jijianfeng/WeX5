package com.zlzkj.app.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
import com.zlzkj.app.model.About;
import com.zlzkj.app.model.News;
import com.zlzkj.app.model.User;
import com.zlzkj.app.service.NewsService;
//import com.zlzkj.app.model.User;
import com.zlzkj.app.service.RoleService;
import com.zlzkj.app.service.UserService;
import com.zlzkj.app.util.StringUtil;
import com.zlzkj.app.util.UIUtils;
//import com.zlzkj.app.service.UserService;
//import com.zlzkj.app.util.StringUtil;
//import com.zlzkj.app.util.UIUtils;
import com.zlzkj.app.util.UploadUtils;
import com.zlzkj.core.base.BaseController;
import com.zlzkj.core.util.Fn;


/**
 * 新闻控制器
 * @author 10313_000
 *
 */
@Controller
@RequestMapping(value={"news"})
public class NewsController extends BaseController{
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private NewsService newsService;

	@RequestMapping(value={"list"})
	public String list(Model model, HttpServletRequest request, HttpServletResponse response){
		if(request.getMethod()=="POST"){
			Map<String, Object> where = new HashMap<String,Object>();
			//判断条件约束
			String title = request.getParameter("title");
			String status = request.getParameter("status");
			if(!StringUtil.isEmpty(status)){
				if(!status.equals("2")){
					where.put("News.status", status);
				}
			}
			if(!StringUtil.isEmpty(title)){
				where.put("News.title",new String[]{"like","%"+title+"%"});
			}
			where.put("is_image", "0");
			//判断身份
			String id = request.getSession().getAttribute("userId").toString();
			User user = userService.findById(Integer.valueOf(id));
			return ajaxReturn(response, newsService.getUIGridData(where,UIUtils.getPageParams(request)));
		}
		else{
			return "news/list";
		}
	}
	
	@RequestMapping(value={"list_add"},method=RequestMethod.GET)
	public String listAdd(Model model, HttpServletRequest request, HttpServletResponse response,News news) throws Exception{
		return "news/list_add";
	}
	@RequestMapping(value = "list_add",method=RequestMethod.POST)
	public String listAdd(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("imgFile") MultipartFile picWebFile ,News news){
//		Map<String, Object> data = new HashMap<String, Object>();
		String picWeb = ""; //图片保存名
		Map<String,Object> picWebInfo = UploadUtils.saveMultipartFile(picWebFile);
		if((Integer)picWebInfo.get("status")>0){ //上传完成
			picWeb =  UploadUtils.parseFileUrl(picWebInfo.get("saveName").toString());
		}else{ //上传出错
			return ajaxReturn(response,null,picWebInfo.get("errorMsg").toString(),0);
		}
		news.setAddTime(Fn.time());
		//判断身份
		String id = request.getSession().getAttribute("userId").toString();
		news.setUserId(Integer.valueOf(id));
		news.setIsImage(0);
		news.setImageUrl(picWeb);
		int i=0;
		try {
			i = newsService.save(news);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(i==1){
			return ajaxReturn(response, null, "添加成功", 1); 
		}
		else{
			return ajaxReturn(response, null, "添加失败，请联系管理员", 0); 
		} 
	}
	
	@RequestMapping(value={"list_delete"})
	public String listDelete(Model model, HttpServletRequest request, HttpServletResponse response,int id){
		if(request.getMethod()=="POST"){
			int i = newsService.delete(id);
			if(i==1){
				return ajaxReturn(response, "", "删除成功", 1); 
			}
			else{
				return ajaxReturn(response, "", "删除失败，请联系管理员", 0); 
			}
		}
		else{
			return "news/list";
		}
	}
	
	@RequestMapping(value={"list_edit"},method=RequestMethod.GET)
	public String listEditGet(Model model, HttpServletRequest request, HttpServletResponse response,News news,int id) throws Exception{
		News data = newsService.findById(id);
		model.addAttribute("news",data);
		return "news/list_edit";
	}
	
	@RequestMapping(value = "list_edit",method=RequestMethod.POST)
	public String listEdit(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("imgFile") MultipartFile picWebFile ,News news){
		Map<String, Object> data = new HashMap<String, Object>();
		String picWeb = ""; //图片保存名
		if(!picWebFile.isEmpty()){
			Map<String,Object> picWebInfo = UploadUtils.saveMultipartFile(picWebFile);
			if((Integer)picWebInfo.get("status")>0){ //上传完成
				picWeb =  UploadUtils.parseFileUrl(picWebInfo.get("saveName").toString());
			}else{ //上传出错
				return ajaxReturn(response,null,picWebInfo.get("errorMsg").toString(),0);
			}
			news.setImageUrl(picWeb);
		}
		news.setAddTime(Fn.time());
		//判断身份
		String id = request.getSession().getAttribute("userId").toString();
		news.setUserId(Integer.valueOf(id));
		news.setIsImage(0);
		int i=0;
		try {
			i = newsService.update(news);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(i==1){
			return ajaxReturn(response, null, "修改成功", 1); 
		}
		else{
			return ajaxReturn(response, null, "修改失败，请联系管理员", 0); 
		} 
	}
	
	@RequestMapping(value={"image_list"})
	public String listImage(Model model, HttpServletRequest request, HttpServletResponse response){
		if(request.getMethod()=="POST"){
			Map<String, Object> where = new HashMap<String,Object>();
			//判断条件约束
			String title = request.getParameter("title");
			String status = request.getParameter("status");
			if(!StringUtil.isEmpty(status)){
				if(!status.equals("2")){
					where.put("News.status", status);
				}
			}
			if(!StringUtil.isEmpty(title)){
				where.put("News.title",new String[]{"like","%"+title+"%"});
			}
			where.put("is_image", "1");
			//判断身份
			String id = request.getSession().getAttribute("userId").toString();
			User user = userService.findById(Integer.valueOf(id));
			return ajaxReturn(response, newsService.getImageUIGridData(where,UIUtils.getPageParams(request)));
		}
		else{
			return "news/image_list";
		}
	}
	
	@RequestMapping(value={"image_add"},method=RequestMethod.GET)
	public String listImageAddGET(Model model, HttpServletRequest request, HttpServletResponse response,News news) throws Exception{
		return "news/image_add";
	}
	
	@RequestMapping(value = "image_add",method=RequestMethod.POST)
	public String listImageAdd(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("imgFile") MultipartFile picWebFile ,News news){
		Map<String, Object> data = new HashMap<String, Object>();
		String picWeb = ""; //图片保存名
		Map<String,Object> picWebInfo = UploadUtils.saveMultipartFile(picWebFile);
		if((Integer)picWebInfo.get("status")>0){ //上传完成
			picWeb =  UploadUtils.parseFileUrl(picWebInfo.get("saveName").toString());
		}else{ //上传出错
			return ajaxReturn(response,null,picWebInfo.get("errorMsg").toString(),0);
		}
//		data.put("url", picWeb);
//		data.put("alt", "");
		news.setAddTime(Fn.time());
		//判断身份
		String id = request.getSession().getAttribute("userId").toString();
		news.setUserId(Integer.valueOf(id));
		news.setIsImage(1);
		news.setImageUrl(picWeb);
		int i=0;
		try {
			i = newsService.save(news);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(i==1){
			return ajaxReturn(response, null, "添加成功", 1); 
		}
		else{
			return ajaxReturn(response, null, "添加失败，请联系管理员", 0); 
		} 
	}
	
	@RequestMapping(value={"image_edit"},method=RequestMethod.GET)
	public String listImageSolvegGet(Model model, HttpServletRequest request, HttpServletResponse response,News news,int id) throws Exception{
		News data = newsService.findById(id);
		model.addAttribute("news",data);
		return "news/image_edit";

	}
	
	@RequestMapping(value = "image_edit",method=RequestMethod.POST)
	public String ImageEdit(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("imgFile") MultipartFile picWebFile ,News news){
		Map<String, Object> data = new HashMap<String, Object>();
		String picWeb = ""; //图片保存名
		if(!picWebFile.isEmpty()){
			Map<String,Object> picWebInfo = UploadUtils.saveMultipartFile(picWebFile);
			if((Integer)picWebInfo.get("status")>0){ //上传完成
				picWeb =  UploadUtils.parseFileUrl(picWebInfo.get("saveName").toString());
			}else{ //上传出错
				return ajaxReturn(response,null,picWebInfo.get("errorMsg").toString(),0);
			}
			news.setImageUrl(picWeb);
		}
		news.setAddTime(Fn.time());
		//判断身份
		String id = request.getSession().getAttribute("userId").toString();
		news.setUserId(Integer.valueOf(id));
		news.setIsImage(1);
		int i=0;
		try {
			i = newsService.update(news);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(i==1){
			return ajaxReturn(response, null, "修改成功", 1); 
		}
		else{
			return ajaxReturn(response, null, "修改失败，请联系管理员", 0); 
		} 
	}
	
	@RequestMapping(value={"image_delete"})
	public String listImageDelete(Model model, HttpServletRequest request, HttpServletResponse response,int id){
		if(request.getMethod()=="POST"){
			int i = newsService.delete(id);
			if(i==1){
				return ajaxReturn(response, "", "删除成功", 1); 
			}
			else{
				return ajaxReturn(response, "", "删除失败，请联系管理员", 0); 
			}
		}
		else{
			return "news/image_list";
		}
	}
	
	/**
	 * 下载图片文件
	 */
	@RequestMapping(value={"download"})
	public void downloadExample(HttpServletRequest request, HttpServletResponse response,String url) throws IOException {  
			try {
				response.setContentType("application/msexcel");
				response.setHeader("Content-disposition", "inline; filename="
						+url);
				InputStream is = new java.io.BufferedInputStream(
						new java.io.FileInputStream((url)));

				int read = 0;
				byte[] bytes = new byte[2048];

				OutputStream os = response.getOutputStream();
				while ((read = is.read(bytes)) != -1) {
					os.write(bytes, 0, read);
				}
				os.flush();
				os.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
    }
}

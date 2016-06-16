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
import com.zlzkj.app.model.User;
import com.zlzkj.app.model.Video;
import com.zlzkj.app.service.RoleService;
import com.zlzkj.app.service.UserService;
import com.zlzkj.app.service.VideoService;
import com.zlzkj.app.util.StringUtil;
import com.zlzkj.app.util.UIUtils;
import com.zlzkj.app.util.UploadUtils;
import com.zlzkj.app.util.VideoUtil;
import com.zlzkj.core.base.BaseController;
import com.zlzkj.core.util.Fn;

/**
 * 视频控制器
 * @author 10313_000
 *
 */
@Controller
@RequestMapping(value={"video"})
public class VideoController extends BaseController{
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private VideoService videoService;
	
	@RequestMapping(value={"list"})
	public String list(Model model, HttpServletRequest request, HttpServletResponse response){
		if(request.getMethod()=="POST"){
			Map<String, Object> where = new HashMap<String,Object>();
			//判断条件约束
			String title = request.getParameter("title");
			String status = request.getParameter("status");
			if(!StringUtil.isEmpty(status)){
				if(!status.equals("2")){
					where.put("Video.status", status);
				}
			}
			if(!StringUtil.isEmpty(title)){
				where.put("Video.title",new String[]{"like","%"+title+"%"});
			}
			//判断身份
			String id = request.getSession().getAttribute("userId").toString();
			User user = userService.findById(Integer.valueOf(id));
			return ajaxReturn(response, videoService.getUIGridData(where,UIUtils.getPageParams(request)));
		}
		else{
			return "video/list";
		}
	}
	
	@RequestMapping(value={"list_add"},method=RequestMethod.GET)
	public String listAddGet(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception{
		return "video/list_add";
	}
	
	@RequestMapping(value = "list_add",method=RequestMethod.POST)
	public String listAdd(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("imgFile") MultipartFile picWebFile ,@RequestParam("vidFile") MultipartFile videoWebFile ,Video video){
		Map<String, Object> data = new HashMap<String, Object>();
		String picWeb = ""; //图片保存名
		String videoWeb = ""; //视频保存名
		Map<String,Object> picWebInfo = UploadUtils.saveMultipartFile(picWebFile);
		Map<String,Object> videoWebInfo = UploadUtils.saveMultipartFile(videoWebFile);
		if((Integer)picWebInfo.get("status")>0){ //上传完成
			picWeb =  UploadUtils.parseFileUrl(picWebInfo.get("saveName").toString());
		}else{ //上传出错
			return ajaxReturn(response,null,picWebInfo.get("errorMsg").toString(),0);
		}
		if((Integer)videoWebInfo.get("status")>0){ //上传完成
			videoWeb =  UploadUtils.parseFileUrl(videoWebInfo.get("saveName").toString());
			//获取文件物理路径并判断时间长度
			String videoFile  = "";
			String osName = System.getProperty("os.name").toLowerCase();
//			System.out.println(osName+"我的系统"+osName.indexOf("windowdas"));
			if(osName.indexOf("windows")!=-1){
				videoFile = UploadUtils.getFullSavePath(videoWebInfo.get("saveName").toString())+"\\"+videoWebInfo.get("saveName").toString();
			}
			else{
				videoFile = UploadUtils.getFullSavePath(videoWebInfo.get("saveName").toString())+"/"+videoWebInfo.get("saveName").toString();
			}
			video.setVideoTime(VideoUtil.secToTime(VideoUtil.getVideoLength(videoFile)));
		}else{ //上传出错
			return ajaxReturn(response,null,videoWebInfo.get("errorMsg").toString(),0);
		}
		video.setAddTime(Fn.time());;
		//判断身份
		String id = request.getSession().getAttribute("userId").toString();
		video.setUserId(Integer.valueOf(id));
		video.setVideoImage(picWeb);
		video.setVideoUrl(videoWeb);
		int i=0;
		try {
			i = videoService.save(video);
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
	
	@RequestMapping(value={"list_edit"},method=RequestMethod.GET)
	public String listEditGet(Model model, HttpServletRequest request, HttpServletResponse response,int id) throws Exception{
		model.addAttribute("Video", videoService.findById(id));
		return "video/list_edit";
	}
	
	@RequestMapping(value = "list_edit",method=RequestMethod.POST)
	public String listEdit(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("imgFile") MultipartFile picWebFile ,@RequestParam("vidFile") MultipartFile videoWebFile ,Video video){
		Map<String, Object> data = new HashMap<String, Object>();
		String picWeb = ""; //图片保存名
		String videoWeb = ""; //视频保存名
		Map<String,Object> picWebInfo = UploadUtils.saveMultipartFile(picWebFile);
		Map<String,Object> videoWebInfo = UploadUtils.saveMultipartFile(videoWebFile);
		if(!picWebFile.isEmpty()){
			if((Integer)picWebInfo.get("status")>0){ //上传完成
				picWeb =  UploadUtils.parseFileUrl(picWebInfo.get("saveName").toString());
			}else{ //上传出错
				return ajaxReturn(response,null,picWebInfo.get("errorMsg").toString(),0);
			}
			video.setVideoImage(picWeb);
		}
		if(!videoWebFile.isEmpty()){
			if((Integer)videoWebInfo.get("status")>0){ //上传完成
				videoWeb =  UploadUtils.parseFileUrl(videoWebInfo.get("saveName").toString());
				//获取文件物理路径并判断时间长度
				String videoFile  = "";
				String osName = System.getProperty("os.name").toLowerCase();
//				System.out.println(osName+"我的系统"+osName.indexOf("windowdas"));
				if(osName.indexOf("windows")!=-1){
					videoFile = UploadUtils.getFullSavePath(videoWebInfo.get("saveName").toString())+"\\"+videoWebInfo.get("saveName").toString();
				}
				else{
					videoFile = UploadUtils.getFullSavePath(videoWebInfo.get("saveName").toString())+"/"+videoWebInfo.get("saveName").toString();
				}
				video.setVideoTime(VideoUtil.secToTime(VideoUtil.getVideoLength(videoFile)));
			}else{ //上传出错
				return ajaxReturn(response,null,videoWebInfo.get("errorMsg").toString(),0);
			}
			video.setVideoUrl(videoWeb);
		}
		video.setAddTime(Fn.time());;
		//判断身份
		String id = request.getSession().getAttribute("userId").toString();
		video.setUserId(Integer.valueOf(id));
		int i=0;
		try {
			i = videoService.update(video);
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
	public String listDelete(Model model, HttpServletRequest request, HttpServletResponse response){
		if(request.getMethod()=="POST"){
			int i = videoService.delete(Integer.valueOf(request.getParameter("id")));
			if(i==1){
				return ajaxReturn(response, null, "删除成功", 1); 
			}
			else{
				return ajaxReturn(response, null, "删除失败，请联系管理员", 0); 
			}
		}
		else{
			return "video/list";
		}
	}
}

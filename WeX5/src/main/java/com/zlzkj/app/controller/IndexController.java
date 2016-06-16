package com.zlzkj.app.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.zlzkj.app.service.ActionNodeService;
import com.zlzkj.app.util.UploadUtils;
import com.zlzkj.core.base.BaseController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 首页控制器
 */
@Controller
public class IndexController extends BaseController {
	
	@Autowired
	ActionNodeService actionNodeService;
	
	@RequestMapping(value={"/"})
	public String index(Model model,HttpServletRequest request,HttpServletResponse response) throws IOException {
		if(request.getSession().getAttribute("loginName") != null){
			model.addAttribute("topMenuList",actionNodeService.getTopNodeList(Integer.valueOf(request.getSession().getAttribute("roleId").toString())));
		}else{
//			System.out.println("返回登录");
			return "public/login";
		}
		return "index/index";
	}
	
	/**
	 * 左侧菜单
	 */
	@RequestMapping(value = "/left_menu/{topMenuId}")
	public String leftMenu(Model model, @PathVariable(value="topMenuId") int topMenuId) {
		
		model.addAttribute("leftMenuList",actionNodeService.getLeftNodeList(topMenuId));
		
		return "public/left_menu";
	}
	
	/**
	 * 网站概况
	 */
	@RequestMapping(value = "/index/dashboard")
	public String dashboard(Model model) {
		return "index/dashboard";
	}
	
	@RequestMapping(value = "upload", method = RequestMethod.POST)
	public String upload(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam("imgFile") MultipartFile picWebFile) {
		System.out.println("upload");
		Map<String, Object> data = new HashMap<String, Object>();
		String picWeb = ""; // 图片保存名
		Map<String, Object> picWebInfo = UploadUtils
				.saveMultipartFile(picWebFile);
		if ((Integer) picWebInfo.get("status") > 0) { // 上传完成
			picWeb = UploadUtils.parseFileUrl(picWebInfo.get("saveName")
					.toString());
		} else { // 上传出错
			return ajaxReturn(response, null, picWebInfo.get("errorMsg")
					.toString(), 0);
		}
		data.put("url", picWeb);
		data.put("alt", "");
		return ajaxReturn(response, data, null, 1);
	}
	
}

package com.zlzkj.app.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zlzkj.app.model.Chapter;
import com.zlzkj.app.model.Comment;
import com.zlzkj.app.service.CommentService;
import com.zlzkj.app.service.UserService;
import com.zlzkj.core.base.BaseController;
import com.zlzkj.core.util.Fn;

/**
 * 评论控制器
 */
@Controller
@RequestMapping(value={"comment"})
public class CommentController extends BaseController {
	
	@Autowired
	private CommentService commentService;
	
	@RequestMapping(value={"list_add"})
	public String listAdd(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception{
		Comment comment = new Comment();
		int i = commentService.save(comment);
		comment.setAddTime(Fn.time());
		comment.setCommentId(Integer.valueOf(request.getParameter("id")));
		comment.setContent(String.valueOf(request.getParameter("content")));
		comment.setLikeNumber(0);
		comment.setType(Integer.valueOf(request.getParameter("type")));
		comment.setUserId(String.valueOf(request.getParameter("userId")));
		if(i==1){
			return ajaxReturn(response, null,"添加成功",1);
		}
		else{
			return ajaxReturn(response, null,"添加失败",0);
		}
	}
}

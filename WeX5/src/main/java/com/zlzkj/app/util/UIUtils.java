package com.zlzkj.app.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import com.zlzkj.core.mybatis.SqlRunner;
import com.zlzkj.core.sql.Row;
import com.zlzkj.core.sql.SQLBuilder;

public class UIUtils {

	/**
     * 获取前端控件传回来的分页参数
     * @return
     */
	public static Map<String,String> getPageParams(HttpServletRequest request) {
		
		Map<String,String> map= new HashMap<String,String>();
		String[] params = {"page","rows","sort","order"};
		for(String p:params){
			String v = request.getParameter(p);
			if(v!=null){
				map.put(p, v);
				//System.out.println(v);
			}
//			else if(v.equals("sort")){
//				map.put(p, "returnTime");
//				map.put("order", "desc");
//			}
		}
		System.out.println(map.toString());
		return map;
	}
	
	/**
	 * 组装前端datagrid组件需要的数据
	 * @param totalCount 记录总数
	 * @param data 结果集
	 * @return
	 */
	public static Map<String, Object> getGridData(Integer totalCount,List<?> data){
		Map<String, Object> res = new HashMap<String, Object>();
		res.put("total", totalCount);
		res.put("rows", data);
		return res;
	}
	
	/**
	 * 获取前端表单内的值
	 * @param name
	 * @param request
	 * @return
	 */
	public static String [] getRequestContent(String name[],HttpServletRequest request){
		String contents[] = new String[name.length];
		for(int i=0;i<name.length;i++){
			contents[i] = request.getParameter(name[i]);
			if(StringUtil.isEmpty(contents[i])){
				contents[i] = "";
			}
		}
		return contents;
	}

	/**
	 * 去掉Html标签
	 * @param inputString
	 * @return
	 */
	public static  String Html2Text(String inputString) { 
        String htmlStr = inputString.replace("&nbsp;", ""); //含html标签的字符串 
            String textStr =""; 
      java.util.regex.Pattern p_script; 
      java.util.regex.Matcher m_script; 
      java.util.regex.Pattern p_style; 
      java.util.regex.Matcher m_style; 
      java.util.regex.Pattern p_html; 
      java.util.regex.Matcher m_html; 
    
      try { 
       String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>"; //定义script的正则表达式{或<script[^>]*?>[\\s\\S]*?<\\/script> } 
       String regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>"; //定义style的正则表达式{或<style[^>]*?>[\\s\\S]*?<\\/style> } 
          String regEx_html = "<[^>]+>"; //定义HTML标签的正则表达式 
       
          p_script = Pattern.compile(regEx_script,Pattern.CASE_INSENSITIVE); 
          m_script = p_script.matcher(htmlStr); 
          htmlStr = m_script.replaceAll(""); //过滤script标签 

          p_style = Pattern.compile(regEx_style,Pattern.CASE_INSENSITIVE); 
          m_style = p_style.matcher(htmlStr); 
          htmlStr = m_style.replaceAll(""); //过滤style标签 
       
          p_html = Pattern.compile(regEx_html,Pattern.CASE_INSENSITIVE); 
          m_html = p_html.matcher(htmlStr); 
          htmlStr = m_html.replaceAll(""); //过滤html标签 
       
       textStr = htmlStr; 
       
      }catch(Exception e) { 
               System.err.println("Html2Text: " + e.getMessage()); 
      } 
    
      return textStr;//返回文本字符串 
       }   
}


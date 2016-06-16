package com.zlzkj.app.util;

import java.util.HashMap;
import java.util.List;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;



@Aspect
public class CatchExceptionAspect {

	@Around("execution(* cn.xfxj.busi.*.impl.*.*(..))")
	public Object catchBusiException(ProceedingJoinPoint jp) throws Throwable {

		//System.out.println("aaaaaaaa");
		//输出内容
		Object rvt = null;
		HashMap<String, Object> map = new HashMap<String, Object>();

		Object oInput = null;
		if (jp.getArgs() != null && jp.getArgs().length > 0)
			oInput = jp.getArgs()[0];
 
		//System.out.println("oInput=="+oInput);
		String newInput = getNewInput(oInput);

		try {
			if (StringUtil.isEmpty(newInput))
				rvt = jp.proceed();
			else
			{
				Object[] args = new Object[1];
				args[0] = newInput;
				rvt = jp.proceed(args);
			}
		} catch (Exception e) {

			map.put("code", "-1");
			map.put("msg", "操作失败！");
			map.put("detail", getExceptionMsg(e.getMessage().trim()));

			return map;
		}
		map.put("code", "0");
		map.put("msg", "操作成功！");
	//	map.put("detail", rvt);
		if(rvt instanceof List<?>){//如果是列表，则是列表查询，构造2个参数作为easyui传参用途
			map.put("rows", rvt);
			map.put("total", ((List<?>) rvt).size());
		}else{  //单个记录查询
			map.put("detail", rvt);
		}
		

		
		
		return map;
	}

	private String getNewInput(Object oInput)
	{
		if (oInput instanceof String)
		{
			String sInput = StringUtil.objectToString(oInput);
			if (sInput.contains("_._"))
			{
				return StringUtil.getSubStringByIndex(sInput, 1, "_._");
			}
		}
		return "";
	}



	private String getExceptionMsg(String msg)
	{
		if (msg.equals("can't call something"))
			return "数据库连接失败，请联系信息部相关人员！";
		else
			return msg;
	}
}

package com.zlzkj.app.quartz;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.zlzkj.app.model.User;
import com.zlzkj.app.service.UserService;
import com.zlzkj.app.util.sendsms;
import com.zlzkj.core.mybatis.SqlRunner;
import com.zlzkj.core.sql.Row;
import com.zlzkj.core.sql.SQLBuilder;
import com.zlzkj.core.util.Fn;


public class Job {
//	private static Logger log = Logger.getLogger(Job.class);
//	private static final String CONFIG_QUARTZ = "quartz.properties";
	
	@Autowired
	private SqlRunner sqlRunner;
	
	@Autowired
	private UserService userService;

	
	public void work() throws IOException{
		System.out.println("自动运行程序");
	}
}

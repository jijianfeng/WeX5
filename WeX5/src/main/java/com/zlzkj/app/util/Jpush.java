package com.zlzkj.app.util;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;
import java.util.UUID;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import cn.jpush.api.JPushClient;
import cn.jpush.api.common.ClientConfig;
import cn.jpush.api.common.resp.APIConnectionException;
import cn.jpush.api.common.resp.APIRequestException;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.Notification;
import cn.jpush.api.schedule.ScheduleResult;

import com.alibaba.fastjson.JSONObject;
//import com.justep.baas.action.ActionContext;

public class Jpush {
	
	private static String appKey = "";
	private static String masterSecret = "";
	private static Boolean apnsProduction = false;  //苹果是否开发者模式
	private static JPushClient jpushClient;
	private static final String CONFIG_SENDMESS = "jpush.properties";//读取配置文件
	
	public static void main(String args[]){
		try{
//			System.out.println(appKey+":"+masterSecret);
			ScheduleResult scheduleResult= sendPushMessage("010a3e6d1cf","一个定向推送");
			//System.out.println(scheduleResult.getResponseCode()+"打打");//200为正常值
			if(scheduleResult.getResponseCode()==200){
				System.out.println("发送成功");
			}
			else{
				System.out.println("发送失败");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	
	
	public static ScheduleResult sendPushMessage(String registrationId,String message) throws APIConnectionException, APIRequestException{
		String key = getConfig("jpush.appKey");
		String secret = masterSecret = getConfig("jpush.masterSecret");
		apnsProduction = getConfig("jpush.apnsProduction").equals("true")?true:false;  //苹果是否开发者模式，没钱申请，放弃
		jpushClient = new JPushClient(secret, key);
        PushPayload payload = PushPayload.newBuilder()
                .setPlatform(Platform.all())
                .setAudience(Audience.registrationId(registrationId))
                .setNotification(Notification.alert(message))
                .build();
        payload.resetOptionsTimeToLive(86400);
        payload.resetOptionsApnsProduction(apnsProduction);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar nowTime = Calendar.getInstance();
		nowTime.add(Calendar.MINUTE, 1);
		String scheduleTime =sdf.format(nowTime.getTime());
		ScheduleResult result = jpushClient.createSingleSchedule(UUID.randomUUID().toString().replaceAll("-", ""), scheduleTime, payload);
        return result;
    }
	
	public static String getConfig(String key){ //读取配置文件
		ClassLoader loader = UploadUtils.class.getClassLoader();
		InputStream in = loader.getResourceAsStream(CONFIG_SENDMESS);
		Properties properties = new Properties();
		try {
			properties.load(in);
		} catch (IOException e) {
			//log.error("reading `" + CONFIG_QUARTZ + "` error!");
			e.printStackTrace();
		}
		return properties.getProperty(key);
	}
	
	
}

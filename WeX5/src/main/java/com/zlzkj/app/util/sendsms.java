package com.zlzkj.app.util;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.Properties;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.dom4j.Document;   
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;   
import org.dom4j.Element;   

import com.zlzkj.core.util.Fn;

public class sendsms {
	
	private static String Url = "http://106.ihuyi.cn/webservice/sms.php?method=Submit";
	
	private static final String CONFIG_SENDMESS = "sendmess.properties";
	
	public static void main(String [] args) {
		int mobile_code = (int)((Math.random()*9+1)*100000);
		String content = new String("您的验证码是：" + mobile_code + "。请不要把验证码泄露给其他人。"); 
		int number = sendMessage(content,"17826800420");
		System.out.println("发送成功"+number);
		
		//mod测试
	}
	public static int  sendMessage(String content,String phone){
		HttpClient client = new HttpClient(); 
		PostMethod method = new PostMethod(Url); 
		int number[] = new int[3];
		//client.getParams().setContentCharset("GBK");		
		client.getParams().setContentCharset("UTF-8");
		method.setRequestHeader("ContentType","application/x-www-form-urlencoded;charset=UTF-8");
		NameValuePair[] data = {//提交短信
			    new NameValuePair("account", getConfig("mess.messuser")), 
			    new NameValuePair("password", getConfig("mess.messpass")), //密码可以使用明文密码或使用32位MD5加密
			    //new NameValuePair("password", util.StringUtil.MD5Encode("密码")),
			    new NameValuePair("mobile", phone), 
			    new NameValuePair("content", content),
		};
		
		method.setRequestBody(data);		
		
		
		try {
			client.executeMethod(method);	
			
			String SubmitResult =method.getResponseBodyAsString();
					
			//System.out.println(SubmitResult);

			Document doc = DocumentHelper.parseText(SubmitResult); 
			Element root = doc.getRootElement();


			String code = root.elementText("code");	
			String msg = root.elementText("msg");	
			String smsid = root.elementText("smsid");	
			
			
			System.out.println(code);
			System.out.println(msg);
			System.out.println(smsid);
			number[2] = 0;			
			 if("2".equals(code)){
				System.out.println("短信提交成功");
				return 1;
			}
			
		} catch (HttpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;	
	}
	
	public static String getConfig(String key){
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
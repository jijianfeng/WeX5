package com.zlzkj.app.util;

import it.sauronsoftware.jave.Encoder;
import it.sauronsoftware.jave.MultimediaInfo;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

public class VideoUtil {
	public static int getVideoLength(String filePath){  
		        Date begin = new Date();  
//		        try {  
//		            getRemoteFile(  
//		                    "http://video2s.soufun.com/2015/04/27/bj/mp4/ef9ba790c9e74742ae5898aefe9232a1.mp4",  
//		                    "d:/test1.flv");  
//		        } catch (IOException e1) {  
//		            // TODO Auto-generated catch block  
//		            e1.printStackTrace();  
//		        }  
		        // 下载到本地的视频  
		        //File source = new File("d:/test1.flv");  
		        File source = new File(filePath);  
		        Encoder encoder = new Encoder();  
		        try {  
		            // 获取多媒体信息对象  
		            MultimediaInfo m = encoder.getInfo(source);  
		            // 调用方法获取时长  
		            long ls = m.getDuration();  
		            System.out.println("此视频时长为:" + ls / 1000 + "秒！");  
		            return (int)ls/1000;
		        } catch (Exception e) {  
		            e.printStackTrace();  
		        }  
		        Date end = new Date();  
		        long time = end.getTime() - begin.getTime();  
		        System.out.println("耗时" + time / 1000); 
		        return 0;
	}
	
	private static boolean getRemoteFile(String strUrl, String fileName)  
            throws IOException {  
        URL url = new URL(strUrl);  
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();  
        DataInputStream input = new DataInputStream(conn.getInputStream());  
        DataOutputStream output = new DataOutputStream(new FileOutputStream(  
                fileName));  
        byte[] buffer = new byte[1024 * 8];  
        int count = 0;  
        while ((count = input.read(buffer)) > 0) {  
            output.write(buffer, 0, count);  
        }  
        output.close();  
        input.close();  
        return true;  
    }
	
	public static String secToTime(int time) {  
        String timeStr = null;  
        int hour = 0;  
        int minute = 0;  
        int second = 0;  
        if (time <= 0)  
            return "00:00";  
        else {  
            minute = time / 60;  
            if (minute < 60) {  
                second = time % 60;  
                timeStr = unitFormat(minute) + ":" + unitFormat(second);  
            } else {  
                hour = minute / 60;  
                if (hour > 99)  
                    return "99:59:59";  
                minute = minute % 60;  
                second = time - hour * 3600 - minute * 60;  
                timeStr = unitFormat(hour) + ":" + unitFormat(minute) + ":" + unitFormat(second);  
            }  
        }  
        return timeStr;  
    }  
  
    public static String unitFormat(int i) {  
        String retStr = null;  
        if (i >= 0 && i < 10)  
            retStr = "0" + Integer.toString(i);  
        else  
            retStr = "" + i;  
        return retStr;  
    }
	
	public static void main(String args[]){
		System.out.println(secToTime(getVideoLength("F:/电影/真实魔鬼游戏BD日语中字.rmvb")));
	}
}

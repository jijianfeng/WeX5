package com.zlzkj.app.util;

import java.util.Arrays;
import java.util.HashMap;

public class Fwork1 {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		HashMap<String,String> map = new HashMap<String,String>();
		map.put("3", "1");
		map.put("2", "1");
		map.put("4", "1");
		map.put("5", "1");
		map.put("1", "1");
		maxmin(map); 
		System.out.println();
	    System.out.println("===我是分割线===");
		
		String rq = "20110803";
		cwy(rq);
	}	
	
	public static void maxmin(HashMap<String,String> pmap){
		Object key1[] = pmap.keySet().toArray();
		Arrays.sort(key1);		
		System.out.println("输出HashMap的key值，从大到小排序：");     
		for (int i=key1.length;i>0;i--){
			System.out.print(key1[i-1] + " ");
		}   
	}
	public static void cwy(String rq){
		String y = rq.substring(0, 4);
//		System.out.println(y);
		String m = rq.substring(4, 6);
//		System.out.println(m);
		int mm = Integer.parseInt(m);
		String d = rq.substring(6, 8);
		int dd = Integer.parseInt(d);
//		System.out.println(d);
		
		if (m.equals("01") && dd<=25 && dd>=1){
			System.out.println(rq + "的财务月为：" + y+m);}
		else if(m.equals("11") && dd>=25 && dd<=31){
			System.out.println(rq + "的财务月为：" + y+(mm+1));}
		else if(m.equals("12")){
			System.out.println(rq + "的财务月为：" + y+(m));}
		else if(mm >=2 && mm<=11 && (dd>=26 && dd<=31)){
			int a=(mm + 1);
			String b=a+"";
			if (b.length()==1){b="0"+b;}
			System.out.println(rq + "的财务月为：" + y+(b));}
		else if(mm >=2 && mm<=11 && (dd>=1 && dd<=25)){
			int a=(mm - 1);
			String b=a+"";
			if (b.length()==1){b="0"+b;}
			System.out.println(rq + "的财务月为：" + y+(b));}
	}
	
}

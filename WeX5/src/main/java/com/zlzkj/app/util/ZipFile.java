package com.zlzkj.app.util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.*;   
	public class ZipFile {  
	      
	    public static void main(String[] args) {  
	        //List list= getZiplist("C:\\Users\\Administrator\\Desktop\\Desktop\\Desktop.zip","C:\\text\\解压");
	    	//List list= getZiplist("E:\\tomcat\\webapps\\Thesis\\file\\20151128\\vOEsqHQnAv_!!1243601665.zip","C:\\text\\解压");
	    	List list= getZiplist("C:\\Users\\Administrator\\Desktop\\论文管理系统\\HHH.zip","C:\\text\\解压");
	    	//System.out.println(list.size());
//	        for(int i=0;i<list.size();i++){
//	        	System.out.println(list.get(i));
//	        }
	    }
	    public static List getZiplist(String file , String fileout){
			List list = new ArrayList();
			try {  
	            ZipInputStream Zin=new ZipInputStream(new FileInputStream(file));
	            BufferedInputStream Bin=new BufferedInputStream(Zin);  
	            String Parent=fileout;  
	            File Fout=null;  
	            ZipEntry entry;  
	            try { 
//	            	System.out.println(entry = Zin.getNextEntry());
//	            	System.out.println(entry.isDirectory());
	                while((entry = Zin.getNextEntry())!=null && !entry.isDirectory()){  
	                    Fout=new File(Parent,entry.getName());  
	                    if(!Fout.exists()){  
	                        (new File(Fout.getParent())).mkdirs();  
	                    }  
	                    FileOutputStream out=new FileOutputStream(Fout);  
	                    BufferedOutputStream Bout=new BufferedOutputStream(out);  
	                    int b;  
	                    while((b=Bin.read())!=-1){  
	                        Bout.write(b);  
	                    }  
	                    Bout.close();  
	                    out.close();  
	                    System.out.println(Fout+"解压成功");
	                    list.add(Fout.toString());
	                }  
	                Bin.close();  
	                Zin.close();  
	            } catch (IOException e) {  
	                // TODO Auto-generated catch block  
	                e.printStackTrace();  
	            }  
	        } catch (FileNotFoundException e) {  
	            // TODO Auto-generated catch block  
	            e.printStackTrace();  
	        }  
			return list;
		}
	  
} 
	


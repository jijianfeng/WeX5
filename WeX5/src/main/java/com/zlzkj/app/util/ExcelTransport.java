package com.zlzkj.app.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zlzkj.core.sql.Row;

import jxl.Cell;
import jxl.CellType;
import jxl.DateCell;
import jxl.NumberCell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;


public class ExcelTransport {
	
	/**
	  * Read data from a excel file
	  */
	 public static List<Object>  readExcel(String excelFileName, int sheetNum, String keys){
	     Workbook workbook = null;

	     try {
	     workbook = Workbook.getWorkbook(new File(excelFileName));
	     } catch (Exception e) {

	     }
	     Sheet sheet = workbook.getSheet(sheetNum);
	     Cell cell = null;
	     int number = sheet.getColumns();//列数
	     //System.out.println("列数："+number);
	     int rowCount=sheet.getRows();
	     //System.out.println("行数"+rowCount);
	     String[] key = keys.split(",");
	     if(number!=key.length){
	    	 return null;
	     }
//	     System.out.println(key.length+"keylength");
//	     System.out.println("key::"+key[1]);
	     List<Object> list = new ArrayList<Object>();
	     for (int i = 1; i <rowCount; i++) {
	    	 Map dbo = new HashMap();
	    	 for (int j = 0; j <key.length; j++) {
//	    		 System.out.println(j+":::"+i);
	    		cell=sheet.getCell(j, i);
	    		//System.out.println(cell.getContents()+"嵇建峰");
	    		if(j==0&&cell.getContents().equals("")){
	    			System.out.println(cell.getContents()+"嵇建峰");
	    			break;
	    		}
	    		dbo.put(key[j], (cell.getContents()));
	    	}
	    	if(dbo.size()!=0){
	    		list.add(dbo);
	    	}
	     }
	     workbook.close();
	     return list;
	 }
	 
	 /**
	  * 创建一个Excel文件
	  * @param excelFileName	Excel文件名
	  * @param sheetName		Excel页名
	  * @param headerNames		标题名（用逗号分隔）
	  * @param keys				键名（用逗号分隔）
	  * @param list				数据
	  * @return
	  * @throws Exception
	  */
	 public static void createMainExcelFile(String excelFileName, String sheetName, String headerNames, String keys, List<Row> list) throws Exception{
		String filePath = "D:\\" + excelFileName;
		 File file = new File(filePath);
		 try{
			 WritableWorkbook wwb = Workbook.createWorkbook(file);
			   WritableSheet ws = wwb.createSheet(sheetName,0);
			   String[] aheaderName = headerNames.split(",");
			   String[] akey = keys.split(",");
			   if(akey.length != aheaderName.length)
				   throw new CustomerException("键名个数和标题个数必须一样!");
			   
			   for(int col = 0; col < aheaderName.length; col++){
				   Label header = new Label(col, 0, aheaderName[col]);
				   ws.addCell(header);
			   }
			   
			   try {
				   for(int row=0; row<list.size(); row++)
				   {
					   Map obj = (Map) list.get(row);
					   for(int col=0; col<akey.length; col++)
					   {
						   Label body = new Label(col, row+1, StringUtil.objectToString(obj.get(akey[col])));
						   ws.addCell(body);
					   }
				   }
			   } catch (Exception e) {
				   e.printStackTrace();
			   }
			   wwb.write();
			   wwb.close();
		 }catch(IOException e){
			   e.printStackTrace();
		 }
		 catch(WriteException e){
			   e.printStackTrace();
		 }
	 }
	 public static void main(String args[]){
		 String file = "M:\\tomcat\\webapps\\Thesis\\file\\20150810\\venjligcMF_!!23552.xls";
		 
		 List<Object> list = readExcel(file,0,"论文标题,论文编号,要求返回时间,论文类型,论文文件名");
		 System.out.println(list.toString());
	 }
}

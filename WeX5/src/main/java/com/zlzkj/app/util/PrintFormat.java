package com.zlzkj.app.util;

import java.util.HashMap;
import java.util.List;

public class PrintFormat {

	public static void main(String[] args) throws Exception {
		
	}
	
	/**
	 * 建立报表
	 * @param lMain				主项数据
	 * @param lDetail			明细项数据，没有则为NULL
	 * @param lMainFormat		主项格式化，支持以下4种格式：
	 * 							普通：deliverOrderID_deliverOrderID
	 * 							日期：deliverDate_deliverDate_date 
	 * 							日期时间:deliverTime_deliverTime_datetime 
	 * 							浮点:amt_amt_float_0.00 
	 * @param lDetailFormat		明细项格式化，没有则为NULL
	 * @param sOperatorName		操作员名称
	 * @return					XML字符串
	 * @throws Exception
	 */
	public static String buildReport(List<HashMap<String, String>> lMain, List<HashMap<String, String>> lDetail, List<String> lMainFormat, List<String> lDetailFormat, String sOperatorName){

		StringBuffer sbRoot = new StringBuffer();    //根目录
		StringBuffer sbMain = new StringBuffer();	//主信息
		StringBuffer sbDetail = new StringBuffer(); //明细信息
		
		addReportRootHead(sbRoot);
		
		addReportMain(sbMain, lMain, lMainFormat, sOperatorName);
		addReportDetail(sbDetail, lDetail, lDetailFormat);
		
		sbRoot.append(sbMain);
		sbRoot.append(sbDetail);
		
		addReportRootTail(sbRoot);
		return sbRoot.toString();
	}
	
	/**
	 * 加入报表内容，支持格式化
	 * @param map
	 * @param sb
	 * @param lFormat
	 */
	private static void addContent(HashMap<String, String> map, StringBuffer sb, List<String> lFormat){
		newRow(sb);
		for (int i = 0; i<lFormat.size(); i++)
		{
			String sFormat = lFormat.get(i).toString();
			
			if (!getFormatByIndex(sFormat, 3).equals(""))
			{
				String sFormatType = getFormatByIndex(sFormat, 3);
				
				if (sFormatType.equals("float"))			//浮点型格式化
				{
					sFormatType = getFormatByIndex(sFormat, 4);
					addAttribute(sb, getFormatByIndex(sFormat, 1), map.get(getFormatByIndex(sFormat, 2))); 
				}else if (sFormatType.equals("date"))		//日期型格式化
				{
					addAttribute(sb, getFormatByIndex(sFormat, 1), StringUtil.stringDateToDashDateString(StringUtil.objectToString(map.get(getFormatByIndex(sFormat, 2)))) );
				}else if (sFormatType.equals("datetime"))	//日期时间型格式化
				{
					addAttribute(sb, getFormatByIndex(sFormat, 1), getPrintTime());
				}
				else
				{
					addAttribute(sb, getFormatByIndex(sFormat, 1), map.get(getFormatByIndex(sFormat, 2)));	
				}
			}else
			{
				addAttribute(sb, getFormatByIndex(sFormat, 1), map.get(getFormatByIndex(sFormat, 2)));				
			}
		}
		closeRow(sb);
	}
	
	/**
	 * 报表主信息
	 * @param sbMain
	 * @param lMain
	 * @param lFormat
	 */
	private static void addReportMain(StringBuffer sbMain, List<HashMap<String, String>> lMain, List<String> lFormat, String sOperatorName){
		if (lMain==null || lMain.size() == 0) return;

		lFormat.add("operatorName__operatorName__String__");
		lFormat.add("printTime__printTime__String__");
		addReportMainHead(sbMain);
		for (int i = 0; i<lMain.size(); i++)
		{
			HashMap<String, String> map = (HashMap<String, String>) lMain.get(i);
			map.put("operatorName", sOperatorName);
			map.put("printTime", getPrintTime());
			addContent(map, sbMain, lFormat);
		}
		addReportMainTail(sbMain);
	}
	
	/**
	 * 报表明细信息
	 * @param sbDetail
	 * @param lDetail
	 * @param lFormat
	 */
	private static void addReportDetail(StringBuffer sbDetail, List<HashMap<String, String>> lDetail, List<String> lFormat){
		if (lDetail==null || lDetail.size()==0) return;
		
		addReportDetailHead(sbDetail);
		for (int i = 0; i<lDetail.size(); i++)
		{
			HashMap<String, String> map = (HashMap<String, String>) lDetail.get(i);
			addContent(map, sbDetail, lFormat);
		}
		addReportDetailTail(sbDetail);
	}

	/**
	 * xml根节点定义开始
	 * @param sbRoot
	 */
	private static void addReportRootHead(StringBuffer sbRoot){
		sbRoot.append("<?xml version='1.0' encoding='gb2312' ?><root>");
	}

	/**
	 * xml根节点定义结束
	 * @param sbRoot
	 */
	private static void addReportRootTail(StringBuffer sbRoot){
		sbRoot.append("</root>");
	}
	
	/**
	 * 主项信息定义开始
	 * @param sbMain
	 */
	private static void addReportMainHead(StringBuffer sbMain){
		sbMain.append("<CONTENT_RECORDSET ds='1'>");
	}

	/**
	 * 主项信息定义结束
	 * @param sbMain
	 */
	private static void addReportMainTail(StringBuffer sbMain){
		sbMain.append("</CONTENT_RECORDSET>");
	}

	/**
	 * 明细项信息定义开始
	 * @param sbMain
	 */
	private static void addReportDetailHead(StringBuffer sbDetail){
		sbDetail.append("<CONTENT_RECORDSET ds='2'>");
	}

	/**
	 * 明细项信息定义结束
	 * @param sbMain
	 */
	private static void addReportDetailTail(StringBuffer sbDetail){
		sbDetail.append("</CONTENT_RECORDSET>");
	}

	/**
	 * 增加属性值
	 * @param sb
	 * @param AttName
	 * @param value
	 */
	private static void addAttribute(StringBuffer sb, String AttName, Object value) {
		sb.append(" " + AttName + "='");
		sb.append(value == null ? "" : value.toString().trim());
		sb.append("'");
	}
	
	/**
	 * 取Format行的第几个值，定义顺序为：显示字段名，数据字段名，格式化
	 * @param sFormat
	 * @param i
	 * @return
	 */
	private static String getFormatByIndex(String sFormat, int i){
		String[] aFormat = sFormat.split("__");
		if (aFormat.length >= i)
			return aFormat[i-1];
		else
			return "";
	}
	
	/**
	 * 新起一行记录
	 * @param sb
	 */
	private static void newRow(StringBuffer sb) {
		sb.append("<row");
	}
	
	/**
	 * 关闭一行记录
	 * @param sb
	 */
	private static void closeRow(StringBuffer sb) {
		sb.append("/>");
	}

	/**
	 * 取打印日期 格式：yyyy-MM-dd hh:mm:ss
	 * @return
	 */
	private static String getPrintTime(){
		return StringUtil.getCurrentTime();
	}

}

package com.zlzkj.app.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/** 
 * 创 建 人：Liuyk 创建日期：2010-09-29 最后修改人：Liuyk 最后修改日期：2010-10-11
 */
public class CheckData {
	
	public static List<String> getParamList(String... args){
		List<String> paramList = new ArrayList<String>();
		for(String param : args){
			paramList.add(param);
		}
		return paramList;
	}

	public static HashMap<String, String> getParamMap(String paramName, String... args){
		HashMap<String, String> map = new HashMap<String, String>();
		String[] aParam = paramName.split(",");
		for (int i = 0; i < aParam.length; i++)
		{
			paramName = aParam[i].trim();
			map.put(paramName, args[i]);
		}
		return map;
	}
	
	public static void check(List<String> checkDataList, List<String> paramList, String param) throws CustomerException, Exception {
		
		String sResult = "";
		String[] aParam = param.split(",");
		for (int i=0; i<aParam.length; i++)
		{
			String sParamName = aParam[i].trim();
			String row = getFieldRowByName(checkDataList, sParamName);
			HashMap<String, String> map = defineRowToMap(row);
			
			String sParamValue = paramList.get(i);
			//System.out.println("sParamName=" + sParamName + "，sParamValue=" + sParamValue);
			//System.out.println("map=" + map);
			sResult = checkIsNull(sParamValue, map);
			if (!StringUtil.isEmpty(sResult)) break;

			//System.out.println("isNull check");
			sResult = checkLength(sParamValue, map);
			if (!StringUtil.isEmpty(sResult)) break;

			//System.out.println("length check");
			sResult = checkDataType(sParamValue, map);

			if (!StringUtil.isEmpty(sResult)) break; 
			//System.out.println("dataType check");			
		}	

		//System.out.println("sResult=" + sResult);
		//if (!sResult.equals("")) throw new CustomerException(sResult);
		if (!StringUtil.isEmpty(sResult)) throw new CustomerException(sResult);
		
		//return sResult;
	}

	public static void check(List<String> checkDataList, HashMap<String, String> fieldMap, String param) throws CustomerException, Exception {
		
		String sResult = "";
		//System.out.println("fieldMap=" + fieldMap);
		//System.out.println("param=" + param);
		String[] aParam = param.split(",");
		for (int i=0; i<aParam.length; i++)
		{
			String sParamName = aParam[i].trim();
			String row = getFieldRowByName(checkDataList, sParamName);
			HashMap<String, String> map = defineRowToMap(row);
			
			String sParamValue = fieldMap.get(sParamName);
			//System.out.println("sParamName" + i + "=" + sParamName + ",sParamValue=" + sParamValue);
			sResult = checkIsNull(sParamValue, map);
			if (!StringUtil.isEmpty(sResult)) break;

			sResult = checkLength(sParamValue, map);
			if (!StringUtil.isEmpty(sResult)) break;

			sResult = checkDataType(sParamValue, map);
			if (!StringUtil.isEmpty(sResult)) break; 			
		}	

		//System.out.println("sResult=" + sResult);
		//if (!sResult.equals("")) throw new CustomerException(sResult);
		if (!StringUtil.isEmpty(sResult)) throw new CustomerException(sResult);
		
		//return sResult;
	}
	public static void check1(List<String> checkDataList, HashMap<String, Object> fieldMap, String param) throws CustomerException, Exception {
		
		String sResult = "";
		//System.out.println("fieldMap=" + fieldMap);
		//System.out.println("param=" + param);
		String[] aParam = param.split(",");
		for (int i=0; i<aParam.length; i++)
		{
			String sParamName = aParam[i].trim();
			String row = getFieldRowByName(checkDataList, sParamName);
			HashMap<String, String> map = defineRowToMap(row);
			
			Object sParamValue = fieldMap.get(sParamName);
			//System.out.println("sParamName" + i + "=" + sParamName + ",sParamValue=" + sParamValue);
			sResult = checkIsNull(sParamValue, map);
			if (!StringUtil.isEmpty(sResult)) break;
			
			sResult = checkLength(sParamValue, map);
			if (!StringUtil.isEmpty(sResult)) break;
			
			sResult = checkDataType(sParamValue, map);
			if (!StringUtil.isEmpty(sResult)) break; 			
		}	
		
		//System.out.println("sResult=" + sResult);
		//if (!sResult.equals("")) throw new CustomerException(sResult);
		if (!StringUtil.isEmpty(sResult)) throw new CustomerException(sResult);
		
		//return sResult;
	}

	public static void checkIsNull(List<String> checkDataList, String paramName, String paramValue) throws CustomerException, Exception {
		
		String sResult = "";
		String row = getFieldRowByName(checkDataList, paramName);
		HashMap<String, String> map = defineRowToMap(row);		
		sResult = checkIsNull(paramValue, map);

		if (!StringUtil.isEmpty(sResult)) throw new CustomerException(sResult);		
	}

	/**
	 * 检查参数是否为空
	 * @param sParam
	 * @param map
	 * @return
	 */
	public static void checkEntityIsNull(List<String> checkDataList, Object entity, String keyName, String keyValue){
		if (entity == null)
		{
			String row = getFieldRowByName(checkDataList, keyName);
			HashMap<String, String> map = defineRowToMap(row);
			
			throw new CustomerException("不存在【"  + map.get("title") + "】值为【" + keyValue + "】的记录！");
		}
	}

	public static void checkUniqueIndex(int count, List<String> checkDataList, String fieldName, String... paramList) throws CustomerException, Exception {
		if (count == 0) return;
		
		String[] aFieldName = fieldName.split(",");
		String sResult = "";

		for (int i = 0; i < aFieldName.length; i++)
		{
			String fName = aFieldName[i].trim();
			String row = getFieldRowByName(checkDataList, fName);
			HashMap<String, String> map = defineRowToMap(row);
			
			if (!sResult.equals("")) sResult += "，";
			sResult += map.get("title") + ":" + paramList[i];
		}
		sResult += "，违反唯一索引！请检查。";
		
		throw new CustomerException(sResult);
	}
	
	/**
	 * 检查参数是否为空
	 * @param sParam
	 * @param map
	 * @return
	 */
	private static String checkIsNull(String sParam, HashMap<String, String> map){
		if (StringUtil.isEmpty(map.get("nullable")) || (!map.get("nullable").equals("false")))
			return "";

		//System.out.print("title=" + map.get("title"));
		if (StringUtil.isEmpty(sParam))
			return "参数【" + map.get("title") + "】不能为空！";
		else
			return "";
	}
	/**
	 * 检查参数是否为空
	 * @param sParam
	 * @param map
	 * @return
	 */
	private static String checkIsNull(Object sParam, HashMap<String, String> map){
		if (StringUtil.isEmpty(map.get("nullable")) || (!map.get("nullable").equals("false")))
			return "";
		
		//System.out.print("title=" + map.get("title"));
		if (StringUtil.isEmpty(sParam))
			return "参数【" + map.get("title") + "】不能为空！";
		else
			return "";
	}

	/**
	 * 检查参数长度是否超出
	 * @param sParam
	 * @param map
	 * @return
	 */
	private static String checkLength(String sParam, HashMap<String, String> map){
		if (StringUtil.isEmpty(map.get("length")) || StringUtil.isEmpty(sParam))
			return "";

		int iLength = Integer.parseInt(map.get("length"));
		if (iLength < sParam.length())
			return "参数【" + map.get("title") + "】长度超出" + iLength + "个字符！";
		else
			return "";
	}
	/**
	 * 检查参数长度是否超出
	 * @param sParam
	 * @param map
	 * @return
	 */
	private static String checkLength(Object sParam, HashMap<String, String> map){
		if (StringUtil.isEmpty(map.get("length")) || StringUtil.isEmpty(sParam))
			return "";
		
		int iLength = Integer.parseInt(map.get("length"));
		if (iLength < StringUtil.objectToString(sParam).length())
			return "参数【" + map.get("title") + "】长度超出" + iLength + "个字符！";
		else
			return "";
	}

	/**
	 * 检查参数字符合法性
	 * @param sParam
	 * @param map
	 * @return
	 */
	private static String checkDataType(String sParam, HashMap<String, String> map){
		if (StringUtil.isEmpty(map.get("type")) || StringUtil.isEmpty(sParam))
			return "";

		boolean nullable = false;
		if (StringUtil.isEmpty(map.get("nullable")) || (!map.get("nullable").equals("false")))
			nullable = true;
		
		if (nullable && StringUtil.isEmpty(sParam))
		{
		   //System.out.print(map.get("title") + ":" + nullable + ":" + sParam);
		   return null;
		}
		
		//System.out.print(map.get("title") + "------");
		
		String dataType = map.get("type");
		if (dataType.equals("Integer"))
			return checkDataTypeInteger(sParam, map.get("title"));
		else if (dataType.equals("Double"))
			return checkDataTypeDouble(sParam, map.get("title"));
		else if (dataType.equals("Date"))
			return checkDataTypeDate(sParam, map.get("title"));
		else
			return "";
	}
	/**
	 * 检查参数字符合法性
	 * @param sParam
	 * @param map
	 * @return
	 */
	private static String checkDataType(Object sParam, HashMap<String, String> map){
		if (StringUtil.isEmpty(map.get("type")) || StringUtil.isEmpty(sParam))
			return "";
		
		boolean nullable = false;
		if (StringUtil.isEmpty(map.get("nullable")) || (!map.get("nullable").equals("false")))
			nullable = true;
		
		if (nullable && StringUtil.isEmpty(sParam))
		{
			//System.out.print(map.get("title") + ":" + nullable + ":" + sParam);
			return null;
		}
		
		//System.out.print(map.get("title") + "------");
		
		String dataType = map.get("type");
		if (dataType.equals("Integer"))
			return checkDataTypeInteger(sParam, map.get("title"));
		else if (dataType.equals("Double"))
			return checkDataTypeDouble(sParam, map.get("title"));
		else if (dataType.equals("Date"))
			return checkDataTypeDate(sParam, map.get("title"));
		else
			return "";
	}
	
	/**
	 * 检查数字是否为合法整数
	 * @param sParam
	 * @param sTitle
	 * @return
	 */
	private static String checkDataTypeInteger(String sParam, String sTitle){

		if(StringUtil.isEmpty(StringUtil.stringToInteger(sParam)))
		{
			return "参数【" + sTitle + "】不是合法的整数！";
		}
		else
			return "";
	}
	/**
	 * 检查数字是否为合法整数
	 * @param sParam
	 * @param sTitle
	 * @return
	 */
	private static String checkDataTypeInteger(Object sParam, String sTitle){
		
		if(StringUtil.isEmpty(StringUtil.objectToInteger(sParam)))
		{
			return "参数【" + sTitle + "】不是合法的整数！";
		}
		else
			return "";
	}

	/**
	 * 检查数字是否为浮点数
	 * @param sParam
	 * @param sTitle
	 * @return
	 */
	private static String checkDataTypeDouble(String sParam, String sTitle){

		if(StringUtil.isEmpty(StringUtil.stringToDouble(sParam)))
		{
			return "参数【" + sTitle + "】不是合法的浮点数！";
		}
		else
			return "";
	}
	/**
	 * 检查数字是否为浮点数
	 * @param sParam
	 * @param sTitle
	 * @return
	 */
	private static String checkDataTypeDouble(Object sParam, String sTitle){
		
		if(StringUtil.isEmpty(StringUtil.objectTodouble(sParam)))
		{
			return "参数【" + sTitle + "】不是合法的浮点数！";
		}
		else
			return "";
	}

	/**
	 * 检查数字是否为日期
	 * @param sParam
	 * @param sTitle
	 * @return
	 */
	private static String checkDataTypeDate(String sParam, String sTitle){

		//System.out.println("sParam" + sParam);
		//System.out.println("sParam_date" + StringUtil.stringToDate(sParam));
		
		if(StringUtil.isEmpty(StringUtil.stringToDate(sParam)))
		{
			return "参数【" + sTitle + "】不是合法的日期！正确的格式是【YYYYMMDD】";
		}
		else
			return "";
	}
	/**
	 * 检查数字是否为日期
	 * @param sParam
	 * @param sTitle
	 * @return
	 */
	private static String checkDataTypeDate(Object sParam, String sTitle){
		
		//System.out.println("sParam" + sParam);
		//System.out.println("sParam_date" + StringUtil.stringToDate(sParam));
		if(StringUtil.isEmpty(sParam))
			return "";
		if(StringUtil.isEmpty(StringUtil.objectToDate(sParam)))
		{
			return "参数【" + sTitle + "】不是合法的日期！正确的格式是【YYYYMMDD】";
		}
		else
			return "";
	}

	/**
	 * 把定义行先转换为HashMap方便后面取值
	 * @param row
	 * @return
	 */
	private static HashMap<String, String> defineRowToMap(String row){
		String[] aDefine = row.split(" ");
		HashMap<String, String> map = new HashMap<String, String>();
		int j = 0;
		String sKey = "", sValue = "";
		for (int i=0; i<aDefine.length; i++)
		{
			String attribute = aDefine[i].trim();
			if ((attribute.equals("<field"))||(attribute.equals("/>"))||(attribute.equals("="))||(attribute.equals("")))
				continue;
			
			j++;
			if (j % 2 == 0)
			{
				sValue = aDefine[i].replace("'", "").replace("/>", "");
				map.put(sKey, sValue);
			}else
			{
				sKey = aDefine[i];
			}				
		}
		return map;
	}
	
	/**
	 * 按字段名取定义的行
	 * @param sParam
	 * @return
	 */
	private static String getFieldRowByName(List<String> checkList, String sParam){
		
		String row = "";
		for (int i=0; i<checkList.size(); i++)
		{
			row = checkList.get(i);
			if (row.indexOf("'" + sParam + "'") > 0) break;
		}
		return row;
	}
	
	
	
	/**
	 * 按属性名称取属性值
	 * @param row
	 * @param sAttribute
	 * @return
	 
	private static String getFieldAttributeValue(String row, String sAttribute){
		
		int i, j, k = 0;
		i = row.indexOf(sAttribute);
		j = row.indexOf(sAttribute, i);
		k = row.indexOf(sAttribute, j);
		return row.substring(j, k);		
	}*/
	
}

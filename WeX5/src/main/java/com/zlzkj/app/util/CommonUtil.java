package com.zlzkj.app.util;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.lang.reflect.Field;


/**
 *  创 建 人：Liuyk 
 *  创建日期：2010-11-21 
 *  最后修改人：Liuyk 
 *  最后修改日期：2010-11-21
 */
public class CommonUtil {

	/**
	 * 把一个List分割为若干个List，然后以某个值为key，封装为一个HashMap
	 * 
	 * @param lstSource		List
	 * @param keyName   健值
	 * @return
	 * @throws Exception
	 */
	public static HashMap<String,Object> splitListMapObjectToHashMap(List<HashMap<String, Object>> lstSource, String keyName) throws Exception{

		if (isEmpty(lstSource)) return null;
		
		List<HashMap<String, Object>> lst01 = new ArrayList<HashMap<String, Object>>();
		lst01.addAll(lstSource);
		HashMap<String,Object> mapResult = new HashMap<String,Object>();
		while(lst01.size() >0)
		{			
			List<HashMap<String, Object>> lstItem = new ArrayList<HashMap<String, Object>>();
			String keyValue = "";
			int j = lst01.size()-1;
			for (int i = lst01.size()-1; i>=0; i--)
			{
				HashMap<String,Object> map = lst01.get(i);
				String tKeyValue = StringUtil.objectToString(map.get(keyName));
				if (i == j)
				{
					keyValue = tKeyValue;
					lstItem.add(map);
					lst01.remove(i);
				}else
				{
					if (keyValue.equals(tKeyValue))
					{
						lstItem.add(map);
						lst01.remove(i);
					}
				}
			}
			mapResult.put(keyValue, lstItem);			
		}
		return mapResult;
	}

	/**
	 * 把一个List分割为若干个Hash，然后以某个值为key，封装为一个HashMap
	 * 
	 * @param lstSource		List
	 * @param keyName   健值
	 * @return
	 * @throws Exception
	 */
	public static HashMap<String,Object> changeListMapObjectToHashMapMap(List<HashMap<String, Object>> lstSource, String keyName) throws Exception{

		if (isEmpty(lstSource)) return null;

		HashMap<String,Object> mapResult = new HashMap<String,Object>();
		for (int i = 0; i < lstSource.size(); i++)
		{
			HashMap<String,Object> map = lstSource.get(i);
			String tKeyValue = StringUtil.objectToString(map.get(keyName));
			mapResult.put(tKeyValue, map);
		}		
		return mapResult;
	}

	/**
	 * 把一个List分割为若干个List，然后以某个值为key，封装为一个HashMap
	 * 
	 * @param lstSource		List
	 * @param keyName   健值
	 * @return
	 * @throws Exception
	 */
	public static HashMap<String,Object> splitListMapStringToHashMap(List<HashMap<String, String>> lstSource, String keyName) throws Exception{

		if (isEmpty(lstSource)) return null;

		List<HashMap<String, String>> lst01 = new ArrayList<HashMap<String, String>>();
		lst01.addAll(lstSource);
		
		HashMap<String,Object> mapResult = new HashMap<String,Object>();
		while(lst01.size() >0)
		{
			List<HashMap<String, String>> lstItem = new ArrayList<HashMap<String, String>>();
			String keyValue = "";
			int j = lst01.size()-1;
			for (int i = lst01.size()-1; i>=0; i--)
			{
				HashMap<String,String> map = lst01.get(i);
				String tKeyValue = StringUtil.objectToString(map.get(keyName));
				if (i == j)
				{
					keyValue = tKeyValue;
					lstItem.add(map);
					lst01.remove(i);
				}else
				{
					if (keyValue.equals(tKeyValue))
					{
						lstItem.add(map);
						lst01.remove(i);
					}
				}
			}
			mapResult.put(keyValue, lstItem);			
		}
		return mapResult;
	}

	/**
	 * 把一个List分割为若干个List，然后以某个值为key，封装为一个HashMap
	 * 
	 * @param lstSource		List
	 * @param keyName   健值
	 * @return
	 * @throws Exception
	 */
	public static HashMap<String,Object> splitListEntityToHashMap(List<?> lstSource, String keyName) throws Exception{

		if (isEmpty(lstSource)) return null;
		
		//List<Object> lst01 = new ArrayList<Object>();
		//lst01.addAll(lstSource);
		HashMap<String,Object> mapResult = new HashMap<String,Object>();
		while(lstSource.size() >0)
		{			
			List<Object> lstItem = new ArrayList<Object>();
			String keyValue = "";
			int j = lstSource.size()-1;
			for (int i = lstSource.size()-1; i>=0; i--)
			{
				Object obj = lstSource.get(i);
				Field f = obj.getClass().getDeclaredField(keyName);
				f.setAccessible(true);
				String tKeyValue = StringUtil.objectToString(f.get(obj));
				
				if (i == j)
				{
					keyValue = tKeyValue;
					lstItem.add(obj);
					lstSource.remove(i);
				}else
				{
					if (keyValue.equals(tKeyValue))
					{
						lstItem.add(obj);
						lstSource.remove(i);
					}
				}
			}
			mapResult.put(keyValue, lstItem);			
		}
		return mapResult;
	}

	/**
	 * 取List里某一列的值，用逗号分隔
	 * 
	 * @param lst		List
	 * @param keyName   健值
	 * @return
	 * @throws Exception
	 */
	public static String getListMapStringColumnValue(List<HashMap<String, String>> lst, String keyName) throws Exception{

		if (isEmpty(lst)) return "";
		String sResult = "";

		for (int i = 0; i < lst.size(); i++)
		{
			HashMap<String, String> map = lst.get(i);
			String keyValue = map.get(keyName);
			sResult = StringUtil.getSplitJointStr(sResult, keyValue);
		}

		return sResult;
	}

	/**
	 * 取List里某一列的值，用逗号分隔
	 * 
	 * @param lst		List
	 * @param keyName   健值
	 * @return
	 * @throws Exception
	 */
	public static String getListMapObjectColumnValue(List<HashMap<String, Object>> lst, String keyName) throws Exception{

		if (isEmpty(lst)) return "";
		String sResult = "";

		for (int i = 0; i < lst.size(); i++)
		{
			HashMap<String, Object> map = lst.get(i);
			String keyValue = StringUtil.objectToString(map.get(keyName));
			sResult = StringUtil.getSplitJointStr(sResult, keyValue);
		}

		return sResult;
	}
	
	/**
	 * 取List里某一列的值，用逗号分隔
	 * 
	 * @param lst		List
	 * @param keyName   健值
	 * @return
	 * @throws Exception
	 */
	public static double getListMapObjectColumnSumValue(List<HashMap<String, Object>> lst, String keyName) throws Exception{

		if (isEmpty(lst)) return 0;
		double dResult = 0;

		for (int i = 0; i < lst.size(); i++)
		{
			HashMap<String, Object> map = lst.get(i);
			double dKeyValue = StringUtil.stringToDouble(StringUtil.objectToString(map.get(keyName))).doubleValue();
			dResult += dKeyValue;
		}

		return dResult;
	}

	/**
	 * 取List里某一列的值，用逗号分隔
	 * 
	 * @param lst		List
	 * @param keyName   健值
	 * @return
	 * @throws Exception
	 */
	public static double getListMapStringColumnSumValue(List<HashMap<String, String>> lst, String keyName) throws Exception{

		if (isEmpty(lst)) return 0;
		double dResult = 0;

		for (int i = 0; i < lst.size(); i++)
		{
			HashMap<String, String> map = lst.get(i);
			double dKeyValue = StringUtil.stringToDouble(map.get(keyName)).doubleValue();
			dResult += dKeyValue;
		}

		return dResult;
	}
	
	/**
	 * 从HashMap中按关键字提取List
	 * @param map
	 * @param key
	 * @return
	 */
	public static List<HashMap<String, Object>> getListMapObjectFromHashMapByKey(HashMap<String, Object> map, String key){
		if (isEmpty(map)) return null;
		return (List<HashMap<String, Object>>)map.get(key);
	}

	/**
	 * 从HashMap中按关键字提取List
	 * @param map
	 * @param key
	 * @return
	 */
	public static List<HashMap<String, String>> getListMapStringFromHashMapByKey(HashMap<String, Object> map, String key){
		if (isEmpty(map)) return null;
		return (List<HashMap<String, String>>)map.get(key);
	}
	
	public static boolean isEmpty(List lst){
		return lst == null || lst.size() == 0;
	}

	public static boolean isEmpty(HashMap map){
		return map == null || map.size() == 0;
	}



	public static String getPagingStartNum(String page, String perPageNum, int totalNum){
		return "" + getPagingStartNum(StringUtil.stringToInteger(page), StringUtil.stringToInteger(perPageNum), totalNum);
	}

	public static String getPagingEndNum(String page, String perPageNum, int totalNum){
		return "" + getPagingEndNum(StringUtil.stringToInteger(page), StringUtil.stringToInteger(perPageNum), totalNum);
	}
	
	/**
	 * 取分页的开始号码，如果页面超出总页数则取最后一页的开始号
	 * 
	 * @param page			页码
	 * @param perPageNum	每页记录数
	 * @param totalNum		总记录数
	 * @return
	 */
	public static int getPagingStartNum(int page, int perPageNum, int totalNum){
		int totalPag = getTotalPag(perPageNum, totalNum);
		int page2 = page;
		if (page>totalPag) page2 = totalPag;

		System.out.println("page=" + page + ",perPageNum=" + perPageNum + ",totalNum=" + totalNum);
		if (page2>0)
			return (page2 - 1) * perPageNum + 1;
		else
			return 0;
	}

	/**
	 * 取总页数
	 * 
	 * @param perPageNum	每页记录数
	 * @param totalNum		总记录数
	 * @return
	 */
	public static int getTotalPag(int perPageNum, int totalNum){
		System.out.println("totalNum=" + totalNum + ",perPageNum=" + perPageNum + ",(double)totalNum / perPageNum=" + (double)totalNum / perPageNum);
		return (int)Math.ceil((double)totalNum / perPageNum);
	}

	/**
	 * 取分页的开始号码，如果页面超出总页数则取最大数
	 * 
	 * @param page			页码
	 * @param perPageNum	每页记录数
	 * @param totalNum		总记录数
	 * @return
	 */
	public static int getPagingEndNum(int page, int perPageNum, int totalNum){
		int endNum = page * perPageNum;
		if (endNum > totalNum) endNum = totalNum;
		
		return endNum;
	}


	/**
	 * 把mongodb查询出来包含子文档的内容提升上来和主文档数据混在一起
	 * 如：list : [{"a":1},[{"b":2},{"c":3}]] 提升后变成：list:[{"a":1,"b":2},{"a":1,"c":3}] 
	 * 
	 * @param list
	 * @return
	 * @throws Exception
	 */
	public static List<Object> upgradeList(List<Object> list) throws Exception {
		
		List<Object> lstNew = new ArrayList<Object>();
		
		if (isEmpty(list)) return lstNew;
		
		boolean isFound = false;
		
		for(int i = 0; i < list.size(); i++){
			Map<String,Object> obj = (HashMap<String,Object>) list.get(i);

			isFound = false;
			for (String oKey : obj.keySet()) {

				if(obj.get(oKey) instanceof List){

					isFound = true;
					if(!StringUtil.isEmpty(oKey)){
						List<Object> lstDetail = (List<Object>)obj.get(oKey);
						obj.remove(oKey);
						
						if(lstDetail.size() > 0){
							for (int j = 0; j < lstDetail.size(); j++){
								Map objDetail = (Map) lstDetail.get(j);
								objDetail.putAll(obj);
								lstNew.add(objDetail);
							}
						}
					}else 
						lstNew.add(obj);
					
					break;
				}
			}
			
			if (!isFound) lstNew.add(obj);
		}
		return lstNew;
	}
	
	public static void limitList(List<Object> list, HashMap<String, String> map) throws Exception {

		if (isEmpty(list)) return;
		
		for (int i = list.size() - 1; i >= 0; i--){
			Map obj = (Map) list.get(i);

			boolean isEqual = true;
			for (String oKey : map.keySet()) {
				if (!obj.get(oKey).equals(map.get(oKey)))
					isEqual = false;					
			}
			
			if (!isEqual) list.remove(i);
		}
	}
	//空白发票作废原因
	public static String getInvoiceNoUseCancelReason(String type){
		
		if (type.equals("1"))
			return "遗失";
		else if (type.equals("2"))
			return "被盗";
		else if (type.equals("3"))
			return "缴销";
		else if (type.equals("9"))
			return "其它";
		else
			return "其它";
	}
	
	/**
	 * 将一个JavaBean对象转换为HashMap
	 * @param obj
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public static HashMap<String, Object> objToHash1(Object obj) throws IllegalArgumentException,IllegalAccessException {
	     
	    HashMap<String, Object> hashMap = new HashMap<String, Object>();
	    Class clazz = obj.getClass();
	    List<Class> clazzs = new ArrayList<Class>();
	     
	    do {
	        clazzs.add(clazz);
	        clazz = clazz.getSuperclass();
	    } while (!clazz.equals(Object.class));
	     
	    for (Class iClazz : clazzs) {
	        Field[] fields = iClazz.getDeclaredFields();
	        for (Field field : fields) {
	            Object objVal = null;
	            field.setAccessible(true);
	            objVal = field.get(obj);
	            hashMap.put(field.getName(), objVal);
	        }
	    }
	     
	    return hashMap;
	}
	
	/**
	 * 
	 * @param obj
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public static HashMap<String, String> objToHash(Object obj) {
		
		HashMap<String, String> hashMap = new HashMap<String, String>();
		Class clazz = obj.getClass();
		List<Class> clazzs = new ArrayList<Class>();
		
		do {
			clazzs.add(clazz);
			clazz = clazz.getSuperclass();
		} while (!clazz.equals(Object.class));
		
		for (Class iClazz : clazzs) {
			Field[] fields = iClazz.getDeclaredFields();
			for (Field field : fields) {
				String objVal = null;
				field.setAccessible(true);
				try {
					objVal = StringUtil.objectToString(field.get(obj));
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				hashMap.put(field.getName(), objVal);
			}
		}
		
		return hashMap;
	}
}

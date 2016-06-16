package com.zlzkj.app.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * 创 建 人：Liuyk 创建日期：2010-10-11  最后修改人：Liuyk 最后修改日期：2010-10-11
 */
public class FormatData {


	/**
	 * 取JSON字符串列表
	 * @param lst				数据列表
	 * @param fieldNames		需要提取的字段名，用英文逗号分隔
	 * @return					JSON列表字符串
	 * @throws Exception
	 */
	public static String changeListEntityToJson(List<?> lst) throws Exception{
		return changeListEntityToJson(lst, "");
	}
	
	/**
	 * 取JSON字符串列表
	 * @param lst				数据列表
	 * @param fieldNames		需要提取的字段名，用英文逗号分隔
	 * @return					JSON列表字符串
	 * @throws Exception
	 */
	public static String changeListEntityToJson(List<?> lst, String fieldNames) throws Exception{
		if (CommonUtil.isEmpty(lst)) return "";
		
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		//sb.append(lst.get(0).getClass().getSimpleName());
		//sb.append(":{");
		for (int i = 0; i < lst.size(); i++)
		{
			if (i > 0) sb.append(","); 
			sb.append(getEntityToJson(lst.get(i), fieldNames));
		}
		sb.append("]");
		return sb.toString();
	}

	/**
	 * HashMap转为JSON字符串
	 *
	 * @param map				需要转换的HashMap
	 * @return					JSON列表字符串
	 * @throws Exception
	 */
	public static String changeListMapToJson(List<HashMap<String, Object>> lst) throws Exception{
		if (CommonUtil.isEmpty(lst)) return "";

		StringBuffer sb = new StringBuffer();
		sb.append("[");
		for(int i = 0; i < lst.size(); i++)
		{
			if (i>0) sb.append(",");
			sb.append(getMapToJson(lst.get(i)));
		    //System.out.println("00=" + getMapToJson(lst.get(i)));
		}
		sb.append("]");
	    //System.out.println("01=" + sb.toString());
		return sb.toString();
	}

	/**
	 * HashMap转为JSON字符串
	 *
	 * @param map				需要转换的HashMap
	 * @return					JSON列表字符串
	 * @throws Exception
	 */
	public static String changeMapToJson(HashMap<String, Object> map) throws Exception{
		if (CommonUtil.isEmpty(map)) return "";
		
		return "[" + getMapToJson(map) + "]";
	}

	/**
	 * HashMap转为JSON字符串
	 *
	 * @param map				需要转换的HashMap
	 * @return					JSON列表字符串
	 * @throws Exception
	 */
	public static String getMapToJson(HashMap<String, Object> map) throws Exception{
		if (CommonUtil.isEmpty(map)) return "";
		
		StringBuffer sb = new StringBuffer();
		sb.append("{");

		int i = 0;
		for (Iterator<String> iter = map.keySet().iterator(); iter.hasNext();) {    

			if (i > 0) sb.append(",");
		    String key = iter.next();
		    Object val = map.get(key);
		    //System.out.println("key=" + key + ",val=" + val);
			sb.append(getMapField(key, val));
			i++;
		}  
		
		sb.append("}");
		return sb.toString();
	}

	/**
	 * HashMap转为JSON字符串
	 *
	 * @param map				需要转换的HashMap
	 * @return					JSON列表字符串
	 * @throws Exception
	 */
	public static String changeMapToJson2(HashMap<String, String> map) throws Exception{
		if (CommonUtil.isEmpty(map)) return "";
		
		return "[" + getMapToJson2(map) + "]";
	}

	/**
	 * HashMap转为JSON字符串
	 *
	 * @param map				需要转换的HashMap
	 * @return					JSON列表字符串
	 * @throws Exception
	 */
	public static String getMapToJson2(HashMap<String, String> map) throws Exception{
		if (CommonUtil.isEmpty(map)) return "";
		
		StringBuffer sb = new StringBuffer();
		sb.append("{");

		int i = 0;
		for (Iterator<String> iter = map.keySet().iterator(); iter.hasNext();) {    

			if (i > 0) sb.append(",");
		    String key = iter.next();
		    String val = map.get(key);
		    //System.out.println("key=" + key + ",val=" + val);
			sb.append(getMapField(key, val));
			i++;
		}  
		
		sb.append("}");
		return sb.toString();
	}

	private static String getMapField(String Key, Object obj) throws Exception
	{
		if (isDate(obj))
			return "\"" + Key + "\":\"" + getFieldValue(obj, "class java.util.Date") + "\"";
		else
			return "\"" + Key + "\":\"" + getFieldValue(obj, "class java.util.String") + "\"";
	}
	
    public static boolean isDate(Object obj)
    {
        return obj instanceof Date;
    }


	
	/**
	 * 取JSON字符串列表
	 * @param lst				数据列表
	 * @param fieldNames		需要提取的字段名，用英文逗号分隔
	 * @return					JSON列表字符串
	 * @throws Exception
	 */
	public static void printListObject(List<?> lst, String... fieldNames) throws Exception{
		if (CommonUtil.isEmpty(lst))
		{
			System.out.println("该List没有数据");
			return;
		}
		
		String fieldName = "";
		if (fieldNames.length >0) fieldName = fieldNames[0];
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < lst.size(); i++)
		{
			if (i > 0) sb.append(","); 
			System.out.println(getEntityToJson(lst.get(i), fieldName));
			if (i==100) break;
		}
	}
	
	/**
	 * 取JSON字符串列表
	 * @param lst				数据列表
	 * @param fieldNames		需要提取的字段名，用英文逗号分隔
	 * @return					JSON列表字符串
	 * @throws Exception
	 */
	public static void printListData(List<?> lst) throws Exception{
		if (CommonUtil.isEmpty(lst))
		{
			System.out.println("该List没有数据");
			return;
		}
		
		Object o = lst.get(0);
		if (o instanceof HashMap)
			printListMapData((List<HashMap>)lst);
		else
			printListObject(lst);
	}

	/**
	 * 取JSON字符串列表
	 * @param lst				数据列表
	 * @param fieldNames		需要提取的字段名，用英文逗号分隔
	 * @return					JSON列表字符串
	 * @throws Exception
	 */
	public static void printListMapData(List<HashMap> lst) throws Exception{
		if (CommonUtil.isEmpty(lst))
		{
			System.out.println("该List没有数据");
			return;
		}
		
		for (int i = 0; i < lst.size(); i++)
		{
			System.out.println(lst.get(i));
			if (i==100) break;
		}
	}


	/**
	 * 把普通对象列表转化为HashMap列表
	 * @param lst				数据列表
	 * @param fieldNames		需要提取的字段名，用英文逗号分隔
	 * @return					JSON列表字符串
	 * @throws Exception
	 */
	public static List<HashMap<String, Object>> changeObjectListToListMap(List<Object[]> lst, List<String> lstFieldName) throws Exception{
		if (CommonUtil.isEmpty(lst))  return new ArrayList<HashMap<String, Object>>();
		
		List<HashMap<String, Object>> lstResult = new ArrayList<HashMap<String, Object>>();
		for (int i = 0; i < lst.size(); i++)
		{
			HashMap<String, Object> map = new HashMap<String, Object>();
			if (lstFieldName.size() == 1)
			{
				Object obj = lst.get(i);
				map.put(lstFieldName.get(0), obj);
			} else
			{
				Object[] obj = lst.get(i);
				for (int j = 0; j< lstFieldName.size(); j++)
				{
					map.put(lstFieldName.get(j), obj[j]);
				}
			}
			lstResult.add(map);
		}
		return lstResult;
	}

	/**
	 * 把普通对象列表转化为HashMap里的对象转换为字符型
	 * @param lst				数据列表
	 * @param fieldNames		需要提取的字段名，用英文逗号分隔
	 * @return					JSON列表字符串
	 * @throws Exception
	 */
	public static List<HashMap<String, String>> changeListMapObjectToListMapString(List<HashMap<String, Object>> lst) throws Exception{
		if (CommonUtil.isEmpty(lst)) return new ArrayList<HashMap<String, String>>();
		
		List<HashMap<String, String>> lstResult = new ArrayList<HashMap<String, String>>();
		for (int i = 0; i < lst.size(); i++)
		{
			HashMap<String, String> mapString = new HashMap<String, String>();
			HashMap<String, Object> mapObject = lst.get(i);
			
			for (Iterator<String> iter = mapObject.keySet().iterator(); iter.hasNext();) {
			    String key = iter.next();
			    Object val = mapObject.get(key);
			    mapString.put(key, StringUtil.objectToString(val));
			} 			
			
			lstResult.add(mapString);
		}
		return lstResult;
	}

	/**
	 * 把普通对象列表转化为HashMap列表
	 * @param lst				数据列表
	 * @param fieldNames		需要提取的字段名，用英文逗号分隔
	 * @return					JSON列表字符串
	 * @throws Exception
	 */
	public static HashMap<String, Object> changeObjectListToMapMap(List<Object[]> lst, List<String> lstFieldName) throws Exception{
		if (CommonUtil.isEmpty(lst)) return new HashMap<String, Object>();
		
		HashMap<String, Object> mapResult = new HashMap<String, Object>();
		for (int i = 0; i < lst.size(); i++)
		{
			HashMap<String, Object> map = new HashMap<String, Object>();
			if (lstFieldName.size() == 1)
			{
				Object obj = lst.get(i);
				map.put(lstFieldName.get(0), obj);
				mapResult.put(StringUtil.objectToString(obj), map);
			} else
			{
				Object[] obj = lst.get(i);
				for (int j = 0; j< lstFieldName.size(); j++)
				{
					map.put(lstFieldName.get(j), obj[j]);
				}
				mapResult.put(StringUtil.objectToString(obj[0]), map);
			}
		}
		return mapResult;
	}

	/**
	 * 把普通对象列表转化为HashMap列表
	 * @param lst				数据列表
	 * @param fieldNames		需要提取的字段名，用英文逗号分隔
	 * @return					JSON列表字符串
	 * @throws Exception
	 */
	public static HashMap<String, Object> changeObjectListToHashMap(List<Object[]> lst, List<String> lstFieldName) throws Exception{
		if (CommonUtil.isEmpty(lst)) return new HashMap<String, Object>();

		HashMap<String, Object> mapResult = new HashMap<String, Object>();
		
		if (lstFieldName.size() == 1)
		{
			Object obj = lst.get(0);
			mapResult.put(lstFieldName.get(0), obj);
		} else
		{
			Object[] obj = lst.get(0);
			for (int j = 0; j< lstFieldName.size(); j++)
			{
				mapResult.put(lstFieldName.get(j), obj[j]);
			}
		}
		return mapResult;
	}


	/**
	 * 取JSON字符串的行
	 * @param obj				实体Bean
	 * @param fieldNames		需要提取的字段名，用英文逗号分隔
	 * @return					JSON行字符串
	 * @throws Exception
	 */
	public static String changeEntityToJson(Object obj) throws Exception{
		if (obj == null) return "";
		return "[" + getEntityToJson(obj) + "]";
	}

	/**
	 * 取JSON字符串的行
	 * @param obj				实体Bean
	 * @param fieldNames		需要提取的字段名，用英文逗号分隔
	 * @return					JSON行字符串
	 * @throws Exception
	 */
	public static String changeEntityToJson(Object obj, String fieldNames) throws Exception{
		if (obj == null) return "";
		return "[" + getEntityToJson(obj, fieldNames) + "]";
	}
	
	/**
	 * 取JSON字符串的行
	 * @param obj				实体Bean
	 * @param fieldNames		需要提取的字段名，用英文逗号分隔
	 * @return					JSON行字符串
	 * @throws Exception
	 */
	public static String getEntityToJson(Object obj) throws Exception{

		if (obj == null) return "";
        String result = "{";
	    Field[] field = obj.getClass().getDeclaredFields();
	
	    for(int i = 0; i < field.length; i++){    
	        Field f = field[i];
	        f.setAccessible(true);
	        if (f.getName().equals("serialVersionUID")) continue;
	        if (f.getName().equals("pcInheritedFieldCount")) break;

	    	if (!result.equals("{")) result += ",";
	    	result += getJsonFieldString(obj, f);
	    }
        return result + "}";
	}
	
	/**
	 * 取JSON字符串的行
	 * @param obj				实体Bean
	 * @param fieldNames		需要提取的字段名，用英文逗号分隔
	 * @return					JSON行字符串
	 * @throws Exception
	 */
	public static String getEntityToJson(Object obj, String fieldNames) throws Exception{

		if (obj == null) return "";
		if (fieldNames == null) return "";
		if (fieldNames.trim() == "") return getEntityToJson(obj);
		
        String result = "{";
		String[] args = fieldNames.split(",");
		for (int i = 0 ; i < args.length ; i++)
		{
	        Field f = obj.getClass().getDeclaredField(args[i].trim()); 
            f.setAccessible(true);

	    	if (!result.equals("{")) result += ",";
        	result += getJsonFieldString(obj, f);
		}
        return result + "}";
	}
	
	/**
	 * 返回Json格式的字符串
	 * @param obj		实体Bean
	 * @param field		字段
	 * @return			字段名称和值
	 * @throws Exception
	 */
	public static String getJsonFieldString(Object obj, Field field) throws Exception{
        //String result = "";
        
    	//if (field.getGenericType().toString().equals("class java.lang.Date") || field.getGenericType().toString().equals("class java.lang.String"))
    	//	result = field.getName() + ":\"" + getFieldValue(obj, field) + "\"";
    	//else
    	//	result = field.getName() + ":" + getFieldValue(obj, field);
    	
        //return result;
		return "\"" + field.getName() + "\":\"" + getFieldValue(field.get(obj), field.getGenericType().toString()) + "\"";
	}
	
	/**
	 * 按对象、对象类型取该对象的值
	 * @param obj				对象
	 * @param objectType		对象类型
	 * @return					字段的值
	 * @throws Exception
	 */
	public static String getFieldValue(Object obj, String objectType) throws Exception{
        String fieldValue = "";
        
    	if ((objectType != null) && (objectType.equals("class java.util.Date")))
    		fieldValue = StringUtil.dateToString((Date)obj);
    	else
    		fieldValue = StringUtil.objectToString(obj);
    	
        return fieldValue;
	}
}

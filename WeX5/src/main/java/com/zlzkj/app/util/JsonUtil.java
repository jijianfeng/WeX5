package com.zlzkj.app.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

public class JsonUtil {

	/**
	 * 把json字符串转换成一个HashMap<String, String>
	 * @param json 	json字符串
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	public static HashMap<String, String> jsonToHashMap(String json) throws Exception {

		if (StringUtil.isEmpty(json)) return new HashMap<String, String>();
		
		ObjectMapper mapper = new ObjectMapper();
		
		return (HashMap<String, String>)mapper.readValue(json, new TypeReference<HashMap<String, String>>(){});
	}

	/**
	 * 把json字符串转换成一个HashMap<String, Object>
	 * @param json 	json字符串
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	public static HashMap<String, Object> jsonToHashMapObject(String json) throws Exception {

		if (StringUtil.isEmpty(json)) return new HashMap<String, Object>();
		
		ObjectMapper mapper = new ObjectMapper();
		
		return (HashMap<String, Object>)mapper.readValue(json, new TypeReference<HashMap<String, Object>>(){});
	}

	/**
	 * 把实体转换成json字符串
	 * @param map 	HashMap
	 * @return
	 * @throws Exception 
	 */
	public static String objectToJson(Object obj) throws Exception {

		if (obj==null) return "";
		
		ObjectMapper mapper = new ObjectMapper();
		
		return mapper.writeValueAsString(obj);
	}

	
}

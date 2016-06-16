package com.zlzkj.app.util;

import java.io.Writer;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.Clob;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import com.zlzkj.core.sql.SQLBuilder;

/**
 * 字符串工具类 lee
 * 
 * @author Administrator
 * 
 */
public class StringUtil {

	public static void main(String[] args) {
		/*
		 * Date sdate = stringToDate("20110101"); Date edate =
		 * stringToDate("20111231");
		 * 
		 * for (Date d = sdate; d.getTime() <= edate.getTime(); d = addDate(d,
		 * 1)) { String dd = dateToStringyyMMdd(d); System.out.println(dd +
		 * "，财务月=" + getFinanceMonthByDate(dd)); }
		 */

		/*
		 * String ss = "000000488267"; System.out.println(ss + "+1=" +
		 * addStringValue(ss, 1)); System.out.println(ss + "+2=" +
		 * addStringValue(ss, 2)); System.out.println(ss + "+3=" +
		 * addStringValue(ss, 3));
		 */

		// String olds = "1,2,4";
		// String news = "1,4,5";
		// System.out.println("olds=" + olds + " ,news=" + news + " ,remove=" +
		// getRemoveString(olds, news));

		/*
		 * System.out.println(deleteSubString("1,2,3", "1"));
		 * System.out.println(deleteSubString("1,2,3", "2"));
		 * System.out.println(deleteSubString("1,2,3", "3"));
		 * System.out.println(deleteSubString("1,", "1"));
		 */

		// System.out.println(addStringSection("00123,00124", "00124",
		// "00125"));
		// System.out.println(addStringSection("", "00127", "00131"));
		//
		// System.out.println(delStringSection("00123,00124,00125,00126",
		// "00124", "00125"));
		// System.out.println(delStringSection("", "00127", "00131"));

		// System.out.println("aa:" + MD5String.getMD5Str("liuyukuan20130104"));
		System.out.println("aa:" + MD5String.getMD5Str("liuyk80"));
	}

	/**
	 * Object类型转化为String类型，默认为""
	 * 
	 * @return 返回String类型
	 */
	public static String objectToString(Object obj) {
		return objectToString(obj, "");
	}

	/**
	 * Object类型转化为String类型
	 * 
	 * @return 返回String类型
	 */
	public static String objectToString(Object obj, String defValue) {
		return (obj == null) ? defValue : obj.toString();
	}

	/**
	 * Object类型转化为String类型，默认为""
	 * 
	 * @return 返回String类型
	 */
	public static double objectTodouble(Object obj) {
		return stringToDouble(objectToString(obj)).doubleValue();
	}

	/**
	 * Object类型转化为String类型，默认为""
	 * 
	 * @return 返回String类型
	 */
	public static Integer objectToInteger(Object obj) {
		return stringToInteger(objectToString(obj));
	}

	/**
	 * Object类型转化为String类型，默认为""
	 * 
	 * @return 返回String类型
	 */
	public static Integer objectToInteger(Object obj, int defValue) {
		return stringToInteger(objectToString(obj), new Integer(defValue));
	}

	/**
	 * String类型转化为Long类型，默认为null
	 * 
	 * @return 返回Long类型
	 */
	public static long stringTolong(String value) {
		return stringToLong(value, new Long(0)).longValue();
	}

	/**
	 * String类型转化为Long类型，默认为null
	 * 
	 * @return 返回Long类型
	 */
	public static Long stringToLong(String value) {
		return stringToLong(value, null);
	}

	public static String addStringValue(String value, int add) {
		if (value == null || value.equals("")) {
			return "" + add;
		}
		int olength = value.length();
		String newValue = "" + (stringTolong(value) + add);

		if (newValue.length() < olength)
			return "0000000000000000000000".substring(0,
					olength - newValue.length())
					+ newValue;
		else
			return newValue;
	}

	/**
	 * String类型转化为Long类型
	 * 
	 * @return 返回Long类型
	 */
	public static Long stringToLong(String value, Long defValue) {
		if (value == null || value.equals("")) {
			return defValue;
		}
		try {
			return new Long(value);
		} catch (Exception e) {
			return defValue;
		}
	}

	/**
	 * String类型转化为Double类型，默认为null
	 * 
	 * @return 返回Double类型
	 */
	public static Double stringToDouble(String value) {
		return stringToDouble(value, null);
	}

	/**
	 * String类型转化为Double类型，默认为null
	 * 
	 * @return 返回Double类型
	 */
	public static double stringTodouble(String value) {
		return stringToDouble(value, new Double(0)).doubleValue();
	}

	/**
	 * String类型转化为Double类型
	 * 
	 * @return 返回Double类型
	 */
	public static Double stringToDouble(String value, Double defValue) {
		if (value == null || value.equals("")) {
			return defValue;
		}
		try {
			return new Double(value);
		} catch (Exception e) {
			return defValue;
		}
	}

	/**
	 * String类型转化为BigDecimal类型，默认为null
	 * 
	 * @return 返回BigDecimal类型
	 */
	public static BigDecimal stringToBigDecimal(String value) {
		return stringToBigDecimal(value, new BigDecimal(0));
	}

	/**
	 * String类型转化为BigDecimal类型
	 * 
	 * @return 返回BigDecimal类型
	 */
	public static BigDecimal stringToBigDecimal(String value,
			BigDecimal defValue) {
		if (value == null || value.equals("")) {
			return defValue;
		}
		try {
			return new BigDecimal(value);
		} catch (Exception e) {
			return defValue;
		}
	}

	/**
	 * String类型转化为Float类型，默认为null
	 * 
	 * @return 返回Float类型
	 */
	public static Float stringToFloat(String value) {
		return stringToFloat(value, null);
	}

	/**
	 * String类型转化为Float类型
	 * 
	 * @return 返回Float类型
	 */
	public static Float stringToFloat(String value, Float defValue) {
		if (value == null || value.equals("")) {
			return defValue;
		}
		try {
			return new Float(value);
		} catch (Exception e) {
			return defValue;
		}
	}

	/**
	 * String类型转化为Byte类型，默认为null
	 * 
	 * @return 返回Byte类型
	 */
	public static Byte stringToByte(String value) {
		return stringToByte(value, null);
	}

	/**
	 * String类型转化为Byte类型
	 * 
	 * @return 返回Byte类型
	 */
	public static Byte stringToByte(String value, Byte defValue) {
		if (value == null || value.equals("")) {
			return defValue;
		}
		try {
			return new Byte(value);
		} catch (Exception e) {
			return defValue;
		}
	}

	/**
	 * String类型转化为Short类型，默认为null
	 * 
	 * @return 返回Short类型
	 */
	public static Short stringToShort(String value) {
		return stringToShort(value, null);
	}

	/**
	 * String类型转化为Short类型
	 * 
	 * @return 返回Short类型
	 */
	public static Short stringToShort(String value, Short defValue) {
		if (value == null || value.equals("")) {
			return defValue;
		}
		try {
			return new Short(value);
		} catch (Exception e) {
			return defValue;
		}
	}

	/**
	 * String类型转化为Integer类型，默认为null
	 * 
	 * @return 返回Integer类型
	 */
	public static Integer stringToInteger(String value) {
		return stringToInteger(value, null);
	}

	/**
	 * String类型转化为Integer类型
	 * 
	 * @return 返回Integer类型
	 */
	public static Integer stringToInteger(String value, Integer defValue) {
		if (value == null || value.equals("")) {
			return defValue;
		}
		try {
			return new Integer(value);
		} catch (Exception e) {
			return defValue;
		}
	}

	/**
	 * 把字符串转化为日期类型
	 * 
	 * @return 返回日期类型
	 * @throws ParseException
	 */
	public static Timestamp stringToDate(String date) {
		if (date == null || date.equals("")) {
			return null;
		}
		try {
			String formatStr = "";
			if (date.length() == 8)
				formatStr = "yyyyMMdd";
			else if (date.length() == 14)
				formatStr = "yyyyMMddHHmmss";
			else if (date.length() == 17)
				formatStr = "yyyyMMddHHmmssSSS";
			else if (date.length() == 19)
				formatStr = "yyyy-MM-dd HH:mm:ss";
			else if (date.length() == 23)
				formatStr = "yyyy-MM-dd HH:mm:ss.SSS";
			else
				formatStr = "yyyyMMddHHmmss";

			// System.out.println("formatStr=" + formatStr + ",date.length()=" +
			// date.length());
			SimpleDateFormat dateFormate = new SimpleDateFormat(formatStr);
			// System.out.println("dateFormate=" + dateFormate);
			Date d = dateFormate.parse(date);
			// System.out.println("dd=" + d);
			return new Timestamp(d.getTime());
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 把字符串转化为日期类型
	 * 
	 * @return 返回日期类型
	 * @throws ParseException
	 */
	public static Timestamp objectToDate(Object date) {
		if (date == null || date.equals("")) {
			return null;
		}
		try {
			String formatStr = "";
			String date1 = StringUtil.objectToString(date);
			if (date1.length() == 8)
				formatStr = "yyyyMMdd";
			else if (date1.length() == 14)
				formatStr = "yyyyMMddHHmmss";
			else if (date1.length() == 17)
				formatStr = "yyyyMMddHHmmssSSS";
			else if (date1.length() == 19)
				formatStr = "yyyy-MM-dd HH:mm:ss";
			else if (date1.length() == 23)
				formatStr = "yyyy-MM-dd HH:mm:ss.SSS";
			else
				formatStr = "yyyyMMddHHmmss";

			// System.out.println("formatStr=" + formatStr + ",date.length()=" +
			// date.length());
			SimpleDateFormat dateFormate = new SimpleDateFormat(formatStr);
			// System.out.println("dateFormate=" + dateFormate);
			Date d = dateFormate.parse(date1);
			// System.out.println("dd=" + d);
			return new Timestamp(d.getTime());
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 把字符串转化为日期类型
	 * 
	 * @return 返回日期类型
	 * @throws ParseException
	 */
	public static Timestamp stringToTime(String date) {
		if (isEmpty(date))
			return null;
		try {
			SimpleDateFormat dateFormate = null;
			if (date.length() == 8)
				dateFormate = new SimpleDateFormat("yyyyMMdd");
			else if (date.length() == 14)
				dateFormate = new SimpleDateFormat("yyyyMMddHHmmss");
			else if (date.length() == 17)
				dateFormate = new SimpleDateFormat("yyyyMMddHHmmssSSS");
			else if (date.length() == 19)
				dateFormate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			else if (date.length() == 23)
				dateFormate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
			else
				dateFormate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date d = dateFormate.parse(date);
			return new Timestamp(d.getTime());
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 把字符串转化为日期数字类型
	 * 
	 * @return
	 * @throws ParseException
	 */
	public static Long stringToLTime(String date) {
		if (isEmpty(date))
			return null;
		String sDate = date
				+ "00000000000000000".substring(0, (17 - date.length()));

		return stringToLong(sDate);
	}

	/**
	 * 把字符串转化为日期数字类型
	 * 
	 * @return
	 * @throws ParseException
	 */
	public static Long stringToLStartTime(String date) {
		if (isEmpty(date))
			return null;
		String sDate = date + "000000000";

		return stringToLong(sDate);
	}

	/**
	 * 把字符串转化为日期数字类型
	 * 
	 * @return
	 * @throws ParseException
	 */
	public static Long stringToNLTime(String date) {
		if (isEmpty(date))
			return null;
		String sDate = date + "000";

		return stringToLong(sDate);
	}

	/**
	 * 把字符串转化为日期数字类型
	 * 
	 * @return
	 * @throws ParseException
	 */
	public static Long stringToLEndTime(String date) {
		if (isEmpty(date))
			return null;
		String sDate = date + "235959999";

		return stringToLong(sDate);
	}

	/**
	 * 取日期数字型的当前时间
	 * 
	 * @return
	 * @throws ParseException
	 */
	public static Long getNowLTime() {
		return stringToLong(dateToString(new Date(), "yyyyMMddHHmmssSSS"));
	}

	/**
	 * 把字符串转为日期且为当天日期类型
	 * 
	 * @param date
	 * @return
	 */
	public static Timestamp stringToEndTime(String date) {
		if (isEmpty(date))
			return null;
		else if (date.length() == 8)
			return stringToTime(date + "235959");
		else if (date.length() == 10)
			return stringToTime(date + " 23:59:59");
		else
			return stringToTime(date.substring(0, 8) + "235959");
	}

	/**
	 * 把字符串转化为日期类型
	 * 
	 * @return 返回日期类型
	 * @throws ParseException
	 */
	public static Timestamp getNowTime() {
		return new Timestamp((new Date()).getTime());
	}

	/**
	 * 取当前时间
	 * 
	 * @return 返回字符串类型
	 * @throws ParseException
	 */
	public static String getCurrentTime() {
		return dateToString(new Date());
	}

	/**
	 * 取当前时间
	 * 
	 * @return 返回字符串类型
	 * @throws ParseException
	 */
	public static String getChineseCurrentTime() {
		String sDate = getCurrentTime("yyyy-MM-dd");
		String[] aDate = sDate.split("-");
		return aDate[0] + "年" + aDate[1] + "月" + aDate[2] + "日";
	}

	/**
	 * 取当前时间
	 * 
	 * @return 返回字符串类型
	 * @throws ParseException
	 */
	public static String getChineseTimeByLong(Long date) {
		String sDate = "" + date;
		return sDate.substring(0, 4) + "年" + sDate.substring(4, 6) + "月"
				+ sDate.substring(6, 8) + "日";
	}

	/**
	 * 取当前时间
	 * 
	 * @return 返回字符串类型
	 * @throws ParseException
	 */
	public static String getCurrentTime(String dateFormate) {
		return dateToString(new Date(), dateFormate);
	}

	/**
	 * 把字符串转化为日期类型
	 * 
	 * @return 返回日期类型
	 * @throws ParseException
	 */
	public static String dateToString(Date date) {
		return dateToString(date, "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 把字符串转化为日期类型
	 * 
	 * @return 返回日期类型
	 * @throws ParseException
	 */
	public static String formatStringDateToyyyyMMdd(String date) {
		String sResult = date.replace("-", "");
		sResult = sResult.replace(":", "");
		sResult = sResult.replace(" ", "");
		return sResult.substring(0, 14);
	}

	/**
	 * 把字符串转化为日期类型8
	 * 
	 * @return 返回日期类型8
	 * @throws ParseException
	 */
	public static String formatStringDateToyyyyMMdd8(String date) {
		String sResult = date.replace("-", "");
		sResult = sResult.replace(":", "");
		sResult = sResult.replace(" ", "");
		return sResult.substring(0, 8);
	}

	/**
	 * 把字符串转化为日期类型
	 * 
	 * @return 返回日期类型
	 * @throws ParseException
	 */
	public static String dateToStringyyMMdd(Date date) {
		return dateToString(date, "yyyyMMdd");
	}

	/**
	 * 把字符串转化为日期类型
	 * 
	 * @return 返回日期类型
	 * @throws ParseException
	 */
	public static String stringyyyyMMddFormat(String date) {
		if (isEmpty(date) || date.length() < 8)
			return date;

		return date.substring(0, 4) + "-" + date.substring(4, 6) + "-"
				+ date.substring(6, 8);
	}

	/**
	 * 把字符串转化为日期类型
	 * 
	 * @return 返回日期类型
	 * @throws ParseException
	 */
	public static String dateToString(Date date, String format) {
		if (date == null)
			return null;
		try {
			SimpleDateFormat dateFormate = new SimpleDateFormat(format);
			return dateFormate.format(date);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 格式化字符串,如："1,2,3"格式化为"'1','2','3'"
	 * 
	 * @param str
	 * @return 返回加上引号的字符串
	 */
	public static String formatCommaString(String str) {

		if (str == null || str.trim().equals(""))
			return "";

		String[] aTmpStr = str.split(",");
		String sFormatStr = "";
		for (int i = 0; i < (aTmpStr.length - 1); i++) {
			sFormatStr += "'" + aTmpStr[i] + "',";
		}
		sFormatStr += "'" + aTmpStr[aTmpStr.length - 1] + "'";
		return sFormatStr;
	}

	/**
	 * 日期相减
	 * 
	 * @return 返回相减后的日期
	 */
	public static int diffDate(Date date, Date date1) {
		return (int) ((date.getTime() - date1.getTime()) / (24 * 3600 * 1000));
	}

	/**
	 * 日期相加
	 * 
	 * @return 返回相加后的日期
	 */
	public static Date addDate(Date date, int day) {
		Date dd = new Date();
		dd.setTime(date.getTime() + ((long) day) * 24 * 3600 * 1000);
		return dd;
	}

	/**
	 * 日期相减几分钟
	 * 
	 * @return 返回相减后的日期
	 */
	public static Date minusDateMinute(Date date, int minute) {
		Date dd = new Date();
		dd.setTime(date.getTime() + ((long) minute) * 60 * 1000);
		return dd;
	}

	/**
	 * 日期间隔天数
	 * 
	 * @return 返回相差的天数
	 * @throws Exception
	 */
	public static int diffDate(String date1, String date2) throws Exception {
		return diffDate(stringToDate(date1), stringToDate(date2));
	}

	/**
	 * 格式化日期：20000101 -- > 2000-01-01
	 * 
	 * @return 按-分隔的日期字符串
	 */
	public static String stringDateToDashDateString(String str) {
		if (str == null || str.equalsIgnoreCase(""))
			return "";
		str = padRight(str, 8, '0');
		String sYear = str.substring(0, 4);
		String sMonth = str.substring(4, 6);
		String sDay = str.substring(6, 8);
		String sNewDate = sYear + "-" + sMonth + "-" + sDay;
		return sNewDate;
	}

	/**
	 * 
	 * @param 填充的字符串
	 * @param 字符串要返回的长度
	 * @param 字符串在长度不够时
	 *            ，左边填充的字符
	 * 
	 * @return 长度为length的字符串
	 */
	public static String padLeft(String s, int len, char c) {

		if (s == null) {
			s = "";
		}
		s = s.trim();
		if (s.length() >= len) {
			return s;
		}

		StringBuffer d = new StringBuffer(len);
		int fill = len - s.length();

		while (fill-- > 0) {
			d.append(c);
		}

		d.append(s);
		return d.toString();
	}

	/**
	 * 
	 * @param 填充的字符串
	 * @param 字符串要返回的长度
	 * @param 字符串在长度不够时
	 *            ，右边填充的字符
	 * 
	 * @return 长度为length的字符串
	 */
	public static String padRight(String s, int len, char c) {

		if (s == null) {
			s = "";
		}
		s = s.trim();
		if (s.length() >= len) {
			return s;
		}

		StringBuffer d = new StringBuffer(len);
		int fill = len - s.length();

		while (fill-- > 0) {
			d.append(c);
		}

		return s.concat(d.toString());
	}

	/**
	 * 字符串进行重复count次
	 * 
	 * @param 待重复字符串
	 * @param 重复次数
	 * @return
	 */
	public static String getRepeatString(String s, int count) {
		StringBuffer sb = new StringBuffer();
		while (count-- > 0)
			sb.append(s);
		return sb.toString();
	}

	/**
	 * 检查日期是否为空
	 */
	public static boolean isEmpty(Date dd) {
		return dd == null;
	}

	/**
	 * 检查对象是否为空
	 */
	public static boolean isEmpty(Object obj) {
		String ss = objectToString(obj);
		return ss == null || ss.trim().equals("");
	}

	/**
	 * 检查对象是否为空
	 */
	public static boolean isEmpty(HashMap map) {
		return map == null || map.size() == 0;
	}

	/**
	 * 检查字符串是否为空
	 */
	public static boolean isEmpty(String ss) {
		return ss == null || ss.trim().equals("");
	}

	/**
	 * 检查长整型是否为空
	 */
	public static boolean isEmpty(Long ll) {
		return ll == null;
	}

	/**
	 * 检查整型是否为空
	 */
	public static boolean isEmpty(Integer ii) {
		return ii == null;
	}

	/**
	 * 检查手机号段属于哪个公司
	 * 
	 * @param cellphoneNumber
	 *            手机号
	 * @return 1-移动，2-电信，3-联通
	 */
	public static int cellphoneNumberBelongTo(String cellphoneNumber) {
		String cellYD = "134.135.136.137.138.139.150.151.152.157.158.159.187.188"; // 移动
		String cellDX = "133.153.180.189"; // 电信
		String cellLT = "130.131.132.155.156.185.186"; // 联通
		String cellHead = cellphoneNumber.substring(0, 3);

		if (cellYD.contains(cellHead))
			return 1;
		else if (cellDX.contains(cellHead))
			return 2;
		else if (cellLT.contains(cellHead))
			return 3;
		else
			return 0;
	}

	public static String getAllMapKeyName2(HashMap<String, Object> map) {
		Object[] sKey = map.keySet().toArray();
		String result = "";
		for (int i = 0; i < sKey.length; i++) {
			if (result.equals(""))
				result += sKey[i].toString();
			else
				result += "," + sKey[i].toString();
		}
		return result;
	}

	/**
	 * 将map中key修改为驼峰命名方式并返回新的map
	 * 
	 * @param map
	 * @return
	 */
	public static HashMap<String, Object> getAllMapKeyName2AndUpdateMap(
			HashMap<String, Object> map) {
		Object[] sKey = map.keySet().toArray();
		for (int i = 0; i < sKey.length; i++) {
			if (sKey[i].toString().contains("_")) {
				map.put(camelName(sKey[i].toString()),
						map.get(sKey[i].toString()));// 添加驼峰key
				map.remove(sKey[i]);// 移除旧值
			}
		}
		return map;
	}

	public static String getAllMapKeyName(HashMap<String, String> map) {
		Object[] sKey = map.keySet().toArray();
		String result = "";
		for (int i = 0; i < sKey.length; i++) {
			if (result.equals(""))
				result += sKey[i].toString();
			else
				result += "," + sKey[i].toString();
		}
		return result;
	}

	/**
	 * 拼接字符串
	 * 
	 * @param target
	 *            目标字符串
	 * @param join
	 *            需要拼接的字符串
	 * @return
	 */
	public static String getSplitJointStr(String target, String join,
			String... connect) {
		String sResult = "";
		String cnn = ",";
		if (connect.length > 0)
			cnn = connect[0];

		if (isEmpty(target) && (!isEmpty(join)))
			sResult = join;
		else if ((!isEmpty(target)) && (isEmpty(join)))
			sResult = target;
		else if ((!isEmpty(target)) && (!isEmpty(join)))
			sResult = target + cnn + join;

		return sResult;
	}

	public static String getSubStringByIndex(String source, int index,
			String... separator) {
		if (isEmpty(source))
			return "";
		if (index < 1)
			return "";

		String sep = ",";
		if (separator.length > 0)
			sep = separator[0];

		String[] aSource = source.split(sep);
		if (index > aSource.length)
			return "";
		return aSource[index - 1];
	}

	/**
	 * 大写段转为字符串
	 * 
	 * @param clob
	 * @return
	 * @throws Exception
	 */
	public static String oracleClob2Str(Clob clob) throws Exception {
		return (clob != null ? clob.getSubString(1, (int) clob.length()) : "");
	}

	public static Clob oracleStr2Clob(String str, Clob lob) throws Exception {
		Method methodToInvoke = lob.getClass().getMethod(
				"getCharacterOutputStream", (Class[]) null);
		Writer writer = (Writer) methodToInvoke.invoke(lob, (Object[]) null);
		writer.write(str);
		writer.close();
		return lob;
	}

	public static double getDoubleRound(double dub) {
		return Math.round(dub * 100) / 100.0;
	}

	public static String trimDouble0(double dub) {
		return formatDouble(dub, "#0.##");
	}

	public static String formatDouble(double dub, String fromat) {
		DecimalFormat df = new DecimalFormat(fromat);
		return df.format(dub);
	}

	/**
	 * 把数字格式化成字符串
	 * 
	 * @param i
	 * @param format
	 * @return
	 */
	public static String formatNum(int i, String format) {
		if (isEmpty(format)) {
			return "" + i;
		}
		try {
			DecimalFormat df = new DecimalFormat();
			df.applyPattern(format);// 将格式应用于格式化器
			return df.format(i);
		} catch (Exception e) {
			return "" + i;
		}
	}

	public static boolean isNumeric(String str) {
		for (int i = 0; i < str.length(); i++) {
			// System.out.println(str.charAt(i));
			if (!Character.isDigit(str.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	public static String getURLDecoder(String type, String value)
			throws Exception {
		try {
			value = value.replaceAll(type, "%");
			value = URLDecoder.decode(value, "utf-8");
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return value;
	}

	public static String getURLDecoder(String value) throws Exception {
		try {
			value = URLDecoder.decode(value, "utf-8");
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return value;
	}

	public static String getURLEecoder(String value) throws Exception {
		try {
			value = URLEncoder.encode(value, "utf-8");
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return value;
	}

	/**
	 * 字符串分割成整型数组
	 * 
	 * @param str
	 * @return
	 * @throws Exception
	 */
	public static Long[] getLongArrayFromStr(String str) throws Exception {

		if (StringUtil.isEmpty(str))
			return (new Long[0]);

		String[] aStr = str.split(",");
		Long[] iIDs = new Long[aStr.length];
		for (int i = 0; i < aStr.length; i++) {
			iIDs[i] = StringUtil.stringToLong(aStr[i]);
		}
		return iIDs;
	}

	/**
	 * 字符串分割成整型数组
	 * 
	 * @param str
	 * @return
	 * @throws Exception
	 */
	public static String[] getStringArrayFromStr(String str) throws Exception {

		if (StringUtil.isEmpty(str))
			return (new String[0]);

		return str.split(",");
	}

	public static int getSkipRows(String currentPage, String rowsPerPage) {
		if (isEmpty(currentPage) || isEmpty(rowsPerPage))
			return 0;

		int skipRows = (StringUtil.objectToInteger(currentPage) - 1)
				* StringUtil.objectToInteger(rowsPerPage);

		if (skipRows < 0)
			return 0;
		else
			return skipRows;
	}

	public static int getSkipRows(Object currentPage, Object rowsPerPage) {
		return getSkipRows(StringUtil.objectToString(currentPage),
				StringUtil.objectToString(rowsPerPage));
	}

	public static int getLimitRows(String rowsPerPage) {
		if (isEmpty(rowsPerPage))
			return 100;

		return StringUtil.objectToInteger(rowsPerPage);
	}

	public static int getLimitRows(Object rowsPerPage) {
		return getLimitRows(StringUtil.objectToString(rowsPerPage));
	}

	/**
	 * JQuery EasyUI设置分页和排序
	 * 
	 * @param map
	 */
	public static void setPageAndOrder(HashMap<String, Object> map) {
		int skipRows = StringUtil.getSkipRows(map.get("page"), map.get("rows"));// page和rows
																				// JQuery
																				// EasyUI的datagrid里用到的参数
		int limitRows = StringUtil.getLimitRows(map.get("rows"));

		map.put("skipRows", skipRows);
		map.put("limitRows", limitRows);

		String sort = StringUtil.objectToString(map.get("sort"), "id");// 排序字段
		String order = StringUtil.objectToString(map.get("order"), "asc");// 排序方式

		map.put("orderby", sort + " " + order);
	}

	/**
	 * 按日期字符串yyyyMMdd取其所在的财务月
	 * 
	 * @param sInDate
	 * @return
	 */
	public static String getFinanceMonthByDate(String sInDate) {
		String sDate = sInDate.substring(0, 8);
		int sYear = stringToInteger(sDate.substring(0, 4));
		int iMonth = stringToInteger(sDate.substring(4, 6));
		int iDay = stringToInteger(sDate.substring(6, 8));

		/*
		 * if ((iMonth == 1) && (iDay <= 25)) return sYear + "01"; else
		 * if(((iMonth == 11) && (iDay >= 26)) || (iMonth == 12)) return sYear +
		 * "12"; else if(iDay >= 26) return "" + (sYear * 100 + iMonth + 1);
		 * else return "" + (sYear * 100 + iMonth);
		 */

		if ((iMonth <= 11) && (iDay >= 26))
			return "" + (sYear * 100 + iMonth + 1);
		else
			return "" + (sYear * 100 + iMonth);
	}

	/**
	 * 
	 * @param ss
	 * @param sub
	 * @return
	 */
	public static String deleteSubString(String ss, String sub) {
		if (isEmpty(ss) || isEmpty(sub))
			return ss;

		String[] aa = ss.split(",");
		StringBuffer sb = new StringBuffer();

		for (String str : aa) {
			if (!str.equals(sub))
				sb.append(str + ",");
		}

		if (isEmpty(sb))
			return "";
		else
			return sb.substring(0, sb.length() - 1).toString();
	}

	/**
	 * 在一个字符串内加上一段号码
	 * 
	 * @param ss
	 *            原字符串
	 * @param startNo
	 *            开始号
	 * @param endNo
	 *            结束号
	 * @return
	 */
	public static String addStringSection(String ss, String startNo,
			String endNo) {
		if (isEmpty(startNo) || isEmpty(endNo))
			return ss;

		String result = ss;
		if (result == null)
			result = "";

		String tmp = startNo;
		if (!result.contains(tmp))
			result += "," + tmp;

		while (!tmp.equals(endNo)) {
			tmp = addStringValue(tmp, 1);
			if (!result.contains(tmp))
				result += "," + tmp;
		}

		if (result.substring(0, 1).equals(","))
			return result.substring(1, result.length());
		else
			return result;
	}

	/**
	 * 在一个字符串内加删除一段号码
	 * 
	 * @param ss
	 *            原字符串
	 * @param startNo
	 *            开始号
	 * @param endNo
	 *            结束号
	 * @return
	 */
	public static String delStringSection(String ss, String startNo,
			String endNo) {
		if (isEmpty(startNo) || isEmpty(endNo))
			return ss;

		String result = ss;
		if (result == null)
			result = "";

		String tmp = startNo;
		if (result.contains(tmp))
			result = deleteSubString(result, tmp);

		while (!tmp.equals(endNo)) {
			tmp = addStringValue(tmp, 1);
			if (result.contains(tmp))
				result = deleteSubString(result, tmp);
		}

		return result;
	}

	public static String getCompareType(String CompareSign) {
		if (isEmpty(CompareSign))
			return "$gte";

		if (CompareSign.equals(">"))
			return "$gt";
		else if (CompareSign.equals(">="))
			return "$gte";
		else if (CompareSign.equals("<="))
			return "$lte";
		else if (CompareSign.equals("<"))
			return "$lt";
		else
			return "$gte";
	}

	public static String replaceStr(String str) {
		String value = str.replaceAll("__r__n__", "\r\n")
				.replace("__r__", "\r").replace("__n__", "\n")
				.replace("__tab__", "\t").replace("“", "\"").replace("‘", "'")
				.replace("/", "\\").replace("_dq_", "“").replace("_sq_", "‘")
				.replace("；", ";");

		return value;
	}

	/**
	 * 将下划线大写方式命名的字符串转换为驼峰式。如果转换前的下划线大写方式命名的字符串为空，则返回空字符串。</br>
	 * 例如：HELLO_WORLD->HelloWorld
	 * 
	 * @param name
	 *            转换前的下划线大写方式命名的字符串
	 * @return 转换后的驼峰式命名的字符串
	 */
	public static String camelName(String name) {
		StringBuilder result = new StringBuilder();
		// 快速检查
		if (name == null || name.isEmpty()) {
			// 没必要转换
			return "";
		} else if (!name.contains("_")) {
			// 不含下划线，仅将首字母小写
			return name.substring(0, 1).toLowerCase() + name.substring(1);
		}
		// 用下划线将原始字符串分割
		String camels[] = name.split("_");
		for (String camel : camels) {
			// 跳过原始字符串中开头、结尾的下换线或双重下划线
			if (camel.isEmpty()) {
				continue;
			}
			// 处理真正的驼峰片段
			if (result.length() == 0) {
				// 第一个驼峰片段，全部字母都小写
				result.append(camel.toLowerCase());
			} else {
				// 其他的驼峰片段，首字母大写
				result.append(camel.substring(0, 1).toUpperCase());
				result.append(camel.substring(1).toLowerCase());
			}
		}
		return result.toString();
	}

	/**
	 * 将驼峰式命名的字符串转换为下划线大写方式。如果转换前的驼峰式命名的字符串为空，则返回空字符串。</br>
	 * 例如：HelloWorld->HELLO_WORLD
	 * 
	 * @param name
	 *            转换前的驼峰式命名的字符串
	 * @return 转换后下划线大写方式命名的字符串
	 */
	public static String underscoreName(String name) {
		StringBuilder result = new StringBuilder();
		if (name != null && name.length() > 0) {
			// 将第一个字符处理成大写
			result.append(name.substring(0, 1).toUpperCase());
			// 循环处理其余字符
			for (int i = 1; i < name.length(); i++) {
				String s = name.substring(i, i + 1);
				// 在大写字母前添加下划线
				if (s.equals(s.toUpperCase())
						&& !Character.isDigit(s.charAt(0))) {
					result.append("_");
				}
				// 其他字符直接转成大写
				result.append(s.toUpperCase());
			}
		}
		return result.toString();
	}

	/**
	 * 20131212235900 -> 2013-12-12 23:59:00
	 * 
	 * @param str
	 * @return
	 */
	public static String Integer2StringDate(String str) {
		if (str == null || str.equalsIgnoreCase(""))
			return "";
		str = padRight(str, 12, '0');
		String sYear = str.substring(0, 4);
		String sMonth = str.substring(4, 6);
		String sDay = str.substring(6, 8);
		String sHour = str.substring(8, 10);
		String sMin = str.substring(10, 12);
		String sSec = str.substring(12, 14);
		String sNewDate = sYear + "-" + sMonth + "-" + sDay + " " + sHour + ":"
				+ sMin + ":" + sSec;
		return sNewDate;
	}

	/**
	 * 20131212235900 <-- 2013-12-12 23:59:00
	 * 
	 * @param str
	 * @return
	 */
	public static String StringDate2Intgeter(String str) {
		if (str == null || str.equalsIgnoreCase(""))
			return "";
		str = str.replace(" ", "");
		str = str.replace(":", "");
		str = str.replace("-", "");
		return str;
	}
	/**
	 * 构造where条件
	 * 
	 * @param where
	 * @return
	 */
	public static String buildWhere(String... where) {
		StringBuilder sb = new StringBuilder();
		String str = "";
		for (String temp : where) {
			if (StringUtil.isEmpty(temp))
				;
			else {
				temp = temp + " AND ";
				sb.append(temp);
			}
		}
		if (sb.length() != 0) { // 说明有条件存在
			str = sb.substring(0, sb.length() - 4);// 去除最后一个AND
		}
		return str;
	}

	public static String buildOrWhere(String... where) {
		StringBuilder sb = new StringBuilder();
		String str = "";
		for (String temp : where) {
			if (StringUtil.isEmpty(temp))
				;
			else {
				temp = temp + " OR ";
				sb.append(temp);
			}
		}
		if (sb.length() != 0) { // 说明有条件存在
			str = sb.substring(0, sb.length() - 4);// 去除最后一个AND
		}
		return str;
	}

	/**
	 * 模糊查询条件
	 * 
	 * @param Filed
	 * @param str
	 * @return
	 */
	public static String fuzzyQuery(String Filed, String str) {
		String result = "";
		if (StringUtil.isEmpty(str)) {
			str = "";
		}
		result = " " + Filed + " like " + "'%" + str + "%'";
		return result;
	}

	/**
	 * list选择查询条件
	 * 
	 * @param Filed
	 * @param str
	 * @return
	 */
	public static String listQuery(String Filed, String str) {
		String result = "";
		if (isEmpty(str)) {
			return "";
		}
		result = " " + Filed + " = " + "'" + str + "'";
		return result;
	}

	/**
	 * list2String数组
	 * 
	 * @param list
	 * @return
	 */
	public static String[] StringList2Array(List<?> list) {
		int number = list.size();
		if (number == 0) {
			return null;
		}
		String str[] = new String[number];
		for (int i = 0; i < number; i++) {
			str[i] = list.get(i).toString();
		}
		return str;
	}
	
	/**
	 * 生成随机密码
	 * @param length
	 * @return
	*/
	 public static String getRandomString(int length) { 
	 StringBuffer buffer = new StringBuffer("0123456789abcdefghijklmnopqrstuvwxyz"); 
	 StringBuffer sb = new StringBuffer(); 
	 Random r = new Random(); 
	 int range = buffer.length(); 
	 for (int i = 0; i < length; i ++) { 
	sb.append(buffer.charAt(r.nextInt(range)));
	}
	 return sb.toString(); 
	}
}

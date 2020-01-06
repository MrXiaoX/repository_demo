package com.cln.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.apache.log4j.Logger;
import org.aspectj.lang.annotation.Aspect;

/**
 * 
 * @ClassName: DateUtil 
 * @Description: TODO 时间工具类
 * @author LingHao 
 * @date 2018年8月27日 上午11:58:03  
 * @Company 深圳市卡联科技股份有限公司
 */
@Aspect
public class DateUtil
{
	private static Logger logger = Logger.getLogger(DbConnUtil.class);
	public static final String FORMAT = "yyyy-MM-dd HH:mm:ss";

	/**
	 * 
	 * @Title: isValidDate 
	 * @author LingHao  
	 * @date 2018年8月27日 上午11:58:36
	 * @Description: TODO 验证日期数据是否符合规格
	 * @param 
	 * @return String
	 * @throws
	 */
	public static String isValidDate(String str)
	{
		boolean convertSuccess = true;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		try
		{
			format.setLenient(false);
			format.parse(str);
			str = str.substring(0, str.indexOf(" "));
			System.out.println("符合日期格式,转换后格式为：" + str);
		}
		catch (ParseException e)
		{
			logger.error("DateUtil的isValidDate方法:" + e.getMessage());
			System.out.println("不符合日期格式,STR为：" + str);
		}
		return str;
	}
	

    public static String transStringToDateStr(String dateString) {
    	StringBuffer stringBuffer = new StringBuffer(dateString);
    	stringBuffer.insert(4, "-").insert(7, "-").insert(10, " ").insert(13, ":").insert(16, ":");
    	return stringBuffer.toString();
    }
    
    public static String transStringToYearDateStr(String dateString) {
    	StringBuffer stringBuffer = new StringBuffer(dateString);
    	return stringBuffer.substring(0, 8);
    }
    
    public static String transStringToTimeStr(String dateString) {
    	StringBuffer stringBuffer = new StringBuffer(dateString);
    	return stringBuffer.substring(8, 14);
    }
    
    /**
     * 将16进制时间戳格式的字符串转换为  yyyyMMddHHmmss 格式。
     * 由于嵌入式程序内核问题，导致传入的时间比UTC时间多2 * n(n表示时区)个小时，不能按照常规的手段将UTC时间转换为本地时间，
     * 处理方法为，在传入的时间基础上，减去n个小时，即为当地时间
     * @param hexStr
     * 			待转换的16进制字符串
     * @return
     * 			String 转换后的字符串
     */
    public static String hexStringToDate(String hexStr)
    {
    	//将16进制字符串转换为时间戳，秒数
    	Long timestamp = Long.valueOf(hexStr, 16);
    	if(null == timestamp)
    	{
    		return null;
    	}
    	//获取当前时区，北京(+8:00)时区
    	Calendar cal = Calendar.getInstance();
    	TimeZone timeZone = cal.getTimeZone();
    	//减去对应的毫秒数
    	timestamp = (timestamp * 1000) - timeZone.getRawOffset();
    	//获取真实刷卡时间
    	return DateToString(new Date(timestamp), "yyyyMMddHHmmss");
    }
    
    /**
     * 功能: 将日期对象按照某种格式进行转换，返回转换后的字符串
     * 
     * @param date
     *            日期对象
     * @param pattern
     *            转换格式 例：yyyy-MM-dd
     */
    public static String DateToString(Date date, String pattern) {
        String strDateTime = null;
        SimpleDateFormat formater = new SimpleDateFormat(pattern);
        strDateTime = date == null ? null : formater.format(date);
        return strDateTime;
    }


	public static void main(String[] args)
	{
		//String转换为java.util.Date 
		 
//		String str = "2013-01-14 12:12:11"; 
//		 
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
//		 
//		Date date = null; //初始化date 
//		 
//		try { 
//		 
//		date = sdf.parse(str); //Mon Jan 14 00:00:00 CST 2013 
//		 
//		} catch (ParseException e) { 
//		 
//		e.printStackTrace(); 
//		 
//		} 
//		System.out.println(date);
////			SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
////			String ss = s.format(new Date().toString());
////			System.out.println(s.parse(ss));
//			System.out.println(new Date().toString());
//			String s = new Date().toString();
//			Date d = new Date(s);
//			System.out.println(d);
		
		System.out.println(transStringToTimeStr("20181020160516"));
	}
}

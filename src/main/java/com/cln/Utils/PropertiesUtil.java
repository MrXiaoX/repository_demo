package com.cln.Utils;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 * 
 * @ClassName: PropertiesUtil
 * @Description: TODO Properties初始化和读取
 * @author LingHao
 * @date 2018年8月27日 上午10:35:44
 * @Company 深圳市卡联科技股份有限公司
 */
public class PropertiesUtil
{
	private static Logger logger = Logger.getLogger(PropertiesUtil.class);
	public static Properties config = null;
    //初始化日志属性文件位置
    static
    {
    	initLog();
    }

    /**
     * 
     * @Title: init 
     * @author LingHao  
     * @date 2018年11月9日 下午1:43:46
     * @Description: TODO 初始化config.properties
     * @param 
     * @return String
     * @throws
     */
	public static void init() {
	        if (config == null)
	        {
                try
                {
                	config = new Properties();
                	String filePath = System.getProperty("user.dir").concat("/config/jdbc.properties");
                	System.out.println(filePath);
        			InputStream inputStream = new BufferedInputStream(new FileInputStream(filePath));
        			if (inputStream != null)
        			{
        				config.load(inputStream);
        				inputStream.close();
        			}
                }
                catch (IOException e)
                {
                	logger.error(e.getMessage(), e);
                }
            }
	}

	/**
	 * 
	 * @Title: printAllProperty 
	 * @author LingHao  
	 * @date 2018年8月27日 上午11:47:20
	 * @Description: TODO输出文件对应的key与value值
	 * @param 
	 * @return void
	 * @throws
	 */
	private static void printAllProperty(Properties props)
	{
		@SuppressWarnings("rawtypes")
		Enumeration en = props.propertyNames();
		while (en.hasMoreElements())
		{
			String key = (String) en.nextElement();
			String value = props.getProperty(key);
			System.out.println(key + " : " + value);
		}
	}

	/**
	 * 
	 * @Title: GetPropertiesMap 
	 * @author LingHao  
	 * @date 2018年8月27日 上午11:47:27
	 * @Description: TODO读取Properties的全部信息并放入map
	 * @param 
	 * @return Map<String,String>
	 * @throws
	 */
	public static Map<String, String> GetPropertiesMap(String filePath) throws IOException
	{
		Properties pps = new Properties();
		InputStream in = PropertiesUtil.class.getResourceAsStream(filePath);// 使用classPath读取文件
		pps.load(in);
		Enumeration en = pps.propertyNames();
		Map<String, String> propertiesMap = new HashMap<String, String>();
		while (en.hasMoreElements())
		{
			String strKey = (String) en.nextElement();
			String strValue = pps.getProperty(strKey);
			propertiesMap.put(strKey, strValue);
		}
		return propertiesMap;
	}

	public static void main(String[] args)
	{
		PropertiesUtil.init();
	}
	

    public static void initLog()
	{
    	PropertyConfigurator.configure(System.getProperty("user.dir") + "/config/log4j.properties"); 
	}
}

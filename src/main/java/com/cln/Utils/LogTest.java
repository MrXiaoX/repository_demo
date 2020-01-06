package com.cln.Utils;

import org.apache.log4j.Logger;

/**
 * 
 * @ClassName: LogTest
 * @Description: TODO logTest类测试类
 * @author LingHao
 * @date 2018年8月27日 上午10:13:33
 * @Company 深圳市卡联科技股份有限公司
 */
public class LogTest
{
	private static Logger logger = Logger.getLogger(LogTest.class);

	public static void main(String[] args)
	{
		// 记录debug级别的信息
		logger.debug("This is debug message.");
		// 记录info级别的信息
		logger.info("This is info message.");
		// 记录error级别的信息
		logger.error("This is error message.");
	}
}

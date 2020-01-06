package com.cln.Utils;

import java.sql.Connection;
import java.sql.DriverManager;

import org.apache.log4j.Logger;


/**
 * 
 * @ClassName: DbConnUtil 
 * @Description: TODO 数据库连接工具
 * @author LingHao
 * @date 2018年8月27日 上午11:52:12  
 * @Company 深圳市卡联科技股份有限公司
 */
public class DbConnUtil
{

	private static Logger logger = Logger.getLogger(DbConnUtil.class);
	private static String driverName = PropertiesUtil.config.getProperty("driver");
	private static String url = PropertiesUtil.config.getProperty("url");
	private static String username = PropertiesUtil.config.getProperty("username");
	private static String password = PropertiesUtil.config.getProperty("password");
	
	/**
	 * 
	 * @Title: createConnDist 
	 * @author LingHao  
	 * @date 2018年8月27日 上午11:53:05
	 * @Description: TODO 创建mysql数据源连接
	 * @param 
	 * @return Connection
	 * @throws
	 */
	public static Connection createMysqlConn()
	{
		Connection conn = null;
		try
		{
			String driverClassName = driverName;
			String dbUrl = url;
			String dbUser = username;
			String dbPwd = password;
			Class.forName(driverClassName).newInstance();
			conn = DriverManager.getConnection(dbUrl, dbUser, dbPwd);
		}
		catch (Exception e)
		{
			logger.error("DbConnUtil的createMysqlConn代码块:" + e.getMessage());
			e.printStackTrace();

		}

		return conn;
	}

	/**
	 * 
	 * @Title: close 
	 * @author LingHao  
	 * @date 2018年8月27日 上午11:55:07
	 * @Description: TODO 数据连接关闭
	 * @param 
	 * @return void
	 * @throws
	 */
	public static void close(Connection conn)
	{
		try
		{
			conn.close();
		}
		catch (Exception e)
		{
			logger.error("DbConnUtil的close代码块:" + e.getMessage());
			e.printStackTrace();

		}
	}

}

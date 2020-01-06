package com.cln.Utils;


import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ArrayHandler;
import org.apache.commons.dbutils.handlers.ArrayListHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.log4j.Logger;

/**
 * 
 * @ClassName: DbBuilder 
 * @Description: TODO 操作数据库类
 * @author LingHao
 * @date 2018年8月27日 下午3:34:54  
 * @Company 深圳市卡联科技股份有限公司
 */
public class DbBuilder {
	
	private static Logger logger = Logger.getLogger(DbConnUtil.class);
	//数据库连接对象
    public Connection connection = null ;
    
	public DbBuilder(Connection conn) {
		this.connection = conn;
	}

	public  QueryRunner getQueryRunner() {
		return new QueryRunner();
	}
	
	/**
	 * 
	 * @Title: getFirstRowMap 
	 * @author LingHao  
	 * @date 2018年8月27日 下午3:36:38
	 * @Description: TODO 根据传入的sql，查询记录，以Map形式返回第一行记录 注意：如果有多行记录，只会返回第XX，所以应用场景需要注意，可以使用根据主键来查询的场景
	 * @param 
	 * @return Map<String,Object>
	 * @throws
	 */
	public  Map<String, Object> getFirstRowMap(String sql, Object... params) {
		Map<String,Object> queryMap = null;
		try {
			QueryRunner runner = new QueryRunner();
			queryMap = runner.query(connection, sql, new MapHandler(), params);
		} catch (Exception ex) {
			logger.error("DbBuilder的getFirstRowMap代码块:" + ex.getMessage());
			ex.printStackTrace();
		}
		return queryMap;
	}

	/**
	 * 
	 * @Title: getListMap 
	 * @author LingHao  
	 * @date 2018年8月27日 下午3:37:09
	 * @Description: TODO 根据传入的sql查询记录，以List Map形式返回
	 * @param 
	 * @return List<Map<String,Object>>
	 * @throws
	 */
	public  List<Map<String, Object>> getListMap(String sql, Object... params) {
		 List<Map<String, Object>> queryMapList = null;
		try {
			QueryRunner runner = new QueryRunner();
			queryMapList = runner.query(connection, sql, new MapListHandler(), params);
		} catch (Exception ex) {
			logger.error("DbBuilder的getListMap代码块:" + ex.getMessage());
			ex.printStackTrace();
		}
		return queryMapList;
	}

	/**
	 * 
	 * @Title: getBean 
	 * @author LingHao  
	 * @date 2018年8月27日 下午3:37:37
	 * @Description: TODO 根据sql和对象，查询结果并以对象形式返回
	 * @param 
	 * @return T
	 * @throws
	 */
	public  <T> T getBean(String sql, Class<T> type) {
		T t = null;
		try {
			QueryRunner runner = getQueryRunner();
			t = runner.query(sql, new BeanHandler<T>(type));
		} catch (Exception ex) {
			logger.error("DbBuilder的getBean代码块:" + ex.getMessage());
			ex.printStackTrace();
		}
		return t;
	}

	/**
	 * 
	 * @Title: getBean 
	 * @author LingHao  
	 * @date 2018年8月27日 下午3:37:51
	 * @Description: TODO 根据sql和对象，查询结果并以对象形式返回
	 * @param 
	 * @return T
	 * @throws
	 */
	public  <T> T getBean(Connection connection, String sql, Class<T> type) {
		T t = null;
		try {
			QueryRunner runner = new QueryRunner();
			t = runner.query(connection, sql, new BeanHandler<T>(type));
		} catch (Exception ex) {
			logger.error("DbBuilder的getBean代码块:" + ex.getMessage());
			ex.printStackTrace();
		}
		return t;
	}

	/**
	 * 
	 * @Title: getBean 
	 * @author LingHao  
	 * @date 2018年8月27日 下午3:38:02
	 * @Description: TODO 根据sql和对象，查询结果并以对象形式返回
	 * @param 
	 * @return T
	 * @throws
	 */
	public  <T> T getBean(Connection connection, String sql, Class<T> type, Object... params) {
		T t = null;
		try {
			QueryRunner runner = new QueryRunner();
			t = runner.query(connection, sql, new BeanHandler<T>(type), params);
		} catch (Exception ex) {
			logger.error("DbBuilder的getBean代码块:" + ex.getMessage());
			ex.printStackTrace();
		}
		return t;
	}

	/**
	 * 
	 * @Title: getListBean 
	 * @author LingHao  
	 * @date 2018年8月27日 下午3:38:12
	 * @Description: TODO 根据sql查询list对象
	 * @param 
	 * @return List<T>
	 * @throws
	 */
	public  <T> List<T> getListBean(Connection connection, String sql, Class<T> type) {
		try {
			QueryRunner runner = new QueryRunner();
			return runner.query(connection, sql, new BeanListHandler<T>(type));
		} catch (Exception ex) {
			logger.error("DbBuilder的getListBean代码块:" + ex.getMessage());
			ex.printStackTrace();
		}
		return null;
	}

	/**
	 * 
	 * @Title: getListBean 
	 * @author LingHao  
	 * @date 2018年8月27日 下午3:38:23
	 * @Description: TODO 据sql查询list对象（带参param）,没有runner会重新获取connection对象
	 * @param 
	 * @return List<T>
	 * @throws
	 */
	public  <T> List<T> getListBean(String sql, Class<T> type, Object... params) {
		try {
			QueryRunner runner = getQueryRunner();
			return runner.query(sql, new BeanListHandler<T>(type), params);
		} catch (Exception ex) {
			logger.error("DbBuilder的getListBean代码块:" + ex.getMessage());
			ex.printStackTrace();
		}
		return null;
	}

	/**
	 * 
	 * @Title: getListBean 
	 * @author LingHao  
	 * @date 2018年8月27日 下午3:40:17
	 * @Description: TODO 根据sql查询list对象 （带参param，带connection）
	 * @param 
	 * @return List<T>
	 * @throws
	 */
	public  <T> List<T> getListBean(Connection connection, String sql, Class<T> type, Object... params)  throws SQLException{
			QueryRunner runner = new QueryRunner();
			return runner.query(connection, sql, new BeanListHandler<T>(type), params);
	}

	/**
	 * 
	 * @Title: save 
	 * @author LingHao  
	 * @date 2018年8月27日 下午3:41:15
	 * @Description: TODO 根据sql和param保存操作
	 * @param 
	 * @return int
	 * @throws
	 */
	public  int save(String sql, Object... params)  throws SQLException{
		QueryRunner runner = getQueryRunner();
		return runner.update(connection,sql, params);
	}
	
	public  Object[]  insert(Connection conn,String sql, Object... params)  throws SQLException{
		QueryRunner runner = getQueryRunner();
		Object[] ids = runner.insert(conn, sql, new ArrayHandler(), params);
		return ids;
	}

	/**
	 * 
	 * @Title: save 
	 * @author LingHao  
	 * @date 2018年8月27日 下午3:41:43
	 * @Description: TODO 根据connection和sql和params进行保存操作
	 * @param 
	 * @return int
	 * @throws
	 */
	public  int save(Connection connection, String sql, Object... params) {
		try {
			QueryRunner runner = new QueryRunner();
			return runner.update(connection, sql, params);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return 0;
	}

	/**
	 * 
	 * @Title: update 
	 * @author LingHao  
	 * @date 2018年8月27日 下午3:42:09
	 * @Description: TODO 根据sql和params参数进行更新操作
	 * @param 
	 * @return int
	 * @throws
	 */
	public  int update(String sql, Object... params) {
		try {
			QueryRunner runner = getQueryRunner();
			return runner.update(sql, params);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return 0;
	}

	/**
	 * 
	 * @Title: update 
	 * @author LingHao  
	 * @date 2018年8月27日 下午3:42:33
	 * @Description: TODO  根据connection和sql和params参数进行更新操作
	 * @param 
	 * @return int
	 * @throws
	 */
	public  int update(Connection connection, String sql, Object... params) {
		try {
			QueryRunner runner = new QueryRunner();
			return runner.update(connection, sql, params);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return 0;
	}

	/**
	 * 
	 * @Title: delete 
	 * @author LingHao  
	 * @date 2018年8月27日 下午3:43:20
	 * @Description: TODO 根据sql和params参数进行删除操作
	 * @param 
	 * @return int
	 * @throws
	 */
	public  int delete(String sql, Object... params) {
		try {
			QueryRunner runner =  getQueryRunner();
			return runner.update(sql, params);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return 0;
	}

	/**
	 * 
	 * @Title: delete 
	 * @author LingHao  
	 * @date 2018年8月27日 下午3:44:26
	 * @Description: TODO 根据connection和sql和params参数进行删除操作
	 * @param 
	 * @return int
	 * @throws
	 */
	public  int delete(Connection connection, String sql, Object... params) {
		try {
			QueryRunner runner = new QueryRunner();
			return runner.update(connection, sql, params);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return 0;
	}

	/**
	 * 
	 * @Title: batch 
	 * @author LingHao  
	 * @date 2018年8月27日 下午3:44:53
	 * @Description: TODO 批量操作，包括批量保存，修改、删除	 
	 * @param 
	 * @return int[]
	 * @throws
	 */
	public  int[] batch(String sql, Object[][] params) {
		try {
			QueryRunner runner = getQueryRunner();
			return runner.batch(sql, params);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 
	 * @Title: batch 
	 * @author LingHao  
	 * @date 2018年8月27日 下午3:47:11
	 * @Description: TODO 批量操作，包括批量保存,修改、删删除	
	 * @param 
	 * @return int[]
	 * @throws
	 */
	public  int[] batch(Connection connection, String sql, Object[][] params) 
	{
		int[] num = null; 
		try 
		{
			QueryRunner runner = new QueryRunner();
			num = runner.batch(connection, sql, params);
		} catch (Exception ex) 
		{
			ex.printStackTrace();
		}
		return num;
	}
	
	/**
	 * 
	 * @Title: insertBatch 
	 * @author LingHao  
	 * @date 2018年8月28日 下午1:36:01
	 * @Description: TODO 批量插入
	 * @param 
	 * @return int[]
	 * @throws
	 */
	public  Long[] insertBatch(Connection connection,String sql, Object[][] params) {
		Long[] ids = new Long[params.length];
		List<Object[]> objects =  new ArrayList<Object[]>();
		try 
		{
			QueryRunner runner = new QueryRunner();
			objects = runner.insertBatch(connection, sql, new ArrayListHandler(), params);
		} catch (Exception ex) 
		{
			ex.printStackTrace();
		}
		if(objects != null && objects.size()>0) {
			for(int i = 0;i<objects.size();i++) {
				logger.info(sql+"  批量插入数据id:"+objects.get(i)[0]);
				ids[i] = (Long)objects.get(i)[0];
			}
		}
		return ids;
	}

	/**
	 * 
	 * @Title: beginTransaction 
	 * @author LingHao  
	 * @date 2018年8月27日 下午3:47:26
	 * @Description: TODO 开始事务
	 * @param 
	 * @return void
	 * @throws
	 */
	public  void beginTransaction(Connection conn) {
		try {
			conn.setAutoCommit(false);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 
	 * @Title: rollback 
	 * @author LingHao  
	 * @date 2018年8月27日 下午3:47:35
	 * @Description: TODO 回滚事务
	 * @param 
	 * @return void
	 * @throws
	 */
	public  void rollback(Connection conn) {
		try {
			conn.rollback();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 
	 * @Title: commit 
	 * @author LingHao  
	 * @date 2018年8月27日 下午3:47:42
	 * @Description: TODO 提交事务
	 * @param 
	 * @return void
	 * @throws
	 */
	public  void commit(Connection conn) {
		try {
			conn.commit();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}

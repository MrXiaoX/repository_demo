package com.cln.ICplace;

import java.sql.Connection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.cln.Utils.DbBuilder;
import com.cln.Utils.DbConnUtil;
import com.cln.Utils.ICUtils;

public class ICRunable implements Runnable
{
	private static Logger logger = Logger.getLogger(ICRunable.class);

	@Override
	public void run()
	{
		try {
			//1.查询card_sign = 5  和 placeStatus = 0的15条数据
			//1.1打开链接
			Connection connMysql = DbConnUtil.createMysqlConn();
			DbBuilder mysql = new DbBuilder(connMysql);
			StringBuffer sql = new StringBuffer();
			sql.append( "SELECT tbtd.id,")
				.append("tbtd.terminal_no,")
				.append("tbtd.card_no,")
				.append("tbtd.trans_amount,")
				.append("tbtd.consume_type,")
				.append("tbtd.round_trip,")
				.append("tbtd.mer_code,")
				.append("tbtd.company_name,")
				.append("tbtd.line_road_id,")
				.append("tbtd.plate_number,")
				.append("tbtd.operator_id,")
				.append("tbtd.station_no,")
				.append("tbtd.trans_date,")
				.append("tbtd.after_amount,")
				.append("tlr.line_road_name lineRoadName ")
				.append("FROM ")
				.append(" t_bus_trans_data tbtd ")
				.append(" LEFT JOIN t_line_road tlr ON tbtd.line_road_id = tlr.id ")
				.append(" WHERE ")
				.append(" tbtd.card_sign = 5 and tbtd.placeStatus = 'N' ")
				.append(" order by tbtd.id limit 1000");
			//1.2查询出对应的List数据
			List<Map<String, Object>> retList = mysql.getListMap(sql.toString(), null);
			
			
			DbConnUtil.close(connMysql);
			if(retList !=null && retList.size()>14) 
			{
				System.out.println("推送IC信息开始");
				// 开始时间
		        Long begin = new Date().getTime();
				
		        ICUtils.ICPlace(retList);
		        
		        Long end = new Date().getTime();
				 // 耗时
		        System.out.println("推送IC信息花费时间 : " + (end - begin) / 1000 + " s");
			}else {
				logger.info("===========数据为null或者数据量小于15条=============");
				System.out.println("===========数据为null或者数据量小于15条=============");
			}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
        
	}

}

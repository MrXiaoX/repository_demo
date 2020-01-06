package com.cln.Utils;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.txmpay.openapi.SDKContext;
import com.txmpay.openapi.model.IcOrderModel;
import com.txmpay.openapi.model.OrderResultModel;

public class ICUtils
{
	private static SDKContext mSDKContext = new SDKContext("http://60.220.210.164:8822/openapi/api/", // API 地址
			"20000001", // 入网机构代码
			"o3oZ1SJDcMBVlNI3vF-TzPFpcB0ofRoe_4LOdgDbVq9Z8L5DAFNsUdEzUyeKKFrf" // 入网机构私钥
	);

	private static Logger logger = Logger.getLogger(ICUtils.class);

	public static boolean ICPlace(List<Map<String, Object>> retList)
	{
		boolean flag = false;
		SDKContext.setHttpsTimeout(5000, 5000);

		ArrayList<IcOrderModel> icOrderModels = new ArrayList<>();

		for (Map<String, Object> retMap : retList)
		{
			IcOrderModel icOrderModel = new IcOrderModel();
			try
			{
				// 入网机构订单号，入网机构唯一 未知
				icOrderModel.setOuttradeno(retMap.get("id").toString());

				// 车载机编号 terminal_no
				icOrderModel.setPosno((String) retMap.get("terminal_no"));

				// IC卡卡号 card_no
				icOrderModel.setCardno((String) retMap.get("card_no"));

				// 订单类型 固定 2IC卡
				icOrderModel.setOrdertype(2);

				// 订单子类型 预留 未知
				icOrderModel.setSubtype(0);

				// 主卡代码 未知
				icOrderModel.setMaincardno("4");

				// 子卡代码 未知
				icOrderModel.setSubcardno("22");

				// 商品描述 未知
				icOrderModel.setProductdesc("88888路,XX站上⻋");

				// 原价(单位:分) trans_amount（这个字段是元）
				Number Oldprice = Float.parseFloat(retMap.get("trans_amount").toString()) * 100;
				icOrderModel.setOldprice(Oldprice.intValue());

				// 现价(单位:分)，折扣后，也即实际扣款 未知 trans_amount（这个字段是元）
				icOrderModel.setPrice(Oldprice.intValue());

				// 补贴金额(单位:分) 未知
				icOrderModel.setGivemoney(0);

				// 线路类型 1 一票制 2分段 consume_type
				if(retMap.get("consume_type") == null) {
					icOrderModel.setIspart(1);
				}else {
					icOrderModel.setIspart((Integer) retMap.get("consume_type"));
				}
//				icOrderModel.setIspart(1);

				// 上下行 0 下行 1上行 round_trip
				icOrderModel.setIsup((Integer) retMap.get("round_trip"));

				// 商户名称，入⽹机构内下设的商户名称或编号 mer_code
				icOrderModel.setBusinessname(retMap.get("mer_code").toString());

				// 分公司名称，商户下设的分公司名称或编号 company_name
				if(retMap.get("company_name")!=null) {
					icOrderModel.setCompanyname(retMap.get("company_name").toString());
				}

				// 线路名称或编号 未补数据前 只有线路line_road_id
				if(retMap.get("lineRoadName")==null && retMap.get("line_road_id")!=null) {
						icOrderModel.setLinename(retMap.get("line_road_id").toString());
				}else if(retMap.get("lineRoadName")!=null){
					icOrderModel.setLinename(retMap.get("lineRoadName").toString());
				}

				// 车型名称或编号 未补数据前 vehicle
				icOrderModel.setBustype("普通车");

				// 车牌号或车辆编号 plate_number
				if(retMap.get("plate_number")!=null) {
					icOrderModel.setBuscode(retMap.get("plate_number").toString());
				}

				// 司机编号/名称，最多6位，不足前补0 operator_id
				icOrderModel.setDriverno(String.valueOf(retMap.get("operator_id")));

				// 开往方向 未知
				icOrderModel.setDirection("xx总站");

				// 城市名称，入网机构所在城市名称或代码 未知
				icOrderModel.setCityname("XX市");

				// 上车站名或站号 station_no
				icOrderModel.setUpstationname(retMap.get("station_no").toString());

				// 下车站名或站号，一票制可与上车一样 station_no
				icOrderModel.setDownstationname(retMap.get("station_no").toString());

				// 上车时间 trans_date 需要转下列格式
				String trans_date = DateUtil.transStringToDateStr((String) retMap.get("trans_date"));
				icOrderModel.setGetonat(trans_date);

				// 下车时间，一票制可与上车一样 trans_date 需要转下列格式
				icOrderModel.setGetoffat(trans_date);

				// 扣费类型 1 正常扣费 2补扣
				icOrderModel.setHandtype(1);

				// PSAM终端编号 终端机编号?
				icOrderModel.setTermno("413101446024");

				// PSAM交易序号 未知
				icOrderModel.setTermseq("00000E5B");

				// 卡交易计数器 未知
				icOrderModel.setCardseq(17);

				// 交易tac码 未知
				icOrderModel.setTradetac("F187555F");

				// 交易日期
				String yearDate = DateUtil.transStringToYearDateStr((String) retMap.get("trans_date"));
				icOrderModel.setTradedate(yearDate);

				// 交易时间
				String time = DateUtil.transStringToTimeStr((String) retMap.get("trans_date"));
				icOrderModel.setTradetime(time);

				// 交易类型 6普通交易 9复合交易
				icOrderModel.setTradetype(9);

				// 算法标识
				icOrderModel.setAlgflag("0100");

				// 密钥算法版本
				icOrderModel.setAlgver("01");

				// 密钥算法索引
				icOrderModel.setAlgindex("01");

				// 交易后余额(单位:分) after_amount
				Number afterbalance = Float.parseFloat(retMap.get("after_amount").toString()) * 100;
				icOrderModel.setAfterbalance(afterbalance.intValue());

				// 入网机构收单时间 未知
				icOrderModel.setNetat(DateUtil.DateToString(new Date(), "yyyy-MM-dd HH:mm:ss"));

				// 扩展字段
				 icOrderModel.setExtra("{}");

				// 备注
				icOrderModel.setRemark("{卡联推送IC信息}");

				// 卡所属机构代码 未知
				icOrderModel.setIssuercode("03925550FFFFFFFF");

				// 市区原价 单位:分 如有市郊区划分，无则填原价 未知
//				 icOrderModel.setInmoney(200);

				// 郊区原价 单位:分 郊区原价，如有市郊区划分，无则填0 未知
//				 icOrderModel.setOutmoney(0);

				// 市区应扣 单位:分 应扣的市区部分金额，如有市郊区划分，无则填交易金额 未知
//				 icOrderModel.setSqykmoney(1400);

				// 郊区应扣 单位:分 应扣的效区部分金额，如有市郊区划分，无则填0 未知
				 icOrderModel.setJqykmoney(0);

			}
			catch (Exception e)
			{
				logger.error("转换信息Map为icOrderModel异常:" + retMap.toString() + "异常：" + e.getMessage());
				System.out.println("转换信息Map为icOrderModel异常:" + retMap.toString() + "异常：" + e.getMessage());
				e.printStackTrace();
			}
			// 添加到批量List中
			icOrderModels.add(icOrderModel);
			
			try
			{
				//每次批量15条
				if(icOrderModels.size()>14) {
					StringBuffer idstr = new StringBuffer();
					StringBuffer sqlStr = new StringBuffer("update t_bus_trans_data set placeStatus = 'Y' where id in (");
					List<OrderResultModel> resultModels = mSDKContext.icPlace(icOrderModels);
					for (OrderResultModel m : resultModels)
					{
						// 100 收单成功
						// 200 扣费成功
						// 如果返回码为100或200则为推单成功
						flag = m.getResult() == 100 || m.getResult() == 200;
						// 收单成功才添加更新id
						if (flag)
						{
							idstr.append(m.getOuttradeno()).append(",");
							logger.info("================收单成功===============收单成功ID:" + m.getOuttradeno());
							System.out.println("================收单成功===============收单成功ID:" + m.getOuttradeno());
						}
						else
						{
							logger.info("================收单失败===============收单失败ID:" + m.getOuttradeno()+"收单失败原因"+m.getMessage());
							System.out.println("================收单失败===============收单失败ID:" + m.getOuttradeno()+"收单失败原因"+m.getMessage());
						}
						//清空icOrderModels
						icOrderModels.clear();
					}
					if (idstr.length() > 0)
					{
						idstr = idstr.deleteCharAt(idstr.length() - 1);
						String sql = (sqlStr.append(idstr).append(")")).toString();
						Connection connMysql = DbConnUtil.createMysqlConn();
						DbBuilder mysql = new DbBuilder(connMysql);
						int num = mysql.update(connMysql, sql, null);
						DbConnUtil.close(connMysql);
						sqlStr.setLength(0);
						logger.info(num > 0 ? ("更新成功"+ "修改条数为" + num +",id为:"+ idstr) : ("更新失败,sql为" + sql) + "修改条数为" + num + " ids:" + idstr);
						System.out.println(num > 0 ? ("更新成功"+ "修改条数为" + num +",id为:"+ idstr) : ("更新失败,sql为" + sql) +" ids:" + idstr);
					}
				}
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
				logger.error("收单失败,icPlace推送IC信息异常：" + ex.getMessage());
				System.out.println("收单失败,icPlace推送IC信息异常：" + ex.getMessage());
				flag = false;
			}
		}
		return flag;
	}
}

package com.cln.ICplace;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.cln.Utils.PropertiesUtil;

/**
 * 
 * @ClassName: ICScheduleJob 
 * @Description: TODO  10秒推送一次15条数据
 * @author LingHao
 * @date 2018年11月8日 下午4:04:31  
 * @Company 深圳市卡联科技股份有限公司
 */
public class ICScheduleJob
{

	//创建定时线程,线程池1个
	ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
	
	
	public static void main(String[] args)
	{
		PropertiesUtil.init();
		
		ICScheduleJob iCScheduleJob = new ICScheduleJob();
		//10秒执行一次
		iCScheduleJob.executor.scheduleAtFixedRate(new ICRunable(), 0, 10, TimeUnit.SECONDS);
	}
}

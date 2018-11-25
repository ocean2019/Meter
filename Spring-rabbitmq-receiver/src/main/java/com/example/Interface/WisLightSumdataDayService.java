package com.example.Interface;


/**
 * @author zhangkx
 *
 */
public interface WisLightSumdataDayService {
	
	
	 /**
	 * show  把一天的数据插入wis_light_sumdata_day中去
	 * @param value  一天消耗的电量
	 * @param time  采集时间
	 * @param tagid  标签id
	 */
	void insertOneDay(double value,Long time,int tagid);

	 
	 
}

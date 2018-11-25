package com.example.Interface;


/**
 * @author zhangkx
 *
 */
public interface WisLightSumdataHoursService {
	

	 /**
	 *  show  把半个小时的数据插入wis_light_sumdata_hours表中
	 * @param value  半小时的累计电量
	 * @param timeLong  采集时间
	 * @param tagid 标签id
	 */
	void insertHalfHour(double value,Long timeLong,int tagid);
	

}

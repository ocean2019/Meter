package com.example.Interface;

import java.util.List;

import com.example.pojo.WisLightPowerdataHourPojo;


/**
 * @author zhangkx
 *
 */
public interface WisLightPowerdataHourService {
	
//
//	/**
//	 * show  向表wis_light_powerdata_hour表中插入数据
//	 * @param wis WisLightPowerdataHourPojo对象
//	 * @return 
//	 */
//	int insertWisLightPowerdataHour(WisLightPowerdataHourPojo wis);

	/**
	 * show  向表wis_light_powerdata_hour表中插入数据
	 * @param wis WisLightPowerdataHourPojo对象
	 * @return 
	 */
	void insertWisLightPowerdataHour(List<WisLightPowerdataHourPojo> arr);
	
	
	

}

package com.example.Interface;

import java.util.Map;

/**
 * @author zhangkx
 *
 */
public interface WisDeviceTagService {
	
	/**
	 * show 得到所有的设备标签关系表信息   
	 * @return 返回一个设备表ID为主键  标签表ID为值的map集合
	 *
	 */
	 Map<Integer, Integer> getAllWisDeviceTag();

	 
	 
	 
}

package com.example.Interface;

import java.util.Map;

/**
 * @author zhangkx
 *
 */
public interface WisDeviceService {
	
	  /**
		 * show 得到所有的设备信息 并返回y
		 * @return 返回一个设备号为主键  表ID为值的map集合
		 *
		 */
	 Map<String, Integer> getAllWisDevice();

	 
	 
	 
	 
	 
}

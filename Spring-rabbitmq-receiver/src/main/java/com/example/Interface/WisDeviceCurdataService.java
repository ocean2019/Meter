package com.example.Interface;

import net.sf.json.JSONObject;

/**
 * @author zhangkx
 * */
public interface WisDeviceCurdataService {
	
	
	  /**
	 * show 这个接口实现了curdata表的数据更新
	 * @param jo  接收的一条信息里的一个用户的实体JSON对象
	 * @param timeLong   采集时间
	 * @param deviceid  设备ID
	 * 
	 *
	 */
	void changeDeviceCurdata(JSONObject jo,Long timeLong,int deviceid);

}

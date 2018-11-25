package com.example.Interface;


import java.util.Date;
import java.util.List;


import com.example.pojo.WisDeviceDataPojo;


/**
 * @author zhangkx
 *
 */
public interface WisDeviceDataService {
	
	
	
//     /**
//      * show  把接收到的数据插入表 wis_device_data中
//     * @param js  接收到的数据
//     * @param timeLong 采集时间
//     * @param did  设备id
//     */
//    void  insertFiveMin(JSONObject js,Long timeLong,int did);
    
    /**
     * show  把接收到的数据插入表 wis_device_data中
    * 
    */
   void  insertFiveMin(List<WisDeviceDataPojo> arr);
	
	/** 
	 * show  查询是否存在这么一条数据
	 * @param did  设备id
	 * @param collecttime  采集时间
	 * @return  true为存在   false 为不存在
	 */
	boolean findByDidAndCollecttime(int did,Date collecttime);

	
     
}

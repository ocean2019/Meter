package com.example.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.example.pojo.WisDeviceDataPojo;
import com.example.util.WisDeviceDataSql;

/**
 * @author zhangkx
 *
 */

public interface WisDeviceDataDao {
	
//	/**
//	 * show  向表wis_device_data表中插入数据
//	 * @param wis  WisDeviceDataPojo对象
//	 * @return
//	 */
//	@Insert("insert into wis_device_data(did,collecttime,recvtime,data,status,oper,opdate)values(#{wis.did},#{wis.collecttime},#{wis.recvtime},CAST(#{wis.data1} as json),#{wis.status},#{wis.oper},#{wis.opdate})")
//	public int addWisDeviceData(@Param("wis")WisDeviceDataPojo wis);
//
//	
	/**
	 * show  向表wis_device_data表中插入数据
	 * @param wis  WisDeviceDataPojo对象
	 * @return
	 */
//  @Insert("insert into wis_device_data(did,collecttime)values(#{arr[0].did},#{arr[0].collecttime})")
 // @Insert("insert into wis_device_data(did,collecttime,recvtime,data,status,oper,opdate)values(#{arr.get[i].did},#{wis.collecttime},#{wis.recvtime},CAST(#{wis.data1} as json),#{wis.status},#{wis.oper},#{wis.opdate})")
   @InsertProvider(method = "addWisDeviceData", type =WisDeviceDataSql.class)
	 boolean addWisDeviceData(@Param("arr")List<WisDeviceDataPojo> arr);
   
   /**
	 * show 根据设备did和采集时间collecttime确定唯一的一条数据 并返回结果
	 * @param did  设备id
	 * @param collecttime  采集时间
	 * @return 返回一个对象
	 */
     
 @Select("Select did,collecttime from wis_device_data where did=#{did} and collecttime=#{collecttime}")
   WisDeviceDataPojo findByDidAndCollectime(@Param("did")int did,@Param("collecttime")Date collecttime);
	
	

}

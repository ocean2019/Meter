package com.example.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.example.pojo.WisDevicePojo;

/**
 * @author zhangkx
 *
 */
@Mapper
public interface WisDeviceDao {
	
	/**
	 * show 得到所有的wis_device表中的数据
	 * @return  返回一个List集合
	 */
	@Select("select id,deviceid from wis_device")
	public List<WisDevicePojo> getAllWisDevice();
	

	
	
}

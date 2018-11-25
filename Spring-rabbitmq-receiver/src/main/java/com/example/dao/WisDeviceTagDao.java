package com.example.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.example.pojo.WisDeviceTagPojo;

/**
 * @author zhangkx
 *
 */
@Mapper
public interface WisDeviceTagDao {
	
	/**
	 * show 得到表 wis_device_tag表中所有的数据
	 * @return List集合
	 */
	@Select("select did,tagid from wis_device_tag")
	public List<WisDeviceTagPojo> getAllWisDeviceTag();
	
	

}

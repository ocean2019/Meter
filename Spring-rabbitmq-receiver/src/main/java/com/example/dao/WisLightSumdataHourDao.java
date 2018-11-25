package com.example.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.pojo.WisLightSumdataHoursPojo;

/**
 * @author zhangkx
 *
 */
@Mapper
public interface WisLightSumdataHourDao {
	
	
	/**
	 * show 向表wis_light_sumdata_hour中插入数据
	 * @param wis  WisLightSumdataHoursPojo对象
	 * @return
	 */
	@Insert("insert into wis_light_sumdata_hour(tagid,reptime,sumtype1,val1)values(#{wis.tagid},#{wis.reptime},#{wis.sumtype1},#{wis.val1})")
	public boolean addWisLightSumdataHours(@Param("wis")WisLightSumdataHoursPojo wis);
	

}

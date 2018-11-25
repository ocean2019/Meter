package com.example.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.pojo.WisLightSumdataDayPojo;

/**
 * @author zhangkx
 *
 */

@Mapper
public interface WisLightSumdataDayDao {
	
	
	/**
	 * show 向表wis_light_sumdata_day中插入数据
	 * @param wis  WisLightSumdataDayPojo对象
	 * @return
	 */
	@Insert("insert into wis_light_sumdata_day(tagid,reptime,sumtype1,val1)values(#{wis.tagid},#{wis.reptime},#{wis.sumtype1},#{wis.val1})")
	public boolean addWisLinghtSumdataDayDao(@Param("wis")WisLightSumdataDayPojo wis);

	
}

package com.example.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.pojo.WisLightPowerdataHourPojo;
import com.example.util.WisLightPowerdataHourSql;

/**
 * @author zhangkx
 *
 */
@Mapper
public interface WisLightPowerdataHourDao {
	
//	
//  /**
//   * show 向表wis_light_powerdata_hour表中插入数据
// * @param wis  WisLightPowerdataHourPojo对象  
// * @return
// */
//@Insert("insert into wis_light_powerdata_hour(tagid,reptime,type1,val1,type2,val2,type3,val3,type4,val4,type5,val5,type6,val6,type7,val7,type8,val8,status,oper,opdate,extrainfo)"
//  		+ "values(#{wis.tagid},#{wis.reptime},#{wis.type1},#{wis.val1},#{wis.type2},#{wis.val2},#{wis.type3},#{wis.val3},#{wis.type4},#{wis.val4},#{wis.type5},#{wis.val5},#{wis.type6},#{wis.val6},#{wis.type7},#{wis.val7},#{wis.type8},#{wis.val8},#{wis.status},#{wis.oper},#{wis.opdate},#{wis.extrainfo})")
//  int insertWisLightPowerdataHour(@Param("wis")WisLightPowerdataHourPojo wis);


/**
 *感觉这个很慢   主要是要等这个的运行   果然慢
 * show 向表wis_light_powerdata_hour表中插入数据
* @param wis  WisLightPowerdataHourPojo对象  
* @return
*/
      @InsertProvider(method = "insertWisLightPowerdataHour", type =WisLightPowerdataHourSql.class)
     int insertWisLightPowerdataHour(@Param("arr")List<WisLightPowerdataHourPojo> arr);

}

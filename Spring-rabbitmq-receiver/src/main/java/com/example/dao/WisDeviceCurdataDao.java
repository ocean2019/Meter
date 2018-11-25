package com.example.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;

import com.example.pojo.WisDeviceCurdataPojo;
import com.example.util.WisDeviceDataSql;

/**
 * @author zhangkx
 *
 */
@Mapper
public interface WisDeviceCurdataDao {

	/**
	 * show  向表Wis_Device_Curdata中插入数据
	 * @param wis
	 * @return 
	 */
	@Insert("insert into wis_device_curdata(deviceid,reptime,type1,val1,type2,val2,type3,val3,type4,val4,type5,val5,type6,val6,type7,val7,type8,val8,type9,val9,type10,val10,type11,val11,type12,val12,type13,val13,type14,val14,type15,val15,type16,val16,type17,val17,status,oper,opdate)"
			+ "values(#{wis.deviceid},#{wis.reptime},#{wis.type1},#{wis.val1},#{wis.type2},#{wis.val2},#{wis.type3},#{wis.val3},#{wis.type4},#{wis.val4},#{wis.type5},#{wis.val5},#{wis.type6},#{wis.val6},#{wis.type7},#{wis.val7},#{wis.type8},#{wis.val8},"
			+ "#{wis.type9},#{wis.val9},#{wis.type10},#{wis.val10},#{wis.type11},#{wis.val11},#{wis.type12},#{wis.val12},#{wis.type13},#{wis.val13},#{wis.type14},#{wis.val14},#{wis.type15},#{wis.val15},#{wis.type16},#{wis.val16},#{wis.type17},#{wis.val17},#{wis.status},#{wis.oper},#{wis.opdate})")
	public int addWisDeviceCurdataDao(@Param("wis")WisDeviceCurdataPojo wis);
	
	
	/**
	 * show  根据设备id更新Wis_Device_Curdata表中的数据
	 * @param wis
	 * @return
	 */
	@Update("update wis_device_curdata set reptime=#{wis.reptime},val1=#{wis.val1},val2=#{wis.val2},val3=#{wis.val3},val4=#{wis.val4},val5=#{wis.val5},val6=#{wis.val6},val7=#{wis.val7},val8=#{wis.val8}"
			+ ",val9=#{wis.val9},val10=#{wis.val10},val11=#{wis.val11},val12=#{wis.val12},val13=#{wis.val13},val14=#{wis.val14},val15=#{wis.val15},val16=#{wis.val16},val17=#{wis.val17},status=#{wis.status},oper=#{wis.oper} where deviceid=#{wis.deviceid}")
	public int updateWisDeviceCurdataDao(@Param("wis")WisDeviceCurdataPojo wis);
	

	/**
	 * show 判断表里有没有这条数据  有的话更新 没有的话 插入
	 * @param deviceid
	 * @return WisDeviceCurdataPojo
	 */
	@Select("select *from wis_device_curdata where deviceid=#{deviceid}")
	public WisDeviceCurdataPojo getOneWisDeviceCurData(@Param("deviceid")int  deviceid);
	
	
//	/**
//	 * show  根据设备id更新Wis_Device_Curdata表中的数据
//	 * @param wis
//	 * @return
//	 */
//	@UpdateProvider(method = "updateWisDeviceCurdata", type =WisDeviceCurdataSql.class)
//	public int updateWisDeviceCurdata(@Param("arr")List<WisDeviceCurdataPojo> arr);
//	
//	
}

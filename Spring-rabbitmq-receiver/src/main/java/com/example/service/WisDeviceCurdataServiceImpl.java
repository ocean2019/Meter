package com.example.service;
import java.sql.Timestamp;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Interface.WisDeviceCurdataService;
import com.example.dao.WisDeviceCurdataDao;
import com.example.pojo.WisDeviceCurdataPojo;
import com.example.util.TimeUtil;

import net.sf.json.JSONObject;

/**
 * @author zkx
 *
 */
@Service
public class WisDeviceCurdataServiceImpl implements WisDeviceCurdataService {

	
	@Autowired
	private  WisDeviceCurdataDao deviceCurdataDao;

	@Override
	 public void changeDeviceCurdata(JSONObject jo,Long timeLong,int deviceid) {
    	
    	WisDeviceCurdataPojo wis=new WisDeviceCurdataPojo();
    	Date date2=TimeUtil.getCommTime(timeLong);
		wis.setDeviceid(deviceid);
		//这是采集时间
		wis.setReptime(date2);
		//操作人  现在我先随便写的
		wis.setOper(1);
		wis.setVal1(jo.getDouble("累计电量"));
         wis.setVal2(jo.getDouble("用户户号"));
		wis.setVal3(jo.getDouble("当前功率"));
		wis.setVal4(jo.getDouble("电压"));
		wis.setVal5(jo.getDouble("电流"));
		wis.setVal6(jo.getDouble("功率因数"));
		wis.setVal7(jo.getDouble("无功功率"));
		wis.setStatus(1);
		if(jo.has("B当前功率")) {
			wis.setVal8(jo.getDouble("B当前功率"));
	        wis.setVal9(jo.getDouble("B电压"));
			wis.setVal10(jo.getDouble("B电流"));
			wis.setVal11(jo.getDouble("B功率因数"));
			wis.setVal12(jo.getDouble("B无功功率"));
			wis.setVal13(jo.getDouble("C当前功率"));
	         wis.setVal14(jo.getDouble("C电压"));
			wis.setVal15(jo.getDouble("C电流"));
			wis.setVal16(jo.getDouble("C功率因数"));
			wis.setVal17(jo.getDouble("C无功功率"));	
		}
		//判断数据库里是否已经有了这条数据
    	WisDeviceCurdataPojo wisDao=new WisDeviceCurdataPojo();
    	int a=0;
    	if(a<=281) {
        wisDao=deviceCurdataDao.getOneWisDeviceCurData(deviceid);}
        a++;
		if(wisDao==null&&a<=281){
		//累计电量
		wis.setType1(1);
		//用户户号
		wis.setType2(2);
       //当前功率
		wis.setType3(3);
       //电压
		wis.setType4(4);
      //电流
		wis.setType5(5);
      //功率因数
		wis.setType6(6);
      //无功功率
		wis.setType7(7);
		//B当前功率
		wis.setType8(8);
//		B电压
		wis.setType9(9);
//		B电流
		wis.setType10(10);
//		B功率因数
		wis.setType11(11);
//		B无功功率
		wis.setType12(12);
//		C当前功率
		wis.setType13(13);
//		C电压
		wis.setType14(14);
//		C电流
		wis.setType15(15);
//		C功率因数
		wis.setType16(16);
//		C无功功率
		wis.setType17(17);
		wis.setOpdate(new Timestamp(System.currentTimeMillis()));
		deviceCurdataDao.addWisDeviceCurdataDao(wis);
		}else {
		deviceCurdataDao.updateWisDeviceCurdataDao(wis);
		}
    }
	
}

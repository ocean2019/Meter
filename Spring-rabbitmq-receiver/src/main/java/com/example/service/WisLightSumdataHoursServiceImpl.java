package com.example.service;

import java.util.Date;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Interface.WisLightSumdataHoursService;
import com.example.dao.WisLightSumdataHourDao;
import com.example.pojo.WisLightSumdataHoursPojo;

import lombok.extern.slf4j.Slf4j;

/**
 * @author zhangkx
 *
 */
@Service
@Slf4j
public class WisLightSumdataHoursServiceImpl implements WisLightSumdataHoursService {

	@Autowired
	private  WisLightSumdataHourDao lightSumdataHourDao;
	
	
	
	@Override
	//把半个小时的数据插入数据库里  先只插入电流吧 
		public  void insertHalfHour(double value,Long timeLong,int tagid) throws JSONException {
			boolean  flag=false;
			WisLightSumdataHoursPojo wis=new WisLightSumdataHoursPojo();
			//统计时间指的是采集时间
			wis.setReptime(new Date(timeLong));
			//这里指的是消耗的电量
			wis.setSumtype1(1);
			wis.setVal1(value);
			//标签ID
			wis.setTagid(tagid);
			flag=lightSumdataHourDao.addWisLightSumdataHours(wis);
            log.info("插入半个小时的数据成功了"+flag);
		}

}

package com.example.service;

import java.util.Date;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Interface.WisLightSumdataDayService;
import com.example.dao.WisLightSumdataDayDao;
import com.example.pojo.WisLightSumdataDayPojo;

import lombok.extern.slf4j.Slf4j;


/**
 * @author zhangkx
 *
 */
@Service
@Slf4j
public class WisLightSumdataDayServiceImpl implements WisLightSumdataDayService {
	
	@Autowired
	private  WisLightSumdataDayDao lightSumdataDayDao;
	
	@Override
	public  void insertOneDay(double value,Long time,int tagid) throws JSONException {
		boolean  flag=false;
		WisLightSumdataDayPojo wis=new WisLightSumdataDayPojo();
		//统计时间指的是采集时间 这个采集的最后一次数据是凌晨 所以记录的是后一天的时间所以要-1天 但是保存的是date型 所以我-5分钟 就好了  就是 300000微秒；
		wis.setReptime(new Date(time-300000));
		//这里指的是消耗的电量
		wis.setSumtype1(1);
		wis.setVal1(value);
		wis.setTagid(tagid);
		flag=lightSumdataDayDao.addWisLinghtSumdataDayDao(wis);
		log.info("一天的数据插入成功了"+flag);
	}

}

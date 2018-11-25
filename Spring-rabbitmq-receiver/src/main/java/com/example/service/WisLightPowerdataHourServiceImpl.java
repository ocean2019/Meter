package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.example.Interface.WisLightPowerdataHourService;
import com.example.dao.WisLightPowerdataHourDao;
import com.example.pojo.WisLightPowerdataHourPojo;

/**
 * @author zhangkx
 *
 */
@Service

public class WisLightPowerdataHourServiceImpl implements WisLightPowerdataHourService{
	
	
	@Autowired
	private WisLightPowerdataHourDao lightPowerdataHourDao;

	@Override
	@Async("asyncServiceExecutor")//开启多线程
	public void insertWisLightPowerdataHour(List<WisLightPowerdataHourPojo> arr) {
		int a=0;
		if(arr!=null) {
        a=lightPowerdataHourDao.insertWisLightPowerdataHour(arr);}
		System.gc();
        System.out.println("lightPowerData"+a);
	
	}

//	@Override
//	public int insertWisLightPowerdataHour(WisLightPowerdataHourPojo arr) {
//	   
//		int a=lightPowerdataHourDao.insertWisLightPowerdataHour(arr);
//		return a;
//	}

	
	
	
	
}

package com.example.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Interface.WisDeviceService;
import com.example.dao.WisDeviceDao;
import com.example.pojo.WisDevicePojo;

/**
 * @author zkx
 *
 */
@Service
public class WisDeviceServiceImpl implements WisDeviceService {
	
	
	@Autowired
	private WisDeviceDao deviceDao;

	@Override
	public  Map<String, Integer> getAllWisDevice() {
		Map<String, Integer> mapDevice=new HashMap<>();
		List<WisDevicePojo> arr=new  ArrayList<WisDevicePojo>();
		arr=deviceDao.getAllWisDevice();
		int arrSize=arr.size();
		WisDevicePojo wis=new WisDevicePojo();
		for(int i=0;i<arrSize;i++) {
			wis=arr.get(i);
           mapDevice.put(wis.getDeviceid(),wis.getId());
		} 
		return mapDevice;
	}

}

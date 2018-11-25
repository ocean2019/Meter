package com.example.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Interface.WisDeviceTagService;
import com.example.dao.WisDeviceTagDao;
import com.example.pojo.WisDeviceTagPojo;


/**
 * @author zhangkx
 *
 */
@Service
public class WisDeviceTagServiceImpl implements WisDeviceTagService {
	
	@Autowired
	private WisDeviceTagDao deviceTagDao;

	@Override
	public  Map<Integer, Integer> getAllWisDeviceTag() {
		Map<Integer, Integer>  mapTag=new HashMap<>();
		List<WisDeviceTagPojo> arr=new ArrayList<>();
		arr=deviceTagDao.getAllWisDeviceTag();
		int arrSize=arr.size();
		WisDeviceTagPojo wis=new WisDeviceTagPojo();
		for(int i=0;i<arrSize;i++) {
			wis=arr.get(i);
			mapTag.put(wis.getDid(),wis.getTagid());
		}
		
		return mapTag;
	}

}

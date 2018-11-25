package com.wis.jinan.web;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wis.jinan.entity.WisDevice;
import com.wis.jinan.service.WisDeviceService;

@RestController
@RequestMapping("/api/device")
public class WisDeviceController {
	
	private  final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	WisDeviceService wisdeviceService;
	
	@RequestMapping(value = "/list",method = RequestMethod.GET)
	public  List<WisDevice> getWisDevices(){
		
		return wisdeviceService.findWisDeviceList();
	}
	
	@RequestMapping(value = "/{id}",method = RequestMethod.GET)
	public  WisDevice getWisDeviceById(@PathVariable("id") int id){
		WisDevice wisDevice  = wisdeviceService.findWisDeviceById(id);

		logger.info(wisDevice.toString());
	
		return wisDevice;
	}
}

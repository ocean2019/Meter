package com.wis.jinan.service;

import	com.wis.jinan.dao.WisDeviceDao;
import	com.wis.jinan.entity.User;
import com.wis.jinan.entity.WisDevice;
import	com.wis.jinan.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import	java.util.List;

@Service
public class WisDeviceService {
    @Autowired
    WisDeviceDao wisdeviceDao;
   
    public WisDevice findWisDeviceById(int id) {
        return wisdeviceDao.findWisDeviceById(id);
    }

   
    public List<WisDevice> findWisDeviceList() {
        return wisdeviceDao.findWisDeviceList();
    }
}


package com.wis.jinan.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wis.jinan.dao.DepartmentPowerDao;
import com.wis.jinan.entity.DepartmentPower;

@Service
public class DepartmentPowerService {
	@Autowired
	DepartmentPowerDao departmentPowerDao;
	
	public String findDepartmentPowerById(int id, int eType, String period, String sdate, String edate) {
		int					type;
		String				startDate = new String();
		String				endDate = new String();
		Date				t = new Date();
		SimpleDateFormat 	ft = new SimpleDateFormat ("yyyyMMddHHmmss");
		SimpleDateFormat 	dft = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
		DepartmentPower		departmentPower;
				
		type = 0;
		
		if(period.equals("day")){
			type = 2;
		}else if(period.equals("hour")) {
			type = 1;
		}else {
			type = 1;
		}
		
		try {
			t = ft.parse(sdate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		startDate = dft.format(t);

		try {
			t = ft.parse(edate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		endDate = dft.format(t);

        departmentPower =  departmentPowerDao.findDepartmentPowerById(id, eType, type, startDate, endDate);
        
        return departmentPower.toJSONstr();
    }
}

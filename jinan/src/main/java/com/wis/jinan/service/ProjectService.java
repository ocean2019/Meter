package com.wis.jinan.service;

import	java.util.*;
import	com.wis.jinan.entity.TotalPower;
import	com.wis.jinan.dao.ProjectDao;
import	com.wis.jinan.entity.TotalEnergy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {
	@Autowired
	ProjectDao	projectDao;
	
	@Autowired
	TotalPower	totalPower;
	
	@Autowired
	TotalEnergy	totalEnergy;
	
	static	int		totalWater, totalGas;

	private  final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	ProjectService() {
//		GregorianCalendar gcalendar = new GregorianCalendar();
		
		Calendar gcalendar = Calendar.getInstance();
		
		logger.info("ProjectService: " + gcalendar);
		totalWater = (int)(118+Math.random()*(145-118+1)) * gcalendar.get(Calendar.DAY_OF_YEAR);
		totalGas = (int)(429+Math.random()*(524-429+1)) * gcalendar.get(Calendar.DAY_OF_YEAR);

		logger.info("ProjectService totalWater: " + totalWater);
		logger.info("ProjectService totalGas: " + totalGas);
	}
	
	
	public String getTotalPower() {
		String message;
		int		value;
		
		value = (int)(6300+Math.random()*(7700-6300));
		
		value = projectDao.queryTotalPower();
		
		totalPower.setValue(value);
		
		message = "[{" +
                "\"name\":" + "\"" + totalPower.getName() + "\"" + "," +
                "\"value\":" + totalPower.getValue() +
                "}]";
        
		logger.info("getTotalPower" + message);
		return message;
	}
	
	public String getTotalEnergy() {
		String message;
		int		value;
		
		value = (int)(18000+Math.random()*(24000-18000+1));
		
		value = projectDao.queryTotalEnergy();
		
		totalEnergy.setValue(value);
		
		message = "[{" +
                "\"name\":" + "\"" + totalEnergy.getName() + "\"" + "," +
                "\"value\":" + totalEnergy.getValue() +
                "}]";
        
		logger.info("getTotalEnergy" + message);
		return message;
	}
	
	public String getWater() {
		String message;
		String 	name="实时总水流量";
		int		value;
		
		value = (int)(118+Math.random()*(145-118+1));
		
//		totalWater += value;
		
		message = "[{" +
                "\"name\":" + "\"" + name + "\"" + "," +
                "\"value\":" + value +
                "}]";
        
		logger.info("getWater" + message);
		return message;
	}

	public String getTotalWater() {
		String message;
		String 	name="总水量";
		int		value;
		
		totalWater += (int)(118+Math.random()*(145-118+1));
		
		value = totalWater;
		
		message = "[{" +
                "\"name\":" + "\"" + name + "\"" + "," +
                "\"value\":" + value +
                "}]";
        
		logger.info("getTotalWater" + message);
		return message;
	}

	public String getGas() {
		String message;
		String 	name="实时总燃气流量";
		int		value;
		
		value = (int)(429+Math.random()*(524-429+1));
		
//		totalGas += value;
		
		message = "[{" +
                "\"name\":" + "\"" + name + "\"" + "," +
                "\"value\":" + value +
                "}]";
        
		logger.info("getGas" + message);
		return message;
	}

	public String getTotalGas() {
		String message;
		String 	name="总燃气量";
		int		value;
		
		totalGas += (int)(429+Math.random()*(524-429+1));
		
		value = totalGas;
		
		message = "[{" +
                "\"name\":" + "\"" + name + "\"" + "," +
                "\"value\":" + value +
                "}]";
        
		logger.info("getTotalGas" + message);
		return message;
	}
}

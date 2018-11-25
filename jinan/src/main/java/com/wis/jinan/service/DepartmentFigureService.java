package com.wis.jinan.service;

import com.wis.jinan.entity.DepartmentFigure;
import com.wis.jinan.dao.DepartmentFigureDao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class DepartmentFigureService {
	@Autowired
	DepartmentFigureDao departmentFigureDao;
	
	public String findDepartmentFigureById(int id, String period, String sdate, String edate) {
		int		type;
		String	startDate = new String();
		String	endDate = new String();
		Date				t = new Date();
		SimpleDateFormat 	ft = new SimpleDateFormat ("yyyyMMddHHmmss");
		SimpleDateFormat 	dft = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
		DepartmentFigure	departmentFigure;
		
		type = 0;
		
		if(period.equals("year")) {
			type = 2;
			startDate = sdate + "-01-01";
			endDate	  = edate + "-12-31";
			 
		}else if(period.equals("month")) {
			type = 2;
			startDate = sdate.substring(0, 4) + "-" + sdate.substring(4, 6) + "-01";
			endDate	  = edate.substring(0, 4) + "-" + edate.substring(4, 6) + "-31";
		}else if(period.equals("week")) {
			type = 2;
			startDate = sdate.substring(0, 4) + "-" + sdate.substring(4, 6) + "-01";
			endDate	  = edate.substring(0, 4) + "-" + edate.substring(4, 6) + "-31";			
		}else if(period.equals("day")||
				period.equals("hour")) {
			type = 1;

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

		}
		
		departmentFigure = departmentFigureDao.findDepartmentFigureById(id, type, startDate, endDate);
		
		return departmentFigure.toJSONstr();
    }
}

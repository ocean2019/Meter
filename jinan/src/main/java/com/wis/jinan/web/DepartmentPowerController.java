package com.wis.jinan.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wis.jinan.entity.DepartmentPower;
import com.wis.jinan.service.DepartmentPowerService;

@RestController
@RequestMapping("/api/department")
public class DepartmentPowerController {
	@Autowired
	DepartmentPowerService departmentPowerService;
	
	@RequestMapping(value = "/power/{id}",method = RequestMethod.GET)
	public  String getDepartmentPowerById(@PathVariable("id") int id, 
						@RequestParam(value = "period",required = true)String period,
						@RequestParam(value = "sdate",required = true)String sdate,
						@RequestParam(value = "edate",required = true)String edate){

		
		return departmentPowerService.findDepartmentPowerById(id, 2, period, sdate, edate);
	}
	
	@RequestMapping(value = "/light/{id}",method = RequestMethod.GET)
	public  String getDepartmentLightById(@PathVariable("id") int id, 
						@RequestParam(value = "period",required = true)String period,
						@RequestParam(value = "sdate",required = true)String sdate,
						@RequestParam(value = "edate",required = true)String edate){

		
		return departmentPowerService.findDepartmentPowerById(id, 1, period, sdate, edate);
	}
}

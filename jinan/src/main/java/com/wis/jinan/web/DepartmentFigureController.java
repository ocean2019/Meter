package com.wis.jinan.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wis.jinan.service.DepartmentFigureService;;

@RestController
@RequestMapping("/api/department")
public class DepartmentFigureController {
	@Autowired
	DepartmentFigureService departmentFigureService;
	
	@RequestMapping(value = "/figure/{id}",method = RequestMethod.GET)
	public  String getDepartmentFigureById(@PathVariable("id") int id, 
						@RequestParam(value = "period",required = true)String period,
						@RequestParam(value = "sdate",required = true)String sdate,
						@RequestParam(value = "edate",required = true)String edate){
		
		return departmentFigureService.findDepartmentFigureById(id, period, sdate, edate);
	}
}

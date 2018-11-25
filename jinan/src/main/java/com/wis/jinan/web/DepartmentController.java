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

import com.wis.jinan.entity.Department;
import com.wis.jinan.service.DepartmentService;

@RestController
@RequestMapping("/api/department")
public class DepartmentController {
	
	private  final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	DepartmentService departmentService;
	
	@RequestMapping(value = "/list",method = RequestMethod.GET)
	public  List<Department> getDepartment(){
		
		return departmentService.findDepartmentList();
	}
	
	@RequestMapping(value = "/{id}",method = RequestMethod.GET)
	public  Department getDepartmentById(@PathVariable("id") int id){
		Department department  = departmentService.findDepartmentById(id);

		logger.info(department.toString());
	
		return department;
	}
}
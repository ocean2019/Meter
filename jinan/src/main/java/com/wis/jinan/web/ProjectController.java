package com.wis.jinan.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.wis.jinan.service.ProjectService;;

@RestController
@RequestMapping("/api")
public class ProjectController {
	private  final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	ProjectService projectService;
	
	@RequestMapping(value = "/getpower",method = RequestMethod.GET)
	public  String getTPower() {
		return projectService.getTotalPower();
	}
	
	@RequestMapping(value = "/getenergy",method = RequestMethod.GET)
	public  String getTEnergy() {
		return projectService.getTotalEnergy();
	}
	
	@RequestMapping(value = "/getwater",method = RequestMethod.GET)
	public  String getWater() {
		return projectService.getWater();
	}
	
	@RequestMapping(value = "/gettwater",method = RequestMethod.GET)
	public  String getTWater() {
		return projectService.getTotalWater();
	}
	
	@RequestMapping(value = "/getgas",method = RequestMethod.GET)
	public  String getGas() {
		return projectService.getGas();
	}
	
	@RequestMapping(value = "/gettgas",method = RequestMethod.GET)
	public  String getTGas() {
		return projectService.getTotalGas();
	}
}

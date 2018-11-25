package com.wis.jinan.web;


import com.wis.jinan.entity.User;

import com.wis.jinan.service.IUserService;
import com.wis.jinan.entity.User;
import com.wis.jinan.service.IUserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("api/user")
public class UserController {
	
	private  final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	IUserService userService;
	
	@RequestMapping(value = "/list",method = RequestMethod.GET)
	public  List<User> getUsers(){
		
		return userService.findUserList();
	}
	
	@RequestMapping(value = "/{id}",method = RequestMethod.GET)
	public  User getUserById(@PathVariable("id") int id){
		User user = userService.findUserById(id);

		logger.info(user.toString());
	
		return user;
	}
	
	@RequestMapping(value = "/{id}",method = RequestMethod.PUT)
	public  String updateUser(@PathVariable("id")int id , @RequestParam(value = "loginname",required = true)String name,
	@RequestParam(value = "password" ,required = true)String password){
	
		User user=new User();
		user.setPassword(password);
		user.setLoginname(name);
		user.setId(id);
		int t=userService.update(user);
		if(t==1){
			return user.toString();
		}else {
			return "fail";
		}
	}
	
	@RequestMapping(value = "",method = RequestMethod.POST)
	public  String postUser( @RequestParam(value = "loginname")String name,
								@RequestParam(value = "password" )String password){
		User user=new User();
		user.setPassword(password);
		user.setLoginname(name);
		int t= userService.add(user);
		if(t==1){
			return user.toString();
		}else {
			return "fail";
		}
	}
}
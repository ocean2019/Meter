package com.example.pojo;

import java.util.Date;

import org.json.JSONObject;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class WisDeviceTagPojo {

	private int id;
	private  int did;
	private  int tagid;
	private  int  status;
	private  Date datetime;
	private  JSONObject extrainfo;
	
	
	
	
	
}

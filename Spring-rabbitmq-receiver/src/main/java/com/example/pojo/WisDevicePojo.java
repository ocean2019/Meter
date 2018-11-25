package com.example.pojo;

import java.util.Date;

import org.json.JSONObject;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;



@Setter
@Getter
@ToString
public class WisDevicePojo {
	
	private int id;
	private String deviceid;
	private String sn;
	private int protocol;
	private int  status;
	private int oper;
	private Date opdate;
	private JSONObject extrainfo;
	
	

}

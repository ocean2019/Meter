package com.example.pojo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;

@Slf4j
@Setter
@Getter
@ToString
public class CommonPojo {
	
	private int did;
	private String address;
	private Long Longtime;
	private JSONObject jO;
	private  double data;
	

}

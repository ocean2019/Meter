package com.example.pojo;

import java.util.Date;

import org.json.JSONObject;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class WisTagPojo {

	private int id;
	private int parentid;
	private int prjid;
	private String tagname;
	private  String tagval;
	private  JSONObject tagattr;
	private  int status;
	private  int oper;
    private  Date datetime;
    private  JSONObject extrainfo;
	
	
}

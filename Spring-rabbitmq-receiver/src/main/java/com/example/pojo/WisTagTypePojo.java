package com.example.pojo;

import java.util.Date;

import org.json.JSONObject;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Setter
@Getter
@ToString
public class WisTagTypePojo {

	private int id;
	private int type;
	private  String name;
	private  String  desc;
	private  int status;
	private int oper;
	private  Date datetime;
	private JSONObject extrainfo;
}

package com.example.pojo;

import java.util.Date;

import org.json.JSONObject;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class WisLightSumdataDayPojo {
	private int id;
	private int tagid;
	private Date reptime;
	private int sumtype1;
	private double val1;
	private int sumtype2;
	private double val2;
	private int sumtype3;
	private double val3;
	private int sumtype4;
	private double val4;
	private int sumtype5;
	private double val5;
	private int sumtype6;
	private double val6;
	private int sumtype7;
	private double val7;
	private int sumtype8;
	private double val8;
	private int oper;
	private Date opdate;
	private JSONObject extrainfo;
	
}

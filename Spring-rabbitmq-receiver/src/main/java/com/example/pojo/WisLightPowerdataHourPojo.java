package com.example.pojo;

import java.util.Date;

import javax.servlet.descriptor.TaglibDescriptor;

import com.alibaba.fastjson.JSON;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class WisLightPowerdataHourPojo {
	private int id;
	private int tagid;
	private Date reptime;
	private int type1;
	private double val1;
	private int type2;
	private double val2;
	private int type3;
	private double val3;
	private int type4;
	private double val4;
	private int type5;
	private double val5;
	private int type6;
	private double val6;
	private int type7;
	private double val7;
	private int type8;
	private double val8;
	private int status;
	private int oper;
	private Date opdate;
	private JSON extrainfo;
}

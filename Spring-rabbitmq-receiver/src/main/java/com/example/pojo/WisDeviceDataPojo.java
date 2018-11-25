package com.example.pojo;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.sf.json.JSONObject;


@Setter
@Getter
@ToString
public class WisDeviceDataPojo {
	private  int  id;
	private int did;
	private Date collecttime;
	private Date recvtime;
	private JSONObject data;
	private int status;
	private int oper;
	private Date opdate;
	private String extrainfo;

	//data的json的字符串格式
	private String data1;

}

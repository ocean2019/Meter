package com.wis.jinan.entity;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class WisDevice {
		private int		id;
		private String	deviceid;
		private String	sn;
		private int		devicetype;
		private	int		protocol;
		private	int		status;
		private int		oper;
		private	Date	operdate;
		private String	extrainfo;

		private  final Logger logger = LoggerFactory.getLogger(this.getClass());
	    
		public int getId() {
	        return id;
	    }

	    public void setId(int id) {
	        this.id = id;
	    }

	    public int getOper() {
	        return oper;
	    }

	    public void setOper(int oper) {
	        this.oper = oper;
	    }

	    public int getProtocol() {
	        return protocol;
	    }

	    public void setProtocol(int protocol) {
	        this.protocol = protocol;
	    }

	    public String getDeviceid() {
	        return deviceid;
	    }

	    public void setDeviceid(String deviceid) {
	        this.deviceid = deviceid;
	    }


	    public String getExtrainfo()
	    {
	    	logger.info("user getExtrainfo");
	        
	    	return extrainfo;
	    }
	    
	    public void setExtrainfo(String extrainfo) {
	    	JSONObject jsonObject = JSONObject.parseObject(extrainfo);

	    	logger.info("user setExtrainfo");
	        logger.info("表箱规格:  " + jsonObject.getString("表箱规格"));
	        
	        
	        this.extrainfo = extrainfo;
	    }
	    
	    @Override
	    public String toString() {
	    	String	message;
	    	
	    	JSONObject	jsonObject = JSONObject.parseObject(extrainfo);
	    	JSONArray	jsonArray;
	    	JSON		json;
	    	
	    	logger.info("user toString");

	        logger.info("deviceid:  " + deviceid);
	        
	        message = "wisdevice{" +
	                "id=" + id +
	                ", deviceid='" + deviceid + '\'' +
	                ", protocol='" + protocol + '\'' +
	                ", 表箱规格='" + jsonObject.getString("表箱规格") + '\'' +
	                '}';
	        
	        logger.info(message);
	    	
	        return message;
	    }
}

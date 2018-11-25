package com.wis.jinan.entity;

import java.util.*;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSON;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class User {
	private int		id;
	private String	loginname;
	private String	password;
	private String	realname;
	private	String	heading;
	private	String	staffid;
	private int		status;
	private	int		oper;
	private Date	opdate;
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

    public String getLoginname() {
        return loginname;
    }

    public void setLoginname(String loginname) {
        this.loginname = loginname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getExtrainfo()
    {
    	logger.info("user getExtrainfo");
        
    	return extrainfo;
    }
    
    public void setExtrainfo(String extrainfo) {
    	JSONObject jsonObject = JSONObject.parseObject(extrainfo);

    	logger.info("user setExtrainfo");
        logger.info("ip:  " + jsonObject.getString("ip")+ ":" + "  sex:  " + jsonObject.getString("sex"));
        
        
        this.extrainfo = extrainfo;
    }
    
    @Override
    public String toString() {
    	String	message;
    	
    	JSONObject	jsonObject = JSONObject.parseObject(extrainfo);
    	JSONArray	jsonArray;
    	JSON		json;
    	
    	logger.info("user toString");

        logger.info("ip:  " + jsonObject.getString("ip") + ":" + "  sex:  " + jsonObject.getString("sex"));
        
        message = "User{" +
                "id=" + id +
                ", loginname='" + loginname + '\'' +
                ", password='" + password + '\'' +
                ", ip='" + jsonObject.getString("ip") + '\'' +
                ", sex='" + jsonObject.getString("sex") + '\'' +
                '}';
        
        logger.info(message);
    	
        return message;
    }
    public static void testJSONStrToJSONArray(){
    	//json字符串-简单对象型
    	final String  JSON_OBJ_STR = "{\"studentName\":\"lily\",\"studentAge\":12}";
    	//json字符串-数组类型
    	final String  JSON_ARRAY_STR = "[{\"studentName\":\"lily\",\"studentAge\":12},{\"studentName\":\"lucy\",\"studentAge\":15}]";
    	//复杂格式json字符串
    	final String  COMPLEX_JSON_STR = "{\"teacherName\":\"crystall\",\"teacherAge\":27,\"course\":{\"courseName\":\"english\",\"code\":1270},\"students\":[{\"studentName\":\"lily\",\"studentAge\":12},{\"studentName\":\"lucy\",\"studentAge\":15}]}";

        JSONArray jsonArray = JSON.parseArray(JSON_ARRAY_STR);
        //遍历方式1
        int size = jsonArray.size();
        for (int i = 0; i < size; i++){
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            System.out.println(jsonObject.getString("studentName")+":"+jsonObject.getInteger("studentAge"));
        }

        //遍历方式2
        for (Object obj : jsonArray) {
            JSONObject jsonObject = (JSONObject) obj;
            System.out.println(jsonObject.getString("studentName")+":"+jsonObject.getInteger("studentAge"));
        }
    }

}

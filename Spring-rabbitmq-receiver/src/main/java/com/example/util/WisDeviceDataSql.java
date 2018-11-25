package com.example.util;


import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.example.pojo.WisDeviceDataPojo;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class WisDeviceDataSql {
	
	public String addWisDeviceData(Map map) {
		List<WisDeviceDataPojo> arr=(ArrayList<WisDeviceDataPojo>) map.get("arr");
		StringBuilder sb=new StringBuilder();
		sb.append("insert into  wis_device_data");
		sb.append("(did,collecttime,recvtime,data,status,oper,opdate)");
	   sb.append("values");
	   int arrSize=arr.size();
		for(int i=0;i<arrSize;i++) {
			sb.append("(");
			sb.append("#{arr["+i+"].did}");
			sb.append(",");
			sb.append("#{arr["+i+"].collecttime}");
			sb.append(",");
			sb.append("#{arr["+i+"].recvtime}");
			sb.append(",");
			sb.append("CAST(#{arr["+i+"].data1} as json)");
			sb.append(",");
			sb.append("#{arr["+i+"].status}");
			sb.append(",");
			sb.append("#{arr["+i+"].oper}");
			sb.append(",");
			sb.append("#{arr["+i+"].opdate}");
		    sb.append(")");
		    if(i<arrSize-1) {
		    	sb.append(",");
		    }
		}
		return sb.toString();
	}
}

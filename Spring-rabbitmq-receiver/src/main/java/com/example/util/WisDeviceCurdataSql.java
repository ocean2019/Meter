//package com.example.util;
//
//import java.text.MessageFormat;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//
//import org.springframework.stereotype.Component;
//
//import com.example.pojo.WisDeviceCurdataPojo;
//import com.example.pojo.WisDeviceDataPojo;
//
//import lombok.extern.slf4j.Slf4j;
//
//
//@Component
//@Slf4j
//public class WisDeviceCurdataSql {
//
//	public String updateWisDeviceCurdata(Map map) {
//		List<WisDeviceCurdataPojo> arr=(ArrayList<WisDeviceCurdataPojo>) map.get("arr");
//		"update wis_device_curdata set val5=#{wis.val5},val6=#{wis.val6},val7=#{wis.val7},val8=#{wis.val8}"
////		+ ",val9=#{wis.val9},val10=#{wis.val10},val11=#{wis.val11},val12=#{wis.val12},val13=#{wis.val13},val14=#{wis.val14},val15=#{wis.val15},val16=#{wis.val16},val17=#{wis.val17},status=#{wis.status},oper=#{wis.oper} where deviceid=#{wis.deviceid}")
///
//		StringBuilder sb=new StringBuilder();
//		sb.append("update wis_device_curdata set");
//	   MessageFormat mfCron = new MessageFormat("#'{'list[{0}].cron'}'");
//	   int arrSize=arr.size();
//		for(int i=0;i<arrSize;i++) {
//			sb.append("(");
//			sb.append("reptime="+"#{arr["+i+"].did}");
//			sb.append(",");
//			sb.append("val1="+"#{arr["+i+"].collecttime}");
//			sb.append(",");
//			sb.append("val2="+"#{arr["+i+"].recvtime}");
//			sb.append(",");
//			sb.append("val3="+"CAST(#{arr["+i+"].data1} as json)");
//			sb.append(",");
//			sb.append("val4="+"#{arr["+i+"].status}");
//			sb.append(",");
//			sb.append("val5="+"#{arr["+i+"].oper}");
//			sb.append(",");
//			sb.append("val6="+"#{arr["+i+"].opdate}");
//			sb.append(",");
//			sb.append("val7="+"#{arr["+i+"].recvtime}");
//			sb.append(",");
//			sb.append("val8="+"CAST(#{arr["+i+"].data1} as json)");
//			sb.append(",");
//			sb.append("val9="+"#{arr["+i+"].status}");
//			sb.append(",");
//			sb.append("val10="+"#{arr["+i+"].oper}");
//			sb.append(",");
//			sb.append("val11="+"#{arr["+i+"].opdate}");
//			sb.append(",");
//			sb.append("val12="+"#{arr["+i+"].status}");
//			sb.append(",");
//			sb.append("val13="+"#{arr["+i+"].oper}");
//			sb.append(",");
//			sb.append("val14="+"#{arr["+i+"].opdate}");
//			sb.append(",");
//			sb.append("val15"+"#{arr["+i+"].recvtime}");
//			sb.append(",");
//			sb.append("val16="+"CAST(#{arr["+i+"].data1} as json)");
//			sb.append(",");
//			sb.append("val17="+"#{arr["+i+"].status}");
//			sb.append(",");
//			sb.append("status="+"#{arr["+i+"].oper}");
//			sb.append(",");
//			sb.append("oper="+"#{arr["+i+"].opdate}");
//			
//			where deviceid=#{wis.deviceid}
//
//		    sb.append(")");
//		    if(i<arr.size()-1) {
//		    	sb.append(",");
//		    }
//		}
//		System.out.println("zheshiSql+"+sb.toString());
//		log.info("这是sql"+sb.toString());
//		return sb.toString();
//	}
//	
//}

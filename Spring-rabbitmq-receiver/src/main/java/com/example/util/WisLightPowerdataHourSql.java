package com.example.util;

  import java.text.MessageFormat;
  import java.util.ArrayList;
  import java.util.List;
  import java.util.Map;

  import org.springframework.stereotype.Component;

import com.example.pojo.WisLightPowerdataHourPojo;

import lombok.extern.slf4j.Slf4j;

  @Component
  @Slf4j
  public class WisLightPowerdataHourSql {
	public String insertWisLightPowerdataHour(Map map) {
		List<WisLightPowerdataHourPojo> arr=(ArrayList<WisLightPowerdataHourPojo>) map.get("arr");
		StringBuilder sb=new StringBuilder();
		sb.append("insert into  wis_light_powerdata_hour");
		sb.append("(tagid,reptime,type1,val1,type2,val2,type3,val3,type4,val4,type5,val5,type6,val6,type7,val7,type8,val8,status,oper,opdate,extrainfo)");
	   sb.append("values");
	   int arrSize=arr.size();
		for(int i=0;i<arrSize;i++) {
			sb.append("(");
			sb.append("#{arr["+i+"].tagid}");
			sb.append(",");
			sb.append("#{arr["+i+"].reptime}");
			sb.append(",");
			sb.append("#{arr["+i+"].type1}");
			sb.append(",");
			sb.append("#{arr["+i+"].val1}");
			sb.append(",");
			sb.append("#{arr["+i+"].type2}");
			sb.append(",");
			sb.append("#{arr["+i+"].val2}");
			sb.append(",");
			sb.append("#{arr["+i+"].type3}");
			sb.append(",");
			sb.append("#{arr["+i+"].val3}");
			sb.append(",");
			sb.append("#{arr["+i+"].type4}");
			sb.append(",");
			sb.append("#{arr["+i+"].val4}");
			sb.append(",");
			sb.append("#{arr["+i+"].type5}");
			sb.append(",");
			sb.append("#{arr["+i+"].val5}");
			sb.append(",");
			sb.append("#{arr["+i+"].type6}");
			sb.append(",");
			sb.append("#{arr["+i+"].val6}");
			sb.append(",");
			sb.append("#{arr["+i+"].type7}");
			sb.append(",");
			sb.append("#{arr["+i+"].val7}");
			sb.append(",");
			sb.append("#{arr["+i+"].type8}");
			sb.append(",");
			sb.append("#{arr["+i+"].val8}");
			sb.append(",");
			sb.append("#{arr["+i+"].status}");
			sb.append(",");
			sb.append("#{arr["+i+"].oper}");
			sb.append(",");
			sb.append("#{arr["+i+"].opdate}");
			sb.append(",");
			sb.append("#{arr["+i+"].extrainfo}");
		    sb.append(")");
		    if(i<arrSize-1) {
		    	sb.append(",");
		    }
		}
		return sb.toString();
	}
}

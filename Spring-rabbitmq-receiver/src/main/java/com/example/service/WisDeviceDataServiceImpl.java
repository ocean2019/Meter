package com.example.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import com.example.Interface.WisDeviceDataService;
import com.example.dao.WisDeviceDataDao;
import com.example.pojo.WisDeviceDataPojo;
import lombok.extern.slf4j.Slf4j;

/**
 * @author zhangkx
 *
 */

@Service
@Slf4j

public class WisDeviceDataServiceImpl implements WisDeviceDataService {
	
	
	
	@Autowired
	private  WisDeviceDataDao deviceDataDao;

//	@Override
//	//@Transactional回滚
//	public  void  insertFiveMin(JSONObject js,Long timeLong,int did) throws JSONException {
//		WisDeviceDataPojo wis=new WisDeviceDataPojo();
//		Date date2=TimeUtil.getCommTime(timeLong);
//        Timestamp timeStamp = new Timestamp(date2.getTime());
//		wis.setCollecttime(timeStamp);
//		wis.setData1(js.toString());
//		wis.setDid(did);
//		wis.setOper(1);
//		wis.setStatus(1);
//		wis.setOpdate(new Timestamp(System.currentTimeMillis()));
//		wis.setRecvtime(new Timestamp(System.currentTimeMillis()));
//		int a=deviceDataDao.addWisDeviceData(wis);
//    	log.info("五分钟的数据插入数据库成功啦"+a);
//	
//	}
	
	@Override
	@Async("asyncServiceExecutor")//开启多线程
	public  void  insertFiveMin(List<WisDeviceDataPojo> arr) throws JSONException {
		boolean flag1=false;
	    Iterator<WisDeviceDataPojo> ite = arr.iterator();
	  log.info("我是当前程序的线程数"+Thread.activeCount());
        while(ite.hasNext())
            {
        	WisDeviceDataPojo wis=ite.next();
//          判断这条消息是不是在已经存在 true为存在  false为不存在 不存在就添加
            flag1=findByDidAndCollecttime(wis.getDid(), wis.getCollecttime());
            if(flag1==false) {
                         wis.setOper(1);
                         wis.setStatus(1);
                         wis.setOpdate(new Timestamp(System.currentTimeMillis()));
                         wis.setRecvtime(new Timestamp(System.currentTimeMillis()));
             		   }
            }
		boolean flag=false;
		if(arr.size()!=0) {
		flag=deviceDataDao.addWisDeviceData(arr);
		log.info("五分钟的数据插入数据库成功啦"+flag);}
		System.gc();
    	
	}

	@Override
	public boolean findByDidAndCollecttime(int did, Date collecttime) {
		boolean flag=false;
		WisDeviceDataPojo wis=new WisDeviceDataPojo();
		wis=deviceDataDao.findByDidAndCollectime(did, collecttime);
		if(wis!=null) {
			flag=true;
		}
		return flag;
	}


}

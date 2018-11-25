package com.example.util;
//package com.example.service;
//
//
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.Date;
//import java.util.List;
//
//import org.json.JSONException;
//import org.json.JSONObject;
//import org.springframework.amqp.rabbit.annotation.RabbitListener;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import com.example.dao.Wis_device_data_dao;
//import com.example.pojo.wis_device_data;
//
//
//
//@Component
//public class helloService {
//	
//	@Autowired
//	private Wis_device_data_dao ss;
//	//初始值可以是后期直接给的
//	//第一条数据要丢的吧
//    //分钟的记录数
//	private static int MIndex=0;
//	//分钟的初始值
//	private static double Meg=0;
//	//一个小时有的5分钟数
//	private static int MSIndex=12;
//	//当前接收时间的分钟数
//	private static int mDate;
//	//小时的记录数
//	private static int HIndex=0;
//	private static double Heg=0;
//	private static int  HSIndex=24;
//	//当前接收的小时数
//	private static int HDate;
//	//天数的记录数
//	private static int DIndex=0;
//	private static double Deg=0;
//	//月年都可以就是感觉没有必要吧
//	private static int  Mstate=0;
//	private static int Hstate=0;
//	
//	@RabbitListener(queues="one")
//	public  void  getOneTime(String js) {
//		
//		
//		
//		
//		
//	}
//	
//	
////	@RabbitListener(queues="one")
////	public void getMessage(String args) throws JSONException {
////      List<Object> list=one(args);
////	  Date date=(Date) list.get(0);
////	  double message=(double) list.get(1);
////	  System.out.println(args);
////	    double M=0;
////	    double H=0;
////	    double D=0;
////		MIndex=++MIndex;
////		//分钟的数据直接往里面放
////		//60-分钟数除以5就是这个小时剩余的分钟数
////		mDate=getMinute(date);
////		System.out.println("这是当前分钟数"+mDate);
////		if(MSIndex==12) {
////		
////		MSIndex=(60-mDate)/5;
////		}else {
////			Mstate=1;
////		}
////		//24-小时数就是这天剩余的小时数
////		HDate=getHour(date);
////		System.out.println("这是当前小时数"+HDate);
////		if(HSIndex==24) {
////		HSIndex=24-HDate;
////		}else {
////			Hstate=1;
////		}	
////		if(MIndex==MSIndex||(60-mDate==60&&Mstate==1)) {
////			H=message-Heg;
////			//M就是要存进数据库的值 每小时的值
////			//存进数据库
////			Meg=message;
////			System.out.println("我是第"+HIndex+"小时,用电量是："+H);
////			HIndex=++HIndex;//小时数+1
////			MIndex=0;//分钟计时器清零
////			MSIndex=12;//复原
////			Mstate=0;//复原
////		}
////		//判断如果到达一天之后 或者时间到了零点之后
////		if(HIndex==HSIndex||24-HDate==24) {
////			D=message-Deg;
////			//把D插入数据库
////			Deg=message;
////			DIndex=++DIndex;
////			System.out.println("我是第"+DIndex+"天，用电量是："+D);
////			HIndex=0;
////			HSIndex=24;
////			Hstate=0;
////		}
////		
////	}
////
////	public void chuli(String js) throws JSONException {
////		   JSONObject jsonObject=new JSONObject(js);
////	        wis_device_data wis=new wis_device_data();
////	        boolean flag=false;
////			Date date=getCommTime(jsonObject.get("采集时间").toString());
////			wis.setCollecttime(date);
////	       JSONObject Data=(JSONObject) jsonObject.get("用户01");
////	        wis.setData1(Data.toString());
////	        wis.setRecvtime(new Date());
////	        System.out.println(new Date()+"我是新的时间");
////	        wis.setDid(5);
////			wis.setExtrainfo(jsonObject.toString());
////			wis.setOpdate(new Date());
////	        wis.setRecvtime(new Date());
////	        wis.setStatus(1);
////			flag=ss.addWis_device_data(wis);
////	        System.out.println(flag); 
////		
////	}
//	
//
//	//处理收到的数据
//	public List<Object> one(String arjs) throws JSONException{
//	    JSONObject jsonObject=new  JSONObject(arjs);
//	  Date  date=getCommTime(jsonObject.getString("采集时间").toString());
//		double message=Double.parseDouble(jsonObject.get("eg").toString());
//		List<Object> list=new ArrayList<>();
//		list.add(date);
//		System.out.println(date+"这是Ds");
//		list.add(message);
//		return list;
//	}
//	
//	//返回YYYY-MM-DD hh-mm-ss的日期
//	public  static Date getCommTime(String time) {
//		SimpleDateFormat formatter = new SimpleDateFormat( "yyyy-MM-dd hh-mm-ss ");
//        Calendar c = Calendar.getInstance();
//        c.setTimeInMillis(Long.parseLong(time)); 
//		Date date = c.getTime();
//		String string=formatter.format(date);
//		return  date;
//	}
//	
//	//返回时间的分钟数
//	public static int getMinute(Date date) {
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(date);
//        return calendar.get(Calendar.MINUTE);
//    }
//	
//	//返回当前的小时数
//	 public static int getHour(Date date) {
//	        Calendar calendar = Calendar.getInstance();
//	        calendar.setTime(date);
//	        return calendar.get(Calendar.HOUR_OF_DAY);
//	    }
//
//	 
//}

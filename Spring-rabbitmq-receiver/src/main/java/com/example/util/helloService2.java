package com.example.util;
//package com.example.service;
//
//import java.sql.Timestamp;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import org.json.JSONException;
//import org.springframework.amqp.rabbit.annotation.RabbitListener;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import com.example.dao.wis_deviceDao;
//import com.example.dao.wis_device_curdataDao;
//import com.example.dao.wis_device_dataDao;
//import com.example.dao.wis_device_tagDao;
//import com.example.dao.wis_light_sumdata_dayDao;
//import com.example.dao.wis_light_sumdata_hourDao;
//import com.example.pojo.wis_device;
//import com.example.pojo.wis_device_curdata;
//import com.example.pojo.wis_device_data;
//import com.example.pojo.wis_device_tag;
//import com.example.pojo.wis_light_sumdata_days;
//import com.example.pojo.wis_light_sumdata_hours;
//import com.example.util.TimeUtil;
//
//import lombok.extern.slf4j.Slf4j;
//import net.sf.json.JSONObject;
//
//
//@Service
//@Slf4j
//public class helloService2  {
//	private  SimpleDateFormat formatter = new SimpleDateFormat( "yyyyMMddHHmmss");
//	@Autowired
//	private  wis_device_dataDao device_dataDao;
//	@Autowired
//	private  wis_device_tagDao device_tagDao;
//	@Autowired
//	private  wis_light_sumdata_hourDao light_sumdata_hourDao;
//	@Autowired
//	private  wis_light_sumdata_dayDao  light_sumdata_dayDao;
//	@Autowired
//	private wis_deviceDao deviceDao;
//	@Autowired
//	private wis_device_curdataDao device_curdataDao;
//
//	
//	//用来保存所有的设备id
//	private static Map<String, Integer> mapDevice=new HashMap<String, Integer>();
//	//用来保存所有的标签id
//	private static Map<Integer, Integer> mapTag=new HashMap<Integer, Integer>();
//	
//	//保存读到的电表地址 数据等
//	private static JSONObject jarr=new JSONObject();
//	//保存一天开始的最初的数据
//	private static JSONObject dayArr=new JSONObject();
//	//读取本地保存的时间的分钟发数
//	private static int JarrMdata;
//	//读取本地保存时间的小时数
//	private static int JarrHdata;
//	//读取当前数据的时间的分钟发数
//	private static int JoMdata;
//	//读取当前数据的时间的小时数
//	private static int JoHdata;
//	 //分钟的记录数
//	private static int MIndex=0;
//	//当前接收消息的分钟数
//	private static int MSIndex=5;
//	//小时的记录数
//    private static int HIndex=0;
//    
//	private static int  HSIndex=48;
//	
//	private static  int MTime=0;
//	 
//	private static   int c=0;
//	
//	
//	@RabbitListener(queues="one")
//	public void getMessage(String js) throws JSONException {
////	  JSONObject jsonObject=new JSONObject(js);
//		JSONObject jsonObject=JSONObject.fromObject(js);
//	  
//	  //获取所有wis_device的id和设备id数据
//
//
//	  getAllWis_device();
//	  //获取所有wis_device_tag的tagid和did数据
//	  getAllWis_device_tag(); //以后如果有更新这两个表的操作一定要重新调用这两个方法 然后再保存
//	  
//	  //获取采集时间
//	  Long timeLong= Long.parseLong(jsonObject.getString("采集时间"))*1000;
//	  //把获得的数据进行拆分
//	  for(int i=1;i<=jsonObject.size()-4;i++) 
//	  {   //当前读取到的对象
//          JSONObject jo=new JSONObject();
//         
//          JSONObject jssHours=new JSONObject();
//          //判断是不是一天的数据
//          JSONObject dayJs=new JSONObject();
//          if(i<=9)
//          {
//                 jo=JSONObject.fromObject(jsonObject.get("用户0"+i)) ;
//          }else {
//		          jo=JSONObject.fromObject(jsonObject.getJSONObject("用户"+i));
//	            }	
//    	  String address=jo.get("电表地址").toString();
//    	  //把五分钟接收的数据插入五分钟接收的表里
//  		  //获取设备ID 根据电表地址
//  		  int did=mapDevice.get(address); 
//            insertFiveMin(jo,timeLong,did);
//          //把数据拆分插入表CurdataDao
//            changeDevice_curdata(jo,timeLong,did);
//            //只是把第一条数据当作了初始数据
//          if(!jarr.has(address))
//          {   
//              //判断保存时间和半个小时的差值是取一个相对近的默认值
//               if(c==0) {
//            	   c=1;//如果C==0它就会一直以为是一半个小时 其实应该为1；
//               }
//               jssHours.put("采集时间",timeLong);
//               jssHours.put("电量", jo.get("累计电量"));
//               jssHours.put("MIn",MIndex);
//               jssHours.put("HIn",HIndex);
//               jarr.put(address, jssHours);//会有误会呀 只是第一个五分钟的数据 而不是零的
//
//               //会少五分钟的数据 我又不能让它默认是零
//               //半个小时可能就会少的是十分钟的数据 但是下一个30分钟会补上这个量所以问题不大把。
//               //一天的初始化值
//               dayJs.put("采集时间",timeLong);
//               dayJs.put("电量", jo.get("累计电量"));
//               dayJs.put("HIn", 0);
//               dayArr.put(address, dayJs);
//          }else{
//        	  //算算次数
//        	  MTime=Math.abs(30-JarrMdata);
//              c=MTime/MSIndex;
////        	  //将自己的消息来的次数添加其中+1
//        	  jssHours=jarr.getJSONObject(address);
//        	  
//              jssHours.put("MIn", jarr.getJSONObject(address).getInt("MIn")+1);
//              
//         	  jarr.put(address, jssHours);
//         	  
//       	      Date date=TimeUtil.getCommTime(timeLong);
// 
//              JoMdata=TimeUtil.getMinute(date);
//              JoHdata=TimeUtil.getHour(date);
//              
//              Date date2=TimeUtil.getCommTime(jarr.getJSONObject(address).getLong(("采集时间")));
//              System.out.println(formatter.format(date2)+"这是时间2");
//              JarrHdata=TimeUtil.getHour(date2);
//              JarrMdata=TimeUtil.getMinute(date2);
//              System.out.println("我是C"+"Cccccccccccccccccccc"+c);
// 
//              //判断是不是半个小时的
//             if (Math.abs(JarrHdata-JoHdata)<=1)
//             {
//                if((Math.abs(JarrMdata-JoMdata)>=29&&Math.abs(JarrMdata-JoMdata)<=30)||(c==jarr.getJSONObject(address).getInt("MIn")&&jarr.getJSONObject(address).getInt("HIn")==0)) 
//                {
//                	//处理半个小时的数据
//               	disposeHours(jo,timeLong);
//               	jssHours=jarr.getJSONObject(address);
//               	System.out.println(jarr.getJSONObject(address).getInt("HIn")+"这是半小时次数");
//               	jssHours.put("HIn",  jarr.getJSONObject(address).getInt("HIn")+1);
//               	//MIn也失去了它的作用
//               	jssHours.put("MIn", 0);
//               	jarr.put(address, jssHours);
//                }
//             }else {
//			  log.debug("丢消息了呀");
//
//             }
//             //读出本地保存的数据
//             dayJs=dayArr.getJSONObject(address);
//             //读出刚开始的时间
//             Date date1=TimeUtil.getCommTime(dayJs.getLong("采集时间"));
//             int Index=(24-TimeUtil.getHour(date1))*2;//当前开始的时间与一天相差的半个小时的数量
//             //判断是不是一天的
//               if((Math.abs(TimeUtil.getHour(date1)-JoHdata)>=23&&Math.abs(TimeUtil.getMinute(date1)-JoMdata)>=55)||jarr.getJSONObject(address).getInt("HIn")==Index)
//               {
//            	   disposeDay(jo,timeLong);
//               }
//          }
//          
//	  }  	
//	}
//	
//	//把五分的数据插入数据库中
//	@Transactional
//	public  void  insertFiveMin(JSONObject js,Long timeLong,int did) throws JSONException {
//		boolean flag=false;
//		wis_device_data wis=new wis_device_data();
//		Date date2=TimeUtil.getCommTime(timeLong);
//        Timestamp timeStamp = new Timestamp(date2.getTime());
//		wis.setCollecttime(timeStamp);
//		wis.setData1(js.toString());
//		wis.setDid(did);
//		wis.setOper(1);
//		wis.setStatus(1);
//		wis.setOpdate(new Timestamp(new Date().getTime()));
//		wis.setRecvtime(new Timestamp(new Date().getTime()));
//		int a=device_dataDao.addWis_device_data(wis);
//		log.info("五分钟的数据插入数据库成功啦"+a);
//	
//	}
//	//把半个小时的数据插入数据库里  先只插入电流吧 
//	public  void insertHalfHour(double value,Long timeLong,int tagid) throws JSONException {
//		boolean  flag=false;
//		wis_light_sumdata_hours wis=new wis_light_sumdata_hours();
//		//统计时间指的是采集时间
//		wis.setReptime(formatter.format(new Date(timeLong)));
//		//这里指的是消耗的电量
//		wis.setSumtype1(1);
//		wis.setVal1(value);
//		//标签ID
//		wis.setTagid(tagid);
//		flag=light_sumdata_hourDao.addWis_light_sumdata_hours(wis);
//		log.info("插入半个小时的数据成功了"+flag);
//	}
//	
//	//把一天的数据插入数据库中 先知插入电流吧
//	public  void insertOneDay(double value,Long time,int tagid) throws JSONException {
//		boolean  flag=false;
//		wis_light_sumdata_days wis=new wis_light_sumdata_days();
//		//统计时间指的是采集时间
//		wis.setReptime(formatter.format(new Date(time)));
//		//这里指的是消耗的电量
//		wis.setSumtype1(1);
//		wis.setVal1(value);
//		wis.setTagid(tagid);
//		flag=light_sumdata_dayDao.addWis_linght_sumdata_dayDao(wis);
//		log.info("一天的数据插入成功了"+flag);
//	}
//	
//	//从数据库里一次把wis_device的数据全部读出来，如果表不会更新就没有问题 更新这个就也要更新了
//	public  void getAllWis_device() {
//		List<wis_device> arr=new  ArrayList<wis_device>();
//		deviceDao.getAllWis_device();
//		arr=deviceDao.getAllWis_device();
//		for(int i=0;i<arr.size();i++) {
//			wis_device wis=new wis_device();
//			wis=arr.get(i);
//           mapDevice.put(wis.getDeviceid(),wis.getId());
//		} 
//	}
//	//根据deviceid 读取出标签id
//	public  void getAllWis_device_tag() {
//		List<wis_device_tag> arr=new ArrayList<>();
//		arr=device_tagDao.getAllWis_device_tag();
//		for(int i=0;i<arr.size();i++) {
//			wis_device_tag wis=new wis_device_tag();
//			wis=arr.get(i);
//			mapTag.put(wis.getDid(),wis.getTagid());
//		}
//	}
//
//	
//	//处理半小时数据的代码
//	public  void disposeHours(JSONObject jo,Long time) throws JSONException {
//	String address=jo.getString("电表地址");
//    JSONObject jssHours=new JSONObject();
//   	 double newValue=jo.getDouble("累计电量");
//   	  JSONObject jsonObject2=(JSONObject) jarr.get(address);
//   	  double oldValue=jsonObject2.getDouble("电量");
//       //把数据顺便s四舍五入 并且只保留小数点后两位
//   	  double value=	(double) Math.round((newValue-oldValue) * 100) / 100;//这个就是半个小时的电量  然后把相关数据插进去
//   	  //插入半个小时的表
//	   int did=mapDevice.get(address);
//	   int tagid=mapTag.get(did);
//   	  insertHalfHour(value,time,tagid);
//   	//把当前数据中
//   	  jssHours=jarr.getJSONObject(address);
//   	   jssHours.put("采集时间",time);
//   	   jssHours.put("电量", jo.get("累计电量"));
//   	   jarr.put(address, jssHours);
//	}
//	
//	
//	//处理一天数据的代码
//    public  void disposeDay(JSONObject jo,Long longtime) throws JSONException {
//    	 JSONObject jssDay=new JSONObject();
//    	 //当前数据的电表地址
//         String address=jo.getString("电表地址");
//    	 //保存在数据库里这是一天的数据
//  	     double newValue=jo.getDouble("累计电量");
//   	     JSONObject jsonObject2=(JSONObject) dayArr.get(address);
//   	     double oldValue=jsonObject2.getDouble("电量");
//   	     //把数据顺便s四舍五入 并且只保留小数点后两位
//      	 double value=	(double) Math.round((newValue-oldValue) * 100) / 100;//这个就是一天的电量  然后把相关数据插进去
// 		int did=mapDevice.get(address);
// 		int tagid=mapTag.get(did);
//   	     insertOneDay(value,longtime,tagid);
//  	     //把数据保存在本地的一天
//   	     jssDay=dayArr.getJSONObject(address);
//   	     jssDay.put("采集时间", longtime);
//   	     jssDay.put("电量", jo.get("累计电量"));
//   	     dayArr.put(address, jssDay);
//    }
//    
//    //把数据拆分之后插到
//    public void changeDevice_curdata(JSONObject jo,Long timeLong,int deviceid) {
//    	int a=0;
//    	wis_device_curdata wis=new wis_device_curdata();
//    	
//    	wis_device_curdata wis_dao=new wis_device_curdata();
//    	
//    	
//    	Date date2=TimeUtil.getCommTime(timeLong);
//    	
//		wis.setDeviceid(deviceid);
//		//这是采集时间
//		wis.setReptime(formatter.format(date2));
//		//操作人  现在我先随便写的
//		wis.setOper(1);
//		wis.setVal1(jo.getDouble("累计电量"));
//         wis.setVal2(jo.getDouble("用户户号"));
//		wis.setVal3(jo.getDouble("当前功率"));
//		wis.setVal4(jo.getDouble("电压"));
//		wis.setVal5(jo.getDouble("电流"));
//		wis.setVal6(jo.getDouble("功率因数"));
//		wis.setVal7(jo.getDouble("无功功率"));
//		wis.setStatus(1);
//		if(jo.has("B当前功率")) {
//			wis.setVal8(jo.getDouble("B当前功率"));
//	        wis.setVal9(jo.getDouble("B电压"));
//			wis.setVal10(jo.getDouble("B电流"));
//			wis.setVal11(jo.getDouble("B功率因数"));
//			wis.setVal12(jo.getDouble("B无功功率"));
//			wis.setVal13(jo.getDouble("C当前功率"));
//	         wis.setVal14(jo.getDouble("C电压"));
//			wis.setVal15(jo.getDouble("C电流"));
//			wis.setVal16(jo.getDouble("C功率因数"));
//			wis.setVal17(jo.getDouble("C无功功率"));	
//		}
//	    
//		//判断数据库里是否已经有了这条数据
//        wis_dao=device_curdataDao.getOneWis_device_curData(deviceid);
//		if(wis_dao==null) {
//		//累计电量
//		wis.setType1(1);
//		//用户户号
//		wis.setType2(2);
//       //当前功率
//		wis.setType3(3);
//       //电压
//		wis.setType4(4);
//      //电流
//		wis.setType5(5);
//      //功率因数
//		wis.setType6(6);
//      //无功功率
//		wis.setType7(7);
//		//B当前功率
//		wis.setType8(8);
////		B电压
//		wis.setType9(9);
////		B电流
//		wis.setType10(10);
////		B功率因数
//		wis.setType11(11);
////		B无功功率
//		wis.setType12(12);
////		C当前功率
//		wis.setType13(13);
////		C电压
//		wis.setType14(14);
////		C电流
//		wis.setType15(15);
////		C功率因数
//		wis.setType16(16);
////		C无功功率
//		wis.setType17(17);
//		wis.setOpdate(new Timestamp(new Date().getTime()));
//		a=device_curdataDao.addWis_device_curdataDao(wis);
//		}else {
//		  a=device_curdataDao.updateWis_device_curdataDao(wis);
//		  System.out.println("这是更新的"+a);
//		}
//		if(a>0) {
//			System.out.println("操作成功啦  ");
//		}
//    }
//	
//	
//		
//}

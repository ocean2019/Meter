//package com.example.util;
//
//
//import java.io.IOException;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Map;
//
//import javax.sound.midi.MidiDevice.Info;
//
//import org.json.JSONException;
//import org.springframework.amqp.core.Message;
//import org.springframework.amqp.rabbit.annotation.RabbitHandler;
//import org.springframework.amqp.rabbit.annotation.RabbitListener;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
//
//import org.springframework.messaging.handler.annotation.Payload;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.example.Interface.WisDeviceService;
//import com.example.Interface.WisDeviceCurdataService;
//import com.example.Interface.WisDeviceDataService;
//import com.example.Interface.WisDeviceTagService;
//import com.example.Interface.WisLightPowerdataHourService;
//import com.example.Interface.WisLightSumdataDayService;
//import com.example.Interface.WisLightSumdataHoursService;
//import com.example.pojo.WisLightPowerdataHourPojo;
//import com.example.util.TimeUtil;
//import com.rabbitmq.client.Channel;
//
//import lombok.extern.slf4j.Slf4j;
//import net.sf.json.JSONObject;
//
//
///**
// * @author zhangkx
// *
// */
//@RestController
//@Slf4j
//@EnableAutoConfiguration
//public class AllController  {
//	//写的很乱 看起来很恶心
//	/**
//	 * 这个是时间格式 所有的字符串时间格式
//	 */
//	private  SimpleDateFormat formatter = new SimpleDateFormat( "yyyyMMddHHmmss");
//	@Autowired
//	private  WisDeviceDataService deviceDataImpl;
//	@Autowired
//	private  WisDeviceTagService deviceTagImpl;
//	@Autowired
//	private  WisLightSumdataHoursService lightSumdataHoursImpl;
//	@Autowired
//	private  WisLightSumdataDayService  lightSumdataDayImpl;
//	@Autowired
//	private WisDeviceService deviceImpl;
//	@Autowired
//	private WisDeviceCurdataService deviceCurdataImpl;
//	@Autowired
//	private WisLightPowerdataHourService lightPowerdataHourImpl;
//
//	
//	
//	/**
//	 * 用来保存所有的设备id
//	 */
//	private static Map<String, Integer> mapDevice=new HashMap<String, Integer>();
//	/**
//	 * 用来保存所有的标签id
//	 */
//	private static Map<Integer, Integer> mapTag=new HashMap<Integer, Integer>();
//	/**
//	 * 保存读到的电表地址 数据等
//	 */
//	private static JSONObject jarr=new JSONObject();
//	/**
//	 * 保存一天开始的最初的数据
//	 */
//	private static JSONObject dayArr=new JSONObject();
//	/**
//	 * 读取本地保存的时间的分钟发数
//	 */
//	private static int JarrMdata;
//	/**
//	 * 读取本地保存时间的小时数
//	 */
//	private static int JarrHdata;
//	/**
//	 * 读取当前数据的时间的分钟发数
//	 */
//	private static int JoMdata;
//	/**
//	 * 读取当前数据的时间的小时数
//	 */
//	private static int JoHdata;
//	/**
//	 * 读取功率的小时数
//	 */
//	private static int GHdata;
//	/**
//	 * 读取功率的分钟数
//	 */
//	 private static int GMdata;
//	/**
//	 * 分钟的记录数
//	 */
//	private static int MIndex=0;
//	//这个没什么用
//   private static int MS;
//   
//     private static JSONObject fiveTime=new JSONObject(); 
//   
//	/**
//	 * 当前接收消息的分钟的时间间隔
//	 */
//	@Value("${allcontroller.MSIndex}")
//	private  int MSIndex;
//	//用@Value方法给静态变量赋值时 加在它的set方法上就好了
//	@Value("${allcontroller.MSIndex}")
//	//用this是可以的。。不用this也是可以的和this 没有很多关系
//	public  void setMS(int mS) {
//	 MS = mS;
//	}
//
//	//小时的记录数
//    private static int HIndex=0;
//	private static  int MTime=0;
//	private static   int c=0;
//	private static int d=0;
//	
//	
//	/**
//	 * show 这个是监听队列的方法 并对数据进行加工
//	 * @param js  接收到的信息String 
//	 * @param channel  通道
//	 * @param message   
//	 * @throws JSONException
//	 * @throws IOException
//	 */
//	@RabbitListener(queues="one")
//	@RabbitHandler
//	@Transactional
//	public void getMessage(@Payload String js,Channel channel,Message message) throws JSONException, IOException {
// try {
//	            //告诉服务器收到这条消息 已经被我消费了 可以在队列删掉 这样以后就不会再发了 否则消息服务器以为这条消息没处理掉 后续还会在发
//			 JSONObject jsonObject=JSONObject.fromObject(js);	 
//			  //获取所有wis_device的id和设备id数据
//			  mapDevice=deviceImpl.getAllWisDevice();
//			  //获取所有wis_device_tag的tagid和did数据
//			//以后如果有更新这两个表的操作一定要重新调用这两个方法 然后再保存
//			  mapTag=deviceTagImpl.getAllWisDeviceTag(); 
//			  //获取采集时间
//			  Long timeLong= Long.parseLong(jsonObject.getString("采集时间"))*1000;
//			  //把获得的数据进行拆分
//			  for(int i=1;i<=jsonObject.size()-4;i++) 
//			  {   //当前读取到的对象
//		          JSONObject jo=new JSONObject();
//		          JSONObject jssHours=new JSONObject();
//		          //判断是不是一天的数据
//		          JSONObject dayJs=new JSONObject();
//		          if(i<=9)
//		          {
//		                 jo=JSONObject.fromObject(jsonObject.get("用户0"+i)) ;
//		          }else {
//				          jo=JSONObject.fromObject(jsonObject.getJSONObject("用户"+i));
//			            }	
//		          
//		          
//		          
//		    	  String address=jo.get("电表地址").toString();
//		    	  //把五分钟接收的数据插入五分钟接收的表里
//		  		  //获取设备ID 根据电表地址
//		  		  int did=mapDevice.get(address); 
//		  		  if(!fiveTime.has(address)) {
//		  			  fiveTime.put(address,timeLong);
//		  		  }else {
//		  			  if(timeLong-fiveTime.getLong(address)>3000000) {
//		  				  Long aLong=timeLong-fiveTime.getLong(address);
//		  				  log.info("差值是"+aLong);
//		  				  log.info("你丢消息了呀");
//		  			  }
//		  			  fiveTime.put(address,timeLong);
//
//		  		  }
//		  		  //重复消息不太可能吧  感觉
//               deviceDataImpl.insertFiveMin(jo,timeLong,did);
//		          //把数据拆分插入表CurdataDao
//		            deviceCurdataImpl.changeDeviceCurdata(jo,timeLong,did);
//		            //只是把第一条数据当作了初始数
//		            log.info("这是我记录的第一条信息");
//		          if(!jarr.has(address))
//		          {   
//		              //判断保存时间和半个小时的差值是取一个相对近的默认值
//		               if(c==0) {
//		            	 //如果C==0它就会一直以为是一半个小时 其实应该为1；
//		            	   c=1;
//		               }
//		               //功率是一小时统计一次 所以我和电量的采集时间区别一下，但是值是一样的
//		               jssHours.put("功率时间",timeLong);
//		               jssHours.put("采集时间",timeLong);
//		               jssHours.put("电量", jo.get("累计电量"));
//		               jssHours.put("MIn",MIndex);
//		               jssHours.put("HIn",HIndex);
//		               //功率的次数 就是第一次要用的东西
//		               jssHours.put("GIn",0);
//		               jarr.put(address, jssHours);
//		               //半个小时可能就会少的是十分钟的数据 但是下一个30分钟会补上这个量所以问题不大把。
//		               //一天的初始化值
//		               dayJs.put("采集时间",timeLong);
//		               dayJs.put("电量", jo.get("累计电量"));
//		               dayJs.put("HIn", 0);
//		               dayArr.put(address, dayJs);
//		          }else{
//		        	  
//                      //次数增加
//		        	  jssHours=jarr.getJSONObject(address);
//		              jssHours.put("MIn", jarr.getJSONObject(address).getInt("MIn")+1);
//		         	  jarr.put(address, jssHours);
//		     
//		       	      Date date=TimeUtil.getCommTime(timeLong);
//                      //读取当前数据的分钟数
//		              JoMdata=TimeUtil.getMinute(date);
//		              //读取当前数据的小时数
//		              JoHdata=TimeUtil.getHour(date);
//		              
//		              Date date2=TimeUtil.getCommTime(jarr.getJSONObject(address).getLong("采集时间"));
//		              //读取本地的小时数
//		              JarrHdata=TimeUtil.getHour(date2);
//		              //读取本地时间的分钟数
//		              JarrMdata=TimeUtil.getMinute(date2);
//		        	  //算算次数  这应该JarrHdata  Jo的话应该永远是6次吧
//		              //妈耶 绕进去了
//		              if(JarrMdata>=30) {
//		        		  MTime=60-JarrMdata;
//		        	  }else {
//		        		  MTime=30-JarrMdata;  
//		        	  }
//		              c=MTime/MSIndex;  
////		              //分钟数这么判断对不对呢 之后可能会丢数据这么去判断还可以嘛
////		              log.info("这是————————————————————————————————————");
////		              log.info("这是差值"+Math.abs(JarrHdata-JoHdata));
////		              System.out.println(Math.abs(JarrMdata-JoMdata)+"为什么啊 应该是30不是么");
////                    本地保存的都是半个小时的时间 你真蠢  
//		              //判断是不是一个小时的
//		              judgeHour(jo,timeLong,address);
//		            //判断是否是半小时的
//		            judgeHalfHour(jo, timeLong, address, date2);
//		            //判断是否是一天
//		             judgeDate(address, jo, timeLong);
//		          }  
//			  }
//			    channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
//	            System.out.println("receiver success");
//	        } catch (IOException e) {
//	            e.printStackTrace();
//	            //丢弃这条消息
//	            //channel.basicNack(message.getMessageProperties().getDeliveryTag(), false,false);
//	            System.out.println("receiver fail");
//	        }
//	 
//	    }
//	
//	/**
//	 * show  判断是否是一个小时的方法(功率)
//	 * @param jo  接受的一户用户的数据
//	 * @param timeLong 采集时间
//	 * @param address  电表地址
//	 */
//	public void judgeHour(JSONObject jo,Long timeLong,String address) {
//		JSONObject jssHours=new JSONObject();
//		 //读取本地保存的分钟数
//        Date date3=TimeUtil.getCommTime(jarr.getJSONObject(address).getLong("功率时间"));
//        GHdata=TimeUtil.getHour(date3);
//        GMdata=TimeUtil.getMinute(date3);
//        //前一个功率里保存的时间 和 当前数据的时间  小时的差值
//        int joHGH=Math.abs(JoHdata-GHdata);
//        //前一个功率里保存的时间 和 当前数据的时间  分钟的差值
//        int joMGM=Math.abs(JoMdata-GMdata);
//        boolean flag=joHGH==1||joHGH==23&&joMGM<=5;
//        if(flag) {
//      //调用处理功率的方法
//         log.info("------功率------");
//         log.info("这是一天开始的时间-"+formatter.format(date3));
//      	  disposeHours(jo,timeLong,address);
//      	 log.info("这是一天结束的时间-"+formatter.format(new Date(timeLong)));
//         log.info("------功率----");
//      	  jssHours=jarr.getJSONObject(address);
//      	  jssHours.put("功率时间", timeLong);
//      	  jarr.put(address,jssHours);
//        }
//	}
//	
//	/**
//	 * 处理一个小时功率数据的方法(功率)
//	 * @param jo  当前接收的一户的数据JSON对象
//	 * @param time  采集时间
//	 * @param address  电表地址
//	 */
//	public void disposeHours(JSONObject jo,Long time,String address) {
//		//插入半个小时的表
//		   int did=mapDevice.get(address);
//		   int tagid=mapTag.get(did);
//		   WisLightPowerdataHourPojo wis=new WisLightPowerdataHourPojo();
//		   double value=0;
//		   double value1=0;
//		   double value2=0;
//		   double value3 = 0;
//		   wis.setType2(2);
//		   value1=jo.getDouble("当前功率");
//		   wis.setVal2(value1);
//		   wis.setType3(3);
//		   wis.setVal3(jo.getDouble("无功功率"));
//		   if(jo.containsKey("B当前功率")) {
//			   wis.setType4(4);
//			   value2=jo.getDouble("B当前功率");
//			   wis.setVal4(value2);
//			   wis.setType5(5);
//			   wis.setVal5(jo.getDouble("B无功功率"));
//			   wis.setType6(6);
//			   value3=jo.getDouble("C当前功率");
//			   wis.setVal6(value3);
//			   wis.setType7(7);
//			   wis.setVal7(jo.getDouble("C无功功率"));
//		   }
//		   wis.setReptime(new Date(time));
//		   value1=(double) Math.round((value1) * 100) /100;
//		   value2=(double) Math.round((value2) * 100) /100;
//		   value3=(double) Math.round((value3) * 100) /100;
//		   value=(double) Math.round((value1+value2+value3) * 100) /100;
//		   wis.setType1(1);
//		   wis.setVal1(value);
//		   wis.setTagid(tagid);
//		   wis.setOper(1);
//		int a=lightPowerdataHourImpl.insertWisLightPowerdataHour(wis);
//		System.out.println(a);
//	}
//	
//	
//	//
//	/**
//	 * show 判断是否是一天的方法（电量）
//	 * @param address 电表地址
//	 * @param jo  当前数据中一户数据的JSON对象
//	 * @param timeLong  采集时间
//	 */
//	public void judgeDate(String address,JSONObject jo,Long timeLong) {
//        JSONObject dayJs=new JSONObject();
//        JSONObject jssHours=new JSONObject();
//		  //读出本地保存的数据
//	    dayJs=dayArr.getJSONObject(address);
//	    //读出刚开始的时间
//	    Date date1=TimeUtil.getCommTime(dayJs.getLong("采集时间"));
//	  //当前开始的时间与一天相差的半个小时的数量
//	    int index=(24-TimeUtil.getHour(date1))*2;
//	    //当前时间和保存时间小时的差值
//	    int dayHjoH=Math.abs(TimeUtil.getHour(date1)-JoHdata);
//	    //判断是不是一天的
//	    boolean flagDay=(dayHjoH>=23&&dayHjoH>=58)||jarr.getJSONObject(address).getInt("HIn")==index;
//	      if(flagDay)
//	      {
//	   	   disposeDay(jo,timeLong);
//	   		jssHours=jarr.getJSONObject(address);
//	        jssHours.put("HIn", 0);
//	        jarr.put(address, jssHours);
//	      }
//	}
//	
//	//
//	/**
//	 * 
//	 * show  判断是不是半个小时的方法（电量）
//	 * @param jo 当前接受的消息里一户的JSON对象
//	 * @param timeLong  采集时间
//	 * @param address  电表地址
//	 * @param date2
//	 */
//	public void judgeHalfHour(JSONObject jo,Long timeLong,String address,Date date2) {
//		JSONObject jssHours=new JSONObject();
//		//本地保存的电量时间和 当前数据的时间的 小时的差值
//		int jarHJoH=Math.abs(JarrHdata-JoHdata);
//		//本地保存电量时间 和当前数据时间的 分钟差值
//		int jarMJoM=Math.abs(JarrMdata-JoMdata);
//		//不是23的可能性太小了  判断小时差值是否《1 或者等于23
//		boolean flagH=jarHJoH<=1||jarHJoH==23;
//		//判断分钟的差值 在一分钟之内
//		boolean flagM=(jarMJoM>=29&&jarMJoM<=30)||(c==jarr.getJSONObject(address).getInt("MIn")&&jarr.getJSONObject(address).getInt("HIn")==0);
//		 if (flagH)
//         {
//            if(flagM)
//            {
//            //处理半个小时数据的方法
//           	disposeHalfHours(jo,timeLong);
//           	jssHours=jarr.getJSONObject(address);
//           	jssHours.put("HIn",  jarr.getJSONObject(address).getInt("HIn")+1);
//           	//MIn也失去了它的作用
//           	jssHours.put("MIn", 0);
//           	jarr.put(address, jssHours);
//            }
//         }else if(Math.abs(JarrHdata-JoHdata)>1){
//		  log.debug("丢消息了呀");
//			  System.out.println("你是不是运行了啊");
//		  //如果相差的数据大于一个小时
//		  int mm=jarMJoM/30;
//		  //是HCount半个小时后的数据
//		  int hCount=jarHJoH+mm;
//		  System.out.println("这是HCount"+hCount);
//		  //当地保存时间的长时间
//		  Long oldTime=date2.getTime();
//		  //差多少的长时间到达下一个整半小时
//		  Long  addTime=1800000-oldTime%1800000;
//		  //这就是循环开始的第一条数据的开始时间点
//		  Long newTime=oldTime+addTime;
//		  //跟之前一样 把有多少个半个小时 时间记录点， 对象传入就好了
//		  disposeHalfHours2(jo,newTime, hCount);
//		  //上面方法好像可以的
//         }
//	}
//	
//	
//	//
//	
//	/**
//	 * show 处理半小时数据的代码
//	 * @param jo  当前消息里接收一户的数据的JSON对象
//	 * @param time 采集时间
//	 * @throws JSONException
//	 */
//	public  void disposeHalfHours(JSONObject jo,Long time) throws JSONException {
//	String address=jo.getString("电表地址");
//    JSONObject jssHours=new JSONObject();
//   	 double newValue=jo.getDouble("累计电量");
//   	 log.info("----------这是半小时------------------");
//   	 log.info("这是newvalue："+newValue);
//   	 JSONObject jsonObject2=(JSONObject) jarr.get(address);
//   	  double oldValue=jsonObject2.getDouble("电量");
//   	  log.info("这是oldValue:"+oldValue);
//       //把数据顺便s四舍五入 并且只保留小数点后两位
//   	  double value=	(double) Math.round((newValue-oldValue) * 100) / 100;//这个就是半个小时的电量  然后把相关数据插进去
//   	   log.info("这是value:"+value);
//   	   log.info("这是保存的时间："+new Date(time));
//   	   log.info("---------这是半小时的结束-------------------------");
//   	  //插入半个小时的表
//	   int did=mapDevice.get(address);
//	   int tagid=mapTag.get(did);
//   	  lightSumdataHoursImpl.insertHalfHour(value,time,tagid);
//   	//把当前数据中
//   	  jssHours=jarr.getJSONObject(address);
//   	   jssHours.put("采集时间",time);
//   	   jssHours.put("电量", jo.get("累计电量"));
//   	   jarr.put(address, jssHours);
//	}
//	
//
//	/**
//	 * show 如果是多个半个小时的处理方法
//	 * @param jo
//	 * @param time
//	 * @param HCount
//	 * @throws JSONException
//	 */
//	public  void disposeHalfHours2(JSONObject jo,Long time,int hCount) throws JSONException {
//	String address=jo.getString("电表地址");
//	int did=mapDevice.get(address);
//	int tagid=mapTag.get(did);
//    JSONObject jssHours=new JSONObject();
//   	double newValue=jo.getDouble("累计电量");
//   log.info("--------------这是特殊的半小时------------------------");
//   	JSONObject jsonObject2=(JSONObject) jarr.get(address);
//   	double oldValue=jsonObject2.getDouble("电量");
//	log.info("这是oldValue:"+oldValue);
//	log.info("这是newVlaue:"+newValue);
//       //把数据顺便s四舍五入 并且只保留小数点后两位  保存在半小时里的值
//	//这个就是半个小时的电量  然后把相关数据插进去
//   	double value=(double) Math.round((newValue-oldValue) * 100) /100;
//   	log.info("这是value："+value);  
//   	//这个方法应该不会用到一般 先写一下 
//   	 for(int i=0;i<hCount;i++) {
//   	  lightSumdataHoursImpl.insertHalfHour((double) Math.round((value/hCount) * 100) /100,time,tagid);
//   	  
//   	  log.info("这是保存的时间"+formatter.format(new Date(time)));
//       jssHours=jarr.getJSONObject(address);
//	   jssHours.put("采集时间",time);
//	   jssHours.put("电量", jo.get("累计电量"));
//	   //又增加了HCount次的半个小时的电量
//	   jssHours.put("HIn",  jarr.getJSONObject(address).getInt("HIn")+1);
//     //MIn也失去了它的作用
//   	  jssHours.put("MIn", 0);
//   	  jarr.put(address, jssHours);
//   	  //判断加上这半小时后是不是达到了一天
//   	judgeDate(address,jo,time);
//   	time=time+1800000;
//   	 }   	  
//}
//	
//    /**
//     * show 处理一天数据的代码
//     * @param jo
//     * @param longtime
//     * @throws JSONException
//     */
//    public  void disposeDay(JSONObject jo,Long longtime) throws JSONException {
//    	
//    	 JSONObject jssDay=new JSONObject();
//    	 //当前数据的电表地址
//         String address=jo.getString("电表地址");
//    	 //保存在数据库里这是一天的数据
//  	     double newValue=jo.getDouble("累计电量");
//  	     log.info("------------一天的开始---------------------");
//  	     log.info("保存的时间是："+new Date(longtime-300000));
//  		 log.info("这是newvalue："+newValue);
//   	     JSONObject jsonObject2=(JSONObject) dayArr.get(address);
//   	     double oldValue=jsonObject2.getDouble("电量");
//         log.info("这是oldValue："+oldValue);
//   	     //把数据顺便s四舍五入 并且只保留小数点后两位
//       //这个就是一天的电量  然后把相关数据插进去
//      	 double value=	(double) Math.round((newValue-oldValue) * 100) / 100;
//      	 log.info("这是value:"+value);
//      	 log.info("--------一天的结束------------------------");
// 		 int did=mapDevice.get(address);
// 		 int tagid=mapTag.get(did);
//   	     lightSumdataDayImpl.insertOneDay(value,longtime,tagid);
//  	     //把数据保存在本地的一天
//   	     jssDay=dayArr.getJSONObject(address);
//   	     jssDay.put("采集时间", longtime);
//   	     jssDay.put("电量", jo.get("累计电量"));
//   	     dayArr.put(address, jssDay);
//    }
//		
//}

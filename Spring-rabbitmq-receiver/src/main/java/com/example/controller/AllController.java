package com.example.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

import com.example.Interface.WisDeviceService;
import com.example.Interface.WisDeviceCurdataService;
import com.example.Interface.WisDeviceDataService;
import com.example.Interface.WisDeviceTagService;
import com.example.Interface.WisLightPowerdataHourService;
import com.example.Interface.WisLightSumdataDayService;
import com.example.Interface.WisLightSumdataHoursService;
import com.example.pojo.WisDeviceDataPojo;
import com.example.pojo.WisLightPowerdataHourPojo;
import com.example.util.DoubleRound;
import com.example.util.TimeUtil;
import com.rabbitmq.client.Channel;

import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;

/**
 * @author zhangkx
 *
 */
/**
 * @author zhangkx
 *
 */
@Slf4j
@EnableScheduling
@RestController("AllController")
public class AllController  {
	//写的很乱 看起来很恶心
	/**
	 * 这个是时间格式 所有的字符串时间格式
	 */
	private  SimpleDateFormat formatter = new SimpleDateFormat( "yyyyMMddHHmmss");
    @Autowired
	private  WisDeviceDataService deviceDataImpl;
	@Autowired
	private  WisDeviceTagService deviceTagImpl;
	@Autowired
	private  WisLightSumdataHoursService lightSumdataHoursImpl;
	@Autowired
	private  WisLightSumdataDayService  lightSumdataDayImpl;
	@Autowired
	private WisDeviceService deviceImpl;
	@Autowired
	private WisDeviceCurdataService deviceCurdataImpl;
	@Autowired
	private WisLightPowerdataHourService lightPowerdataHourImpl;
	
	/**
	 * 一个公共对象WisDeviceDataPojo
	 */
	private  List<WisDeviceDataPojo> arr=new ArrayList<>();
	/**
	 * 一个公共对象WisLightPowerdataHourPojo
	 */
	 private  List<WisLightPowerdataHourPojo> arr1=new ArrayList<>();
	/**
	 * 用来保存所有的设备id
	 */
	private static  Map<String, Integer> mapDevice=new HashMap<String, Integer>();
	/**
	 * 用来保存所有的标签id
	 */
	private static  Map<Integer, Integer> mapTag=new HashMap<Integer, Integer>();
	/**
	 * 保存读到的电表地址 数据等 这是保存半个小时的
	 */
	private static  JSONObject jarr=new JSONObject();
	/**
	 * 保存一天开始的最初的数据  这是保存一天的
	 */
	private static  JSONObject dayArr=new JSONObject();
	/**
	 * 保存分钟级的数据 可以是任意分钟数
	 */
	private static  JSONObject minarr=new JSONObject();
	/**
	 * 读取本地保存的时间的分钟发数
	 */
	private static int JarrMdata;
	/**
	 * 读取本地保存时间的小时数
	 */
	private static int JarrHdata;
	/**
	 * 读取当前数据的时间的分钟发数
	 */
	private static int JoMdata;
	/**
	 * 读取当前数据的时间的小时数
	 */
	private static int JoHdata;
	/**
	 * 读取功率的小时数
	 */
	private static int GHdata;
	/**
	 * 读取功率的分钟数
	 */
	 private static int GMdata;
	/**
	 * 分钟的记录数
	 */
	private static int MIndex=0;
	//这个没什么用
   private static int MS;   
	/**
	 * 当前接收消息的分钟的时间间隔
	 */
	@Value("${allcontroller.MSIndex}")
	private  int MSIndex;
	//用@Value方法给静态变量赋值时 加在它的set方法上就好了
	@Value("${allcontroller.MSIndex}")
	//用this是可以的。。不用this也是可以的和this 没有很多关系
	public  void setMS(int mS) {
	 MS = mS;
	}
	
	/**
	 * 一次接收多少数据 一次插入数量
	 */
	@Value("${allcontroller.count}")
	private int count;
	
	/**
	 * 一次接收多少数据  一次更新数量
	 */
	@Value("${allcontroller.upCount}")
	private int upCount;
	//小时的记录数
    private static int HIndex=0;
	private static  int MTime=0;
	private static   int c=0;
	/**
	 * 存储WisDeviceData  arrSize的值
	 */
	private static int newDeviceDataArrSize=0;
	/**
	 * 老的值arrSize
	 */
	private static int oldDeviceDataArrSize=0;
	/**
	 * 存储WisDeviceData  arrSize的值
	 */
	private static int newLightPowerdataHourArrSize=0;
	/**
	 * 老的值arrSize
	 */
	private static int oldLightPowerdataHourArrSize=0;
	
	/**
	 * show 这个是监听队列的方法 并对数据进行加工
	 * @param js  接收到的信息String 
	 * @param channel  通道
	 * @param message   
	 * @throws JSONException
	 * @throws IOException
	 */
//    @RabbitListener(queues="one")
   @RabbitListener(queues="wis_collector")
    @Transactional
	public void getMessage(@Payload JSONObject js,Channel channel,Message message) throws JSONException, IOException {
 try {
	        //告诉服务器收到这条消息 已经被我消费了 可以在队列删掉 这样以后就不会再发了 否则消息服务器以为这条消息没处理掉 后续还会在发  
	        JSONObject jsonObject=JSONObject.fromObject(js);	 
			  //获取所有wis_device的id和设备id数据
			  mapDevice=deviceImpl.getAllWisDevice();
			  //获取所有wis_device_tag的tagid和did数据
			//以后如果有更新这两个表的操作一定要重新调用这两个方法 然后再保存
			  mapTag=deviceTagImpl.getAllWisDeviceTag(); 
			  //获取采集时间
			  Long timeLong= Long.parseLong(jsonObject.getString("采集时间"))*1000;
			  //把获得的数据进行拆分
			  JSONObject jo=new JSONObject();
//              用来保存临时一小时的数据
              JSONObject jssHours=new JSONObject();
              int jsonSize=jsonObject.size()-4;
			  for(int i=1;i<=jsonSize;i++) 
			  {   //当前读取到的对象
		          if(i<=9)
		          {
		                 jo=JSONObject.fromObject(jsonObject.get("用户0"+i)) ;
		          }else {
				          jo=JSONObject.fromObject(jsonObject.getJSONObject("用户"+i));
			            }	
		        String address=jo.get("电表地址").toString();
		  		  //获取设备ID 根据电表地址
		  		int did=mapDevice.get(address); 
		  		 //分钟级本地数据初始化 jarr
		  		initMinarr(address,jo,timeLong);
                 //分钟级消息的批量插入 
		  		batchInsert(jo, did,timeLong);
		          //把数据拆分插入表CurdataDao
		        deviceCurdataImpl.changeDeviceCurdata(jo,timeLong,did);
		          //判断此地址本地是否有保存信息
		          if(!jarr.has(address))
		          {   
		        	  //如果是初次消息
		        	  newMessage(jo,address,timeLong);
		          }else{
		        	  //如果不时初次的消息
		        	  oldChange(jo,address,timeLong);
		          } 
			  }
			    channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
	        } catch (IOException e) {
	            e.printStackTrace();
	            //丢弃这条消息
	            //channel.basicNack(message.getMessageProperties().getDeliveryTag(), false,false);
	            System.out.println("receiver fail");
	        }
	    }
  
	/**
	 * show  不是初次接收到该地址的消息
	 * @param jo  接受的一户用户的数据
	 * @param timeLong 采集时间
	 * @param address  电表地址
	 * @param x 批量处理的数据量
	 */   
  public void batchInsert(JSONObject jo,int did,Long timeLong) {
	  if(jo==null&&did==0&&timeLong==0) {
		  //注意静态变量和线程的关系
		  List<WisDeviceDataPojo> arr2=new ArrayList<>(arr);
		  deviceDataImpl.insertFiveMin(arr2);
		  arr.clear();
		  newDeviceDataArrSize=0;
	  }else {
	  Date date2=TimeUtil.getCommTime(timeLong);
      Timestamp timeStamp = new Timestamp(date2.getTime());
      WisDeviceDataPojo wis=new WisDeviceDataPojo();
	      wis.setData1(jo.toString());
	      wis.setDid(did);
	      wis.setCollecttime(timeStamp);
		  arr.add(wis);
		 newDeviceDataArrSize=arr.size();
		 if(arr.size()>=count) {
	     List<WisDeviceDataPojo> arr2=new ArrayList<>(arr);
         deviceDataImpl.insertFiveMin(arr2);
         arr.clear();
		   }
	  }
   }
//    @Scheduled(cron = "0 0/1 * * * ?") // 每1分钟执行一次
     @Scheduled(fixedRate =10000)  //每10秒执行一次
	public void judgeTime() {
    	boolean flag1=newDeviceDataArrSize==oldDeviceDataArrSize&&newDeviceDataArrSize!=0;
    	boolean flag2=newLightPowerdataHourArrSize==oldLightPowerdataHourArrSize&&newLightPowerdataHourArrSize!=0;
		if(flag1) {
			oldDeviceDataArrSize=0;
			batchInsert(null,0,0L);
		}
		if(flag2) {
			
			oldLightPowerdataHourArrSize=0;
			disposeHours(null,0L,null);
		}
		oldLightPowerdataHourArrSize=newLightPowerdataHourArrSize;
		oldDeviceDataArrSize=newDeviceDataArrSize;
		log.info("我运行了 ");
	}
  
  
	/**
	 * show  不是初次接收到该地址的消息
	 * @param jo  接受的一户用户的数据
	 * @param timeLong 采集时间
	 * @param address  电表地址
	 */
  public void initMinarr(String address,JSONObject jo,Long timeLong) {
	  JSONObject fiveTime=new JSONObject();
		 if(!minarr.has(address)) {
//           用来保存临时5分钟的数据
 			  fiveTime.put("采集时间",timeLong);
 			  fiveTime.put("电量",jo.getDouble("累计电量"));
 			  minarr.put(address,fiveTime);
 		  }else {
 			  if(timeLong-minarr.getJSONObject(address).getLong("采集时间")>MSIndex*60000) {
// 				  Long aLong=timeLong-minarr.getJSONObject(address).getLong("采集时间");
// 				  log.info("差值是"+aLong);
 				  log.info("你丢消息了呀");
 			  }
 		    fiveTime.put("采集时间",timeLong);
 			fiveTime.put("电量",jo.getDouble("累计电量"));
 			minarr.put(address,fiveTime);
 		  }
  }
  
	/**
	 * show  不是初次接收到该地址的消息
	 * @param jo  接受的一户用户的数据
	 * @param timeLong 采集时间
	 * @param address  电表地址
	 */
  public void newMessage(JSONObject jo,String address,Long timeLong) {
	//这个只有第一次才运行
//      if(c==0) {
//   	 //如果C==0它就会一直以为是一半个小时 其实应该为1;
//   	   //这说的是下面那种情况里 如果不这么写 如果不这么写就不会取整2
//   	   c=1;
//      }  这个应该没有
      //功率是一小时统计一次 所以我和电量的采集时间区别一下，但是值是一样的
      JSONObject jssHours=new JSONObject();
      jssHours.put("功率时间",timeLong);
      jssHours.put("采集时间",timeLong);
      jssHours.put("电量", jo.get("累计电量"));
      jssHours.put("MIn",MIndex);
      jssHours.put("HIn",HIndex);
      //功率的次数 就是第一次要用的东西
      jssHours.put("GIn",0);
      jarr.put(address, jssHours);
      //半个小时可能就会少的是十分钟的数据 但是下一个30分钟会补上这个量所以问题不大把。
//    用来临时保存一天的数据
   JSONObject dayJs=new JSONObject();
      dayJs.put("采集时间",timeLong);
      dayJs.put("电量", jo.get("累计电量"));
      dayJs.put("HIn", 0);
      dayArr.put(address, dayJs);
  }
  
  
	/**
	 * show  不是初次接收到该地址的消息
	 * @param jo  接受的一户用户的数据
	 * @param timeLong 采集时间
	 * @param address  电表地址
	 */
  public void  oldChange(JSONObject jo,String address,Long timeLong) {
//	  用来保存临时一小时的数据
      JSONObject jssHours=new JSONObject();
	  //次数增加
	  jssHours=jarr.getJSONObject(address);
      jssHours.put("MIn", jarr.getJSONObject(address).getInt("MIn")+1);
 	  jarr.put(address, jssHours);
	      Date date=TimeUtil.getCommTime(timeLong);
      //读取当前数据的分钟数
      JoMdata=TimeUtil.getMinute(date);
      //读取当前数据的小时数
      JoHdata=TimeUtil.getHour(date);
      Date date2=TimeUtil.getCommTime(jarr.getJSONObject(address).getLong("采集时间"));
      //读取本地的小时数
      JarrHdata=TimeUtil.getHour(date2);
      //读取本地时间的分钟数
      JarrMdata=TimeUtil.getMinute(date2);
      //jarr保存的是半个小时的值  算的是 之前保存的时间 到 下一个整30分钟之间需要多少条分钟级数据
	  //算算次数  这应该JarrHdata 
      if(JarrMdata>=30) {
		  MTime=60-JarrMdata;
	  }else {
		  MTime=30-JarrMdata;  
	  }
      c=MTime/MSIndex;  
      //判断是不是一个小时的
      judgeHour(jo,timeLong,address);
    //判断是否是半小时的
    judgeHalfHour(jo,timeLong,address, date2);
    //判断是否是一天
     judgeDate(address, jo, timeLong);
  }
  
	/**
	 * show  判断是否是一个小时的方法(功率)
	 * @param jo  接受的一户用户的数据
	 * @param timeLong 采集时间
	 * @param address  电表地址
	 */
	public void judgeHour(JSONObject jo,Long timeLong,String address) {
		
		 //读取本地保存的分钟数
        Date date3=TimeUtil.getCommTime(jarr.getJSONObject(address).getLong("功率时间"));
        GHdata=TimeUtil.getHour(date3);
        GMdata=TimeUtil.getMinute(date3);
        //前一个功率里保存的时间 和 当前数据的时间  小时的差值
        int joHGH=Math.abs(JoHdata-GHdata);
        //前一个功率里保存的时间 和 当前数据的时间  分钟的差值
        int joMGM=Math.abs(JoMdata-GMdata);
        boolean flag=joHGH==1||joHGH==23&&joMGM<=MSIndex;
        if(flag) {
      //调用处理功率的方法
      	  disposeHours(jo,timeLong,address);
         JSONObject jssHours=new JSONObject();
      	  jssHours=jarr.getJSONObject(address);
      	  jssHours.put("功率时间", timeLong);
      	  jarr.put(address,jssHours);
        }
	}
	
	/**
	 * 处理一个小时功率数据的方法(功率)
	 * @param jo  当前接收的一户的数据JSON对象
	 * @param time  采集时间
	 * @param address  电表地址
	 */
	public void disposeHours(JSONObject jo,Long time,String address) {
		 if(jo==null&&address==null&&time==0) {
			  //注意静态变量和线程的关系
			  List<WisLightPowerdataHourPojo> arr2=new ArrayList<>(arr1);
			  System.out.println(arr2);
			  lightPowerdataHourImpl.insertWisLightPowerdataHour(arr2);
			  arr1.clear();
			  newLightPowerdataHourArrSize=0;
		  }else {
		//插入半个小时的表
		   int did=mapDevice.get(address);
		   int tagid=mapTag.get(did);
		   double value=0;
		   double value1=0;
		   double value2=0;
		   double value3 = 0;
		   WisLightPowerdataHourPojo wis=new WisLightPowerdataHourPojo();
		   wis.setType2(2);
		   value1=jo.getDouble("当前功率");
		   wis.setVal2(value1);
		   wis.setType3(3);
		   wis.setVal3(jo.getDouble("无功功率"));
		   if(jo.containsKey("B当前功率")) {
			   wis.setType4(4);
			   value2=jo.getDouble("B当前功率");
			   wis.setVal4(value2);
			   wis.setType5(5);
			   wis.setVal5(jo.getDouble("B无功功率"));
			   wis.setType6(6);
			   value3=jo.getDouble("C当前功率");
			   wis.setVal6(value3);
			   wis.setType7(7);
			   wis.setVal7(jo.getDouble("C无功功率"));
		   }
		   wis.setReptime(new Date(time));
//		   value1=DoubleRound.getValeLatterTwo(value1);
//		   value2=DoubleRound.getValeLatterTwo(value2);
//		   value3=DoubleRound.getValeLatterTwo(value3);
		   value=DoubleRound.getValeLatterTwo(value1+value2+value3);
		   wis.setVal1(value);
		   wis.setTagid(tagid);
		   wis.setOper(1);
		   arr1.add(wis);
		   newLightPowerdataHourArrSize=arr1.size();
		   if(arr1.size()>=upCount) {
			   List<WisLightPowerdataHourPojo> arr2=new ArrayList<>(arr1);
			   lightPowerdataHourImpl.insertWisLightPowerdataHour(arr2);
		       System.gc();
		       arr1.clear();
		   }
		}	
	}
	//
	/**
	 * show 判断是否是一天的方法（电量）
	 * @param address 电表地址
	 * @param jo  当前数据中一户数据的JSON对象
	 * @param timeLong  采集时间
	 */
	public void judgeDate(String address,JSONObject jo,Long timeLong) {
        JSONObject dayJs=new JSONObject();
		  //读出本地保存的数据
	    dayJs=dayArr.getJSONObject(address);
	    //读出刚开始的时间
	    Date date1=TimeUtil.getCommTime(dayJs.getLong("采集时间"));
	  //当前开始的时间与一天相差的半个小时的数量
	    int index=(24-TimeUtil.getHour(date1))<<1;
	    //当前时间和保存时间小时的差值
	    int dayHjoH=Math.abs(TimeUtil.getHour(date1)-JoHdata);
	    int dayMjoM=Math.abs(TimeUtil.getMinute(date1)-JoMdata);
	    //判断是不是一天的
	    boolean flagDay=(dayHjoH>=23&&dayMjoM>=58)||jarr.getJSONObject(address).getInt("HIn")==index;
	      if(flagDay)
	      {
	       JSONObject jssHours=new JSONObject();
	   	   disposeDay(jo,timeLong,address);
	   		jssHours=jarr.getJSONObject(address);
	        jssHours.put("HIn", 0);
	        jarr.put(address, jssHours);
	      }
	}
	
	//
	/**
	 * 
	 * show  判断是不是半个小时的方法（电量）
	 * @param jo 当前接受的消息里一户的JSON对象
	 * @param timeLong  采集时间
	 * @param address  电表地址
	 * @param date2
	 */
	public void judgeHalfHour(JSONObject jo,Long timeLong,String address,Date date2) {
		
		//本地保存的电量时间和 当前数据的时间的 小时的差值
		int jarHJoH=Math.abs(JarrHdata-JoHdata);
		//本地保存电量时间 和当前数据时间的 分钟差值
		int jarMJoM=Math.abs(JarrMdata-JoMdata);
		//不是23的可能性太小了  判断小时差值是否《1 或者等于23
		boolean flagH=jarHJoH<=1||jarHJoH==23;
		//判断分钟的差值 在一分钟之内
		//还是在采集数据的时候  就最好是时间间隔的倍数最好111
		//理想主义下是0   如果是应该是就是这么多  <=30+30%MIndex  >=30-30%MIndex 
		int minResidue=30%MSIndex;
		boolean flagM=(jarMJoM>=30-minResidue&&jarMJoM<=30+minResidue)||(c==jarr.getJSONObject(address).getInt("MIn")&&jarr.getJSONObject(address).getInt("HIn")==0);
		 if (flagH)
         {
            if(flagM)
            {
	            //判断是否是30的倍数如果是就直接进行插入  如果不是就进行数据处理
            	//这个之后可以改进的 是倍数但不是整点取的 C的地方的时间 之后要开始补整吧
	            if(minResidue==0) 
	            {
		            //处理半个小时数据的方法 
		           	disposeHalfHours(jo,timeLong,address);  
		           	//
	            }else {
	            	log.info("我是多于30分钟的情况  我运行了");
	            	//只有多于30分钟的情况
	            	//获取当前地址的分钟级对象
	            	JSONObject min=minarr.getJSONObject(address);
	            	//获取当前地址的前一个分钟级的累计电量
	            	double oldValue=min.getDouble("电量");
	            	//获取当前分钟级对象的累计电量
	            	double newValue=jo.getDouble("累计电量");
	            	//获得两个分钟级对象间隔消耗的电量
	            	double indexValue=newValue-oldValue;
	            	//属于之前30分钟的消耗的电量
	            	double valueBefore=DoubleRound.getValeLatterTwo((MSIndex-minResidue)/MSIndex*indexValue);
	            	//属于之后30分钟的数据消耗的电量  //不对  将本地当前30分钟保存的电量值  减少这部分不就是 给后30分钟增加了
	            	double valueAfter=(minResidue/MSIndex)*indexValue;
	            	//然后处理时间   如果小时的差值等于1或者等于23   可以判定是小时 如果为零则是半小时  在当前时间的基础上直接写入就好了
	            	if(jarHJoH==1||JarrHdata==23) {
	            	//在当前数据的采集时间基础上 小时加一  分钟数清零
	                timeLong=timeLong+3600000-JoMdata*60000;
	            	}else if(JarrHdata==0) {
	            	 timeLong =timeLong-JoMdata*60000;
	            	}
                   //将jo对象里的数据进行处理  这个是给当前要保存的30分钟的 不是之后的  所以是-去之前的数据 即valueAfter; 
	            	//并且把这个值保存在了本地 既减少后的值
	            	//调用保留两位小数的方法进行保留
	            	double joValue=jo.getDouble("累计电量")-valueAfter;
	            	jo.put("累计电量", joValue);
	            	//处理半个小时数据的方法   
		           	disposeHalfHours(jo,timeLong,address);  
	            }
	    		JSONObject jssHours=new JSONObject();
	           	jssHours=jarr.getJSONObject(address);
	           	jssHours.put("HIn",  jarr.getJSONObject(address).getInt("HIn")+1);
	           	//MIn也失去了它的作用
	           	jssHours.put("MIn", 0);
	           	jarr.put(address, jssHours);
            }
         }else if(Math.abs(JarrHdata-JoHdata)>1){
		  log.debug("丢消息了呀");
	      log.debug("你是不是运行了");
		  //如果相差的数据大于一个小时
		  int mm=jarMJoM/30;
		  //是HCount半个小时后的数据
		  int hCount=jarHJoH+mm;
		  System.out.println("这是HCount"+hCount);
		  //当地保存时间的长时间
		  Long oldTime=date2.getTime();
		  //差多少的长时间到达下一个整半小时
		  Long  addTime=1800000-oldTime%1800000;
		  //这就是循环开始的第一条数据的开始时间点
		  Long newTime=oldTime+addTime;
		  //跟之前一样 把有多少个半个小时 时间记录点， 对象传入就好了
		  disposeHalfHours2(jo,newTime, hCount,address);
		  //上面方法好像可以的
         }
	}
	
	/**
	 * show 处理半小时数据的代码
	 * @param jo  当前消息里接收一户的数据的JSON对象
	 * @param time 采集时间
	 * @param valueAfter 属于后30分钟的数据值
	 * @throws JSONException
	 */
	public  void disposeHalfHours(JSONObject jo,Long time,String address) throws JSONException {
   
   	 double newValue=jo.getDouble("累计电量");
   	 JSONObject jsonObject2=(JSONObject) jarr.get(address);
   	  double oldValue=jsonObject2.getDouble("电量");
       //把数据顺便s四舍五入 并且只保留小数点后两位
   	  double value=DoubleRound.getValeLatterTwo(newValue-oldValue);//这个就是半个小时的电量  然后把相关数据插进去
   	  //插入半个小时的表
	   int did=mapDevice.get(address);
	   int tagid=mapTag.get(did);
   	  lightSumdataHoursImpl.insertHalfHour(value,time,tagid);
   	//把当前数据中
   	 JSONObject jssHours=new JSONObject();
   	  jssHours=jarr.getJSONObject(address);
   	   jssHours.put("采集时间",time);
   	   jssHours.put("电量", jo.get("累计电量"));
   	   jarr.put(address, jssHours);
	}
	

	/**
	 * show 如果是多个半个小时的处理方法
	 * @param jo
	 * @param time
	 * @param HCount
	 * @throws JSONException
	 */
	public  void disposeHalfHours2(JSONObject jo,Long time,int hCount,String address) throws JSONException {
	int did=mapDevice.get(address);
	int tagid=mapTag.get(did);
   	double newValue=jo.getDouble("累计电量");
   	JSONObject jsonObject2=(JSONObject) jarr.get(address);
   	double oldValue=jsonObject2.getDouble("电量");
       //把数据顺便s四舍五入 并且只保留小数点后两位  保存在半小时里的值
	//这个就是半个小时的电量  然后把相关数据插进去
   	double value=(double) Math.round((newValue-oldValue) * 100) /100;
   	//这个方法应该不会用到一般 先写一下 
   	 for(int i=0;i<hCount;i++) {
   	  lightSumdataHoursImpl.insertHalfHour((double) Math.round((value/hCount) * 100) /100,time,tagid);
   	  
   	  log.info("这是保存的时间"+formatter.format(new Date(time)));
   	 JSONObject jssHours=new JSONObject();
       jssHours=jarr.getJSONObject(address);
	   jssHours.put("采集时间",time);
	   jssHours.put("电量", jo.get("累计电量"));
	   //又增加了HCount次的半个小时的电量
	   jssHours.put("HIn",  jarr.getJSONObject(address).getInt("HIn")+1);
     //MIn也失去了它的作用
   	  jssHours.put("MIn", 0);
   	  jarr.put(address, jssHours);
   	  //判断加上这半小时后是不是达到了一天
   	judgeDate(address,jo,time);
   	time=time+1800000;
   	 }   	  
}
	
    /**
     * show 处理一天数据的代码
     * @param jo
     * @param longtime
     * @throws JSONException
     */
    public  void disposeDay(JSONObject jo,Long longtime,String address) throws JSONException {
    	
    	 JSONObject jssDay=new JSONObject();
    	 //保存在数据库里这是一天的数据
  	     double newValue=jo.getDouble("累计电量");
  	     log.info("------------一天的开始---------------------");
  	     log.info("保存的时间是："+new Date(longtime-300000));
  		 log.info("这是newvalue："+newValue);
   	     JSONObject jsonObject2=(JSONObject) dayArr.get(address);
   	     double oldValue=jsonObject2.getDouble("电量");
         log.info("这是oldValue："+oldValue);
   	     //把数据顺便s四舍五入 并且只保留小数点后两位
       //这个就是一天的电量  然后把相关数据插进去
      	 double value=DoubleRound.getValeLatterTwo(newValue-oldValue);
      	 log.info("这是value:"+value);
      	 log.info("--------一天的结束------------------------");
 		 int did=mapDevice.get(address);
 		 int tagid=mapTag.get(did);
   	     lightSumdataDayImpl.insertOneDay(value,longtime,tagid);
  	     //把数据保存在本地的一天
   	     jssDay=dayArr.getJSONObject(address);
   	     jssDay.put("采集时间", longtime);
   	     jssDay.put("电量", jo.get("累计电量"));
   	     dayArr.put(address, jssDay);
    }
		
}

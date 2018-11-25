package com.wis.jinan.entity;

import java.util.*;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;

@Component
public class DepartmentFigure {
	Department	department;
	double		light;
	double		power;
	double		total;
	int			devicenum;
	List<DepartmentDevice> departmentDeviceList;
	
	DepartmentFigure()
	{
		this.light = 0.0;
		this.power = 0.0;
		this.total = 0.0;
		this.devicenum = 0;
		this.departmentDeviceList = new ArrayList<DepartmentDevice>();
	}
	
	public double getLight() {
        return light;
    }

    public void setLight(double light) {
        this.light = light;
    }
	
    public double getPower() {
        return power;
    }

    public void setPower(double power) {
        this.power = power;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal() {
        this.total = (double) Math.round((light + power)*100)/100;
    }

    public int getDevicenum() {
        return devicenum;
    }

    public void setDevicenum(int devicenum) {
        this.devicenum = devicenum;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public DepartmentDevice indexDepartmentDevice(int i) {
    	if(i < this.departmentDeviceList.size()) {
    		return this.departmentDeviceList.get(i);
    	}
    	else {
    		return null;
    	}
    }

    public void addDepartmentDevice(DepartmentDevice departmentDevice) {
        this.departmentDeviceList.add(departmentDevice);
    }

    public List<DepartmentDevice> getDepartmentDevice() {
        return this.departmentDeviceList;
    }
    
    public String toJSONstr() {
    	String	message;
    	int		i;
    	
    	List<DepartmentDevice> departmentDevice = new ArrayList();
    	for(i=0; i< devicenum; i++) {
    		departmentDevice.add(indexDepartmentDevice(i));
    	}
    	
    	message = "{" + "\"Departments\":" +
             JSON.toJSONString(this.department) + ',' +  
            "\"light\": " + light + "," + 
    	    "\"power\": " + power + "," +
    	    "\"total\": " + total + "," +
    	    "\"devicenum\": " + devicenum + "," + "\"departmentDevice\":" +
    	    JSON.toJSONString(departmentDevice) +
    	    "}";
    	
    	return message;
    }
}

package com.wis.jinan.entity;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

@Component
public class DepartmentPower {
	Department	department;
	double		power;
	int			num;
	List<PowerFigure> powerFigureList;
	
	DepartmentPower()
	{
		this.power = 0.0;
		this.num = 0;
		this.powerFigureList = new ArrayList<PowerFigure>();
	}
	
    public double getPower() {
        return power;
    }

    public void setPower(double power) {
        this.power = power;
    }


    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public PowerFigure indexPowerFigure(int i) {
    	if(i < this.powerFigureList.size()) {
    		return this.powerFigureList.get(i);
    	}
    	else {
    		return null;
    	}
    }

    public void addPowerFigure(PowerFigure powerFigure) {
        this.powerFigureList.add(powerFigure);
    }

    public List<PowerFigure> getPowerFigureList() {
        return this.powerFigureList;
    }
    
    public String toJSONstr() {
    	String	message;
    	int		i;
    	List<PowerFigure> powerFigures = new ArrayList();
    	for(i=0; i< num; i++) {
    		powerFigures.add(indexPowerFigure(i));
    	}
    	
    	message = "{" + "\"Departments\":" +
             JSON.toJSONString(this.department) + ',' +  
    	    "\"power\": " + power + "," +
    	    "\"num\": " + num + "," + "\"powerFigures\":" +
    	    JSON.toJSONString(powerFigures) +
    	    "}";
    	
    	return message;
    }
}

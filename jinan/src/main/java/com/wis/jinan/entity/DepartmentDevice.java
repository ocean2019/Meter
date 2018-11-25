package com.wis.jinan.entity;

import org.springframework.stereotype.Component;

public class DepartmentDevice {
	int		id;
	int		parentid;
	String	tagname;
	double	light;
	double	power;
	double	total;

	public DepartmentDevice() {
		this.id 		= 0;
		this.parentid 	= 0;
		this.light 		= 0.0;
		this.power 		= 0.0;
		this.total 		= 0.0;
	}
	
	public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getParentid() {
        return parentid;
    }

    public void setParentid(int parentid) {
        this.parentid = parentid;
    }

    public String getTagname()
    {
    	return tagname;
    }
    
    public void setTagname(String tagname) {
        this.tagname = tagname;
    }
    
    public void setLight(double light) {
        this.light = light;
    }
    
    public double getLight() {
        return this.light;
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

    public void setTotal(double total) {
        this.total = total;
    }
    
    @Override
    public String toString() {
    	String	message;
        message = "Department{" +
                "id='" + id + '\'' +
                ", parentid='" + parentid + '\'' +
                ", tagname='" + tagname + '\'' +
                ", light='" + light + '\'' +
                ", power='" + power + '\'' +
                ", total='" + total + '\'' +
                '}';
        
        return message;
    }

}

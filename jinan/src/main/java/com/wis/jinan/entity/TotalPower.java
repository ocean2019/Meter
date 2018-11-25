package com.wis.jinan.entity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class TotalPower {
	String 	name;
	int		value;
	
	private  final Logger logger = LoggerFactory.getLogger(this.getClass());
    
	TotalPower() {
		this.name = "实时总功率";
		this.value = 0;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public String getName()
	{
		return this.name;
	}
	
	public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

	    
    @Override
    public String toString() {
    	String	message;
    	
        message = "power{" +
                "name='" + name + '\'' +
                "value='" + value + '\'' +
                '}';
        
        logger.info(message);
    	
        return message;
    }

}

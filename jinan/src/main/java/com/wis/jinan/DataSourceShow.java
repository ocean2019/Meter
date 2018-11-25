package com.wis.jinan;

import javax.sql.DataSource;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class DataSourceShow implements ApplicationContextAware {
	ApplicationContext	applicationContext;
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
		
		DataSource dataSource = applicationContext.getBean(DataSource.class);
		System.out.println("------------------------------------------------");
		System.out.println(applicationContext.getApplicationName());
		System.out.println(dataSource.getClass().getName());
		System.out.println(dataSource.toString());
		System.out.println("------------------------------------------------");
	}
}

package com.wis.jinan.service;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class JinHealthIndicator implements HealthIndicator {
	public int	check() {
		return -1;
	}
	
	@Override
	public Health health() {
		int	errorCode = check();
		
		if(errorCode != 0) {
			return Health.down().withDetail("Error Code", errorCode).build();
		}
		
		return Health.up().build();
	}
}

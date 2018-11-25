package com.wis.jinan.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.wis.jinan.entity.TotalPower;;

@Repository
public class ProjectDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	
	
	public int queryTotalPower() {
		Double		totalPower;
		int			power;
		totalPower = jdbcTemplate.queryForObject("select sum(val3) from wis_device_curdata",Double.class);
		if (totalPower == null)
		{
			totalPower = 0.0;
		}
		
		power = (int) (totalPower * 100 / 100);
		
		return power;
	}

	public int queryTotalEnergy() {
		Double		totalEnergy, t1, t2;
		int			energy;
		
		t1 = jdbcTemplate.queryForObject("select sum(val1) from wis_light_sumdata_day",Double.class);
		if (t1 == null)
		{
			t1 = 0.0;
		}
		
		t2 = jdbcTemplate.queryForObject("select sum(val1) from wis_power_sumdata_day",Double.class);
		if (t2 == null) {
			t2 = 0.0;
		}
		totalEnergy = t1 + t2;
		
		energy = (int) (totalEnergy * 100 / 100);
		
		return energy;
	}
}

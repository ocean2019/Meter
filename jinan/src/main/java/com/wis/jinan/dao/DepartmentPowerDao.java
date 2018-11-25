package com.wis.jinan.dao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import com.wis.jinan.entity.Department;
import com.wis.jinan.entity.DepartmentPower;
import com.wis.jinan.entity.PowerFigure;

@Repository
public class DepartmentPowerDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	DepartmentPower	departmentPower;
	
	private  final Logger logger = LoggerFactory.getLogger(this.getClass());

	public DepartmentPower findDepartmentPowerById(int id, int eType, int type, String startDate, String endDate) {
		Double		value;
		Double		total;
		int			power;
		int			num;
		int			tagid;
		SqlRowSet	rs;
		
		value = 0.0;
		total = 0.0;
		num = 0;
		
		logger.info("DepartmentPowerDao " + type + " " + startDate + " " + endDate);
		
		List<Department> list = jdbcTemplate.query("select id, parentid, prjid, tagtype, tagname from wis_tag where id=?", new Object[] {id}, new BeanPropertyRowMapper(Department.class));
		if(list!=null && list.size()>0) {
			departmentPower.setDepartment(list.get(0));
		}
		else {
			return null;
		}
		
		logger.info("DepartmentPowerDao 2" + departmentPower.getDepartment());
		if(eType == 1) {
			rs = jdbcTemplate.queryForRowSet("select date_format(reptime,\"%Y-%m-%d %H:%i:%s\"), sum(val1) from wis_light_powerdata_hour where reptime >=? and reptime <=? and tagid in (select id from wis_tag where parentid=?) group by reptime", startDate, endDate, id);
		}else if(eType == 2) {
			rs = jdbcTemplate.queryForRowSet("select date_format(reptime,\"%Y-%m-%d %H:%i:%s\"), sum(val1) from wis_power_powerdata_hour where reptime >=? and reptime <=? and tagid in (select id from wis_tag where parentid=?) group by reptime", startDate, endDate, id);
		}else {
			logger.warn("DeaprtmentPowerDao wrong eType: " + eType);
			return null;
		}
	
		while(rs.next())
		{
			
			PowerFigure	powerFigure = departmentPower.indexPowerFigure(num);
			if(powerFigure == null) {
				logger.info("DepartmentPowerDao new PowerFigure: " + num);				
				powerFigure = new PowerFigure();
				departmentPower.addPowerFigure(powerFigure);
			}
			
			logger.info("DepartmentPowerDao: " + rs.getString(1));
			
			powerFigure.setTime(rs.getString(1));
			
			
			value = rs.getDouble(2);
			total += value;
			
			power = (int) Math.round(value*100)/100;
			
			powerFigure.setPower(power);
				
			num ++;
			total = (double) Math.round(total*100)/100;
		}

		departmentPower.setNum(num);
		
		power = (int) Math.round(total*100)/100;
		departmentPower.setPower(power);
		
		return departmentPower;
	}
}

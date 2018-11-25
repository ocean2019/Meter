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
import com.wis.jinan.entity.DepartmentDevice;
import com.wis.jinan.entity.DepartmentFigure;

@Repository
public class DepartmentFigureDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	DepartmentFigure	departmentFigure;
/*	
	@Autowired
	DepartmentDevice	departmentDevice;
*/	
	private  final Logger logger = LoggerFactory.getLogger(this.getClass());

	public DepartmentFigure findDepartmentFigureById(int id, int type, String startDate, String endDate) {
		Double		light, power, total;
		Double		t_light, t_power;
		int			devicenum;
		int			tagid;
		
		t_light = 0.0;
		t_power = 0.0;
		devicenum = 0;
		
		logger.info("DepartmentFigureDao " + type + " " + startDate + " " + endDate);
		
		List<Department> list = jdbcTemplate.query("select id, parentid, prjid, tagtype, tagname from wis_tag where id=?", new Object[] {id}, new BeanPropertyRowMapper(Department.class));
		if(list!=null && list.size()>0) {
			departmentFigure.setDepartment(list.get(0));
		}
		else {
			return null;
		}
		
		logger.info("DepartmentFigureDao 2" + departmentFigure.getDepartment());
		
		SqlRowSet rs = jdbcTemplate.queryForRowSet("select id, parentid, tagtype, tagname from wis_tag where parentid=?", id);
		while(rs.next())
		{
			DepartmentDevice	departmentDevice = departmentFigure.indexDepartmentDevice(devicenum);
			if(departmentDevice == null) {
				logger.info("DepartmentFigureDao new DepartmentDeivce: " + devicenum);				
				departmentDevice = new DepartmentDevice();
				departmentFigure.addDepartmentDevice(departmentDevice);
			}
			tagid = rs.getInt("ID");
			departmentDevice.setId(tagid);
			departmentDevice.setParentid(rs.getInt("PARENTID"));
			departmentDevice.setParentid(rs.getInt("TAGTYPE"));
			departmentDevice.setTagname(rs.getString("TAGNAME"));
			
			departmentDevice.setLight(0.0);
			departmentDevice.setPower(0.0);
			departmentDevice.setTotal(0.0);
			
			light = 0.0;
			power = 0.0;
			total = 0.0;
			
			if(type == 2) {
				light = jdbcTemplate.queryForObject("select sum(val1) from wis_light_sumdata_day where tagid=? and reptime >= ? and reptime <= ?", new Object[] {tagid, startDate, endDate}, Double.class);
			}else {
				light = jdbcTemplate.queryForObject("select sum(val1) from wis_light_sumdata_hour where tagid=? and reptime >= ? and reptime <= ?", new Object[] {tagid, startDate, endDate}, Double.class);
			}
			
			logger.info("DepartmentFigureDao light " + tagid + " : " +  light);
			
			if (light != null) {
				light = (double) Math.round(light*100)/100;

				logger.info("DepartmentFigureDao " + tagid + " " +  light);
				
				departmentDevice.setLight(light);
				t_light += light;
				
				total  += light;
			}
		
			logger.info("DepartmentFigureDao 3" + departmentFigure.getLight());
			if(type == 2) {
				power = jdbcTemplate.queryForObject("select sum(val1) from wis_power_sumdata_day where tagid=? and reptime >= ? and reptime <= ?", new Object[] {tagid, startDate, endDate}, Double.class);
			}else {
				power = jdbcTemplate.queryForObject("select sum(val1) from wis_power_sumdata_hour where tagid=? and reptime >= ? and reptime <= ?", new Object[] {tagid, startDate, endDate}, Double.class);
			}
			if(power != null) {
				
				power = (double) Math.round(power*100)/100;
				departmentDevice.setPower(power);
				t_power += power;
				total += power;
			}
			
			devicenum ++;
			total = (double) Math.round(total*100)/100;
			departmentDevice.setTotal(total);
		}

		departmentFigure.setDevicenum(devicenum);
		t_light = (double) Math.round(t_light*100)/100;
		departmentFigure.setLight(t_light);
		
		t_power = (double) Math.round(t_power*100)/100;
		departmentFigure.setPower(t_power);
		departmentFigure.setTotal();
		
		return departmentFigure;
	}
}

package com.wis.jinan.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.wis.jinan.entity.WisDevice;

@Repository
public class WisDeviceDao {

		@Autowired
		private JdbcTemplate jdbcTemplate;
		
		public WisDevice findWisDeviceById(int id) {
			List<WisDevice> list = jdbcTemplate.query("select * from wis_device where id=?", new Object[] {id}, new BeanPropertyRowMapper(WisDevice.class));
			if(list!=null && list.size()>0) {
				WisDevice wisDevice = list.get(0);
				return wisDevice;
			}
			else {
				return null;
			}
		}
		
	    public List<WisDevice> findWisDeviceList() {
	        List<WisDevice> list = jdbcTemplate.query("select * from wis_device", new Object[]{}, new BeanPropertyRowMapper(WisDevice.class));
	        if(list!=null && list.size()>0){
	            return list;
	        }else{
	            return null;
	        }
	    }
}

package com.wis.jinan.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.wis.jinan.entity.Department;

@Repository
public class DepartmentDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public Department findDepartmentById(int id) {
		List<Department> list = jdbcTemplate.query("select id, parentid, prjid, tagtype, tagname from wis_tag where id=?", new Object[] {id}, new BeanPropertyRowMapper(Department.class));
		if(list!=null && list.size()>0) {
			Department department = list.get(0);
			return department;
		}
		else {
			return null;
		}
	}
	
    public List<Department> findDepartmentList() {
        List<Department> list = jdbcTemplate.query("select id, parentid, prjid, tagtype, tagname from wis_tag where status = 1 and parentid in (select id from wis_tag where tagname='部门')", new Object[]{}, new BeanPropertyRowMapper(Department.class));
        if(list!=null && list.size()>0){
            return list;
        }else{
            return null;
        }
    }
}

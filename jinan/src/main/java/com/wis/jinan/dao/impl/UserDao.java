package com.wis.jinan.dao.impl;

import com.wis.jinan.dao.IUserDao;
import com.wis.jinan.entity.User;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDao implements IUserDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public int add(User user) {
		return jdbcTemplate.update("insert into wis_user(loginname, password) values(?, ?)",
				user.getLoginname(), user.getPassword());
	}

	@Override
    public int update(User user) {
        return jdbcTemplate.update("UPDATE  wis_user SET loginname=? , password=? WHERE id=?",
                user.getLoginname(), user.getPassword(), user.getId());
    }
	
	@Override
	public int delete(int id) {
		return jdbcTemplate.update("delete from table wis_user where id=?", id);
	}
	
	@Override
	public User findUserById(int id) {
		List<User> list = jdbcTemplate.query("select * from wis_user where id=?", new Object[] {id}, new BeanPropertyRowMapper(User.class));
		if(list!=null && list.size()>0) {
			User user = list.get(0);
			return user;
		}
		else {
			return null;
		}
	}
	
    @Override
    public List<User> findUserList() {
        List<User> list = jdbcTemplate.query("select * from wis_user", new Object[]{}, new BeanPropertyRowMapper(User.class));
        if(list!=null && list.size()>0){
            return list;
        }else{
            return null;
        }
    }
}
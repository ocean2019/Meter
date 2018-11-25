package com.wis.jinan.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wis.jinan.dao.DepartmentDao;
import com.wis.jinan.entity.Department;

@Service
public class DepartmentService {
    @Autowired
    DepartmentDao departmentDao;

    
    public Department findDepartmentById(int id) {
        return departmentDao.findDepartmentById(id);
    }

    
    public List<Department> findDepartmentList() {
        return departmentDao.findDepartmentList();
    }
}

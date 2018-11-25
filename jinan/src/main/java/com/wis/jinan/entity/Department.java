package com.wis.jinan.entity;

import java.util.*;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class Department {
	int 	id;
	int		parentid;
	int		prjid;
	int		tagtype;
	String	tagname;

	public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getParentid() {
        return parentid;
    }

    public void setParentid(int parentid) {
        this.parentid = parentid;
    }



    public String getTagname()
    {
    	return tagname;
    }
    
    public void setTagname(String tagname) {
        this.tagname = tagname;
    }
    
    @Override
    public String toString() {
    	String	message;
        message = "Department{" +
                "id='" + id + '\'' +
                ", tagtype='" + tagtype + '\'' +
                ", tagname='" + tagname + '\'' +
                '}';
        
        return message;
    }
}

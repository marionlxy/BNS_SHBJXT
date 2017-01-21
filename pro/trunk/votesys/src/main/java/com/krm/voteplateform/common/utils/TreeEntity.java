package com.krm.voteplateform.common.utils;

import java.util.Date;
import java.util.Map;


import com.alibaba.fastjson.JSONObject;

@SuppressWarnings({ "unused"})
public class TreeEntity extends JSONObject {

	private static final long serialVersionUID = 1L;
	
	
	
	private String userDataScope; //用户的数据范围
	
	private String parentid;
	
	private String parentIds;
	
	public String getParentIds() {
		return parentIds;
	}

	public void setParentIds(String parentIds) {
		this.parentIds = parentIds;
	}

	public String getParentid() {
		return parentid;
	}

	public void setParentid(String parentid) {
		this.parentid = parentid;
	}

	public TreeEntity() {
		super();
	}
	
	public void setId(Long id){
		this.set("id",id);
	}

	public String getId(){
		return this.getString("id");
	}
	
    public String getUserDataScope() {
		return this.getString("userDataScope");
    }
   
    public void setUserDataScope(String userDataScope) {
		this.set("userDataScope", userDataScope);
    }

	public TreeEntity(Map<String, Object> map) {
		super(map);
	}

	public TreeEntity getEntity(String key) {
		Object value = this.get(key);
		if (value instanceof TreeEntity) {
			return (TreeEntity) value;
		}
		
		JSONObject jobj = null;

		if (value instanceof JSONObject) {
			jobj = (JSONObject) value;
		} else {
			jobj = (JSONObject) toJSON(value);
		}

		return jobj == null ? null : new TreeEntity(jobj);
	}

	public TreeEntity set(String key, Object value, boolean force) {
		if (force || value != null) {
			super.put(key, value);
		}
		return this;
	}

	public TreeEntity set(String key, Object value) {
		return this.set(key, value, true);
	}

	public TreeEntity setAll(Map<? extends String, ? extends Object> m) {
		super.putAll(m);
		return this;
	}

	public static TreeEntity err(int errCode) {
		return new TreeEntity().set("errCode", errCode);
	}

	public static TreeEntity err(int errCode, String errMsg) {
		return new TreeEntity().set("errCode", errCode).set("errMsg", errMsg);
	}
}

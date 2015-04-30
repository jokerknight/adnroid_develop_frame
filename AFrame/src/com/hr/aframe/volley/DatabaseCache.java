package com.hr.aframe.volley;

import java.io.Serializable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "tb_cache")
public class DatabaseCache implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3196450415804873844L;

	@DatabaseField(id = true)
	private String index;

	@DatabaseField
	private long timestamp;

	@DatabaseField
	private String data;

	public String getIndex() {
		return index;
	}

	public void setIndex(String index) {
		this.index = index;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}
}

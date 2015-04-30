package com.hr.aframe.bean;

import java.io.Serializable;

public class TestBean implements Serializable {
	public double lon;
	public double lat;
	public int level;
	public String cityName;
	public String address;
	public int alevel;

	@Override
	public String toString() {
		return "TestBean [lon=" + lon + ", lat=" + lat + ", level=" + level
				+ ", cityName=" + cityName + ", address=" + address
				+ ", alevel=" + alevel + "]";
	}

}

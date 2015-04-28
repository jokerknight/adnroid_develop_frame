package com.hr.aframe.bean;

import java.io.Serializable;

public class LogoutCheckResponse implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4421161717911019955L;
	private boolean logout;

	public boolean isLogout() {
		return logout;
	}

	public void setLogout(boolean logout) {
		this.logout = logout;
	}

	@Override
	public String toString() {
		return "LogoutCheckResponse [logout=" + logout + "]";
	}

}

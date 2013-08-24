package com.kaowtiw.partyinmypants;

import android.app.Application;

public class Globals extends Application {
	private String userId;
	
	public String getUserId() {
		return userId;
	}
	
	public void setUserId(String userId) {
		this.userId = userId;
	}
}

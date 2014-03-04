package com.cra.drac.api

import java.text.SimpleDateFormat;

import com.cra.drac.interfaces.ISession

class Session implements ISession {
	String serverName

	@Override
	public void login() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void login(String userName, String password) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getUserName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String serverName() {
		return serverName
	}

	@Override
	public void serverName(String serverName) {
		this.serverName = serverName
	}

}

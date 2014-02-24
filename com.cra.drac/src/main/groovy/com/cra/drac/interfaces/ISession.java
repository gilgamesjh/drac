package com.cra.drac.interfaces;

public interface ISession {
	void login();
	void login(String userName, String password);
	String getUserName();
	String serverName();
	void serverName(String serverName);
}

package com.cra.drac.interfaces;

import com.fasterxml.jackson.databind.ObjectMapper;

public interface ISession {
	ISession login();
	ISession login(String userName, String password);
	String getUserName();
	String serverName();
	ISession serverName(String serverName);
    ObjectMapper getObjectMapper();
}

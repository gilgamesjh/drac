package com.cra.drac.api

import com.cra.drac.interfaces.ISession
import com.fasterxml.jackson.databind.ObjectMapper

class Session implements ISession {
	private String serverName
    private ObjectMapper objectMapper

    Session(){
        this.objectMapper = new ObjectMapper()
    }

	@Override
	public ISession login() {
		// TODO Auto-generated method stub
		return this
	}

	@Override
	public ISession login(String userName, String password) {
		// TODO Auto-generated method stub
		return this
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
	public ISession serverName(String serverName) {
        if(serverName.endsWith('/')){
            this.serverName = serverName
        } else {
            this.serverName = serverName + '/'
        }
        return this
	}

    @Override
    ObjectMapper getObjectMapper() {
        return objectMapper
    }
}

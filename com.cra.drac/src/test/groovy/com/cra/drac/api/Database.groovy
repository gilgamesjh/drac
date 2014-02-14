package com.cra.drac.api

import com.cra.drac.interfaces.IDatabase
import com.cra.drac.interfaces.IDocument
import com.cra.drac.interfaces.ISession

class Database implements IDatabase {
	ISession session
	String database
	String databasePath
	String view
	String query
	int start = 0
	int count = 30
	String key
	
	Database(ISession session, String database){
		this.session = session
		this.database = database
		databasePath = session.serverName()+database+'/api/data/collections'
	}
	
	@Override
	public IDatabase view(String view) {
		this.view = view
		return this
	}

	@Override
	public IDatabase query(String query) {
		this.query = query
		return this
	}

	@Override
	public IDatabase start(int start) {
		this.start = start
		return this
	}

	@Override
	public IDatabase count(int count) {
		this.count = count
		return this
	}

	@Override
	public IDatabase key(String key) {
		this.key = key
		return this
	}

	@Override
	public List<IDocument> execute() {
		// build a url
		String url = databasePath
		if(view){
			url += "name/${view}?compact=false"
		}
		if(key){
			url += "&keys=${key}"
		}
		url += "&start=${start}&count=${count}"
		
		println url
		
		return []
	}

}

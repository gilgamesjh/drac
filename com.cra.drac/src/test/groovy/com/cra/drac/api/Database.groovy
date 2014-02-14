package com.cra.drac.api

import groovy.json.JsonSlurper
import groovyx.net.http.HTTPBuilder
import static groovyx.net.http.Method.GET
import static groovyx.net.http.ContentType.JSON
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
		databasePath = session.serverName()+database+'/api/data/collections/'
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
		
		def slurper = new JsonSlurper()
		
		def http = new HTTPBuilder( url )
		
		println url
		
		http.request(GET,JSON) { req ->
		   
			response.success = { resp, reader ->
			  println "My response handler got response: ${resp.statusLine}"
			  println "Response length: ${resp.headers.'Content-Length'}"
			  println reader
			}
		   
			// called only for a 404 (not found) status code:
			response.'404' = { resp ->
			  println 'Not found'
			}
			
			http.handler.failure = { resp ->
				println "Unexpected failure: ${resp.statusLine}"
			}
		}
		
		//def result = slurper.parse(new URL(url).getContent())
		
		//println result
		
		return []
	}

}

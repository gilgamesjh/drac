package com.cra.drac.util

import static groovyx.net.http.ContentType.JSON
import static groovyx.net.http.ContentType.TEXT
import static groovyx.net.http.Method.*
import groovyx.net.http.HTTPBuilder
import groovyx.net.http.URIBuilder

import com.cra.drac.api.Document
import com.cra.drac.interfaces.IDatabase
import com.cra.drac.interfaces.IDocument
import com.cra.drac.interfaces.IDocumentHandler
import com.cra.drac.interfaces.ISession

class DocumentHandler implements IDocumentHandler{
	ISession session
	IDatabase database
	String databasePath
	
	DocumentHandler(IDatabase database){
		this.session = database.getSession()
		this.database = database
		databasePath = database.getDatabase()+'/api/data/documents'
	}
	
	@Override
	public IDocument get(String unid) {
		return get(unid, Document)
	}
	
	@Override
	public IDocument get(String unid, Class<?> clazz) {
		IDocument document
		String url = "${databasePath}/unid/${unid}?compact=true&multipart=false&strongtype=true"
		
		def http = new HTTPBuilder( session.serverName()+url )
		http.request(GET,JSON) { req ->
			
			response.success = { resp, reader ->
				document = clazz.newInstance(database, reader)
				//document = new Document(session, reader)			   
			}
			
			// called only for a 404 (not found) status code:
			response.'404' = { resp ->
				println 'Not found'
			}
			 
			http.handler.failure = { resp ->
				println "Unexpected failure: ${resp.statusLine}"
			}
		}
		
		return document
	}

	@Override
	public void delete(String unid) {
		String url = "${databasePath}/unid/${unid}"
		
		def http = new HTTPBuilder( session.serverName()+url )
		http.request(DELETE,JSON) { req ->
			
			response.success = { resp, reader ->
				return null			   
			}
			
			// called only for a 404 (not found) status code:
			response.'404' = { resp ->
				println 'Not found'
			}
			 
			http.handler.failure = { resp ->
				println "Unexpected failure: ${resp.statusLine}"
			}
		}
	}

	@Override
	public IDocument save(String unid, Map<String, Object> items, Class<?> clazz) {
		def uri = new URIBuilder( session.serverName() )
		uri.path = "/${databasePath}"
		if(unid){
			uri.path = "/${databasePath}/unid/${unid}"
		}
		if(!unid && items.form){
			uri.query = [
				form: items.form	
			]
		}
		println uri.toString()
				
		def http = new HTTPBuilder( uri.toString() )
		http.parser.'application/json' = http.parser.'text/plain'
		if(unid){
			http.setHeaders([
				'X-HTTP-Method-Override':'PATCH'	
			])
		}
		http.request(POST, JSON) { req ->
			body = items
						
			response.success = { resp, text ->
				
				return get(unid, clazz)
			}
			
			response.'201' = { resp ->
				String location = resp.headers.'Location'
				unid = location.substring(location.indexOf('unid/')+5)
				return get(unid, clazz)
			}
			
			// called only for a 404 (not found) status code:
			response.'404' = { resp ->
				println 'Not found'
			}
			
			response.'400' = { resp ->
				println resp.statusLine
			}
			 
			http.handler.failure = { resp ->
				println "Unexpected failure: ${resp.statusLine}"
			}
		}
		
		
		return get(unid, clazz);
	}

	@Override
	public IDocument save(IDocument doc) {
		String unid = doc.'@unid'
		
		return save(unid, doc.getRawFields(), doc.getClass())
	}

	@Override
	public IDocument put(String unid, Map<String, Object> items) {
		return save(unid, items, Document)
	}

	@Override
	public void delete() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public IDocument save(String unid, Map<String, Object> items) {
		// TODO Auto-generated method stub
		return null;
	}


}
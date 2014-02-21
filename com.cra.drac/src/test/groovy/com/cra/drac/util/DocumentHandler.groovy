package com.cra.drac.util

import static groovyx.net.http.ContentType.JSON
import static groovyx.net.http.Method.*
import groovyx.net.http.HTTPBuilder

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
		databasePath = database.getDatabase()+'/api/data/documents/unid/'
	}
	
	@Override
	public IDocument get(String unid) {
		return get(unid, Document)
	}
	
	@Override
	public IDocument get(String unid, Class<?> clazz) {
		IDocument document
		String url = databasePath + unid + '?compact=true&multipart=false&strongtype=true'
		
		def http = new HTTPBuilder( session.serverName()+url )
		http.request(GET,JSON) { req ->
			
			response.success = { resp, reader ->
				document = clazz.newInnstance(session, reader)
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
	public void delete() {
		String url = databasePath + unid
		
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
	public IDocument patch(String unid, Map<String, Object> items) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IDocument put(String unid, IDocument document) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IDocument put(String unid, Map<String, Object> items) {
		// TODO Auto-generated method stub
		return null;
	}

	

}

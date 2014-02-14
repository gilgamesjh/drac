package com.cra.drac.util

import static groovyx.net.http.ContentType.JSON
import static groovyx.net.http.Method.GET
import groovyx.net.http.HTTPBuilder

import com.cra.drac.api.Document
import com.cra.drac.api.Entry
import com.cra.drac.interfaces.IDatabase
import com.cra.drac.interfaces.IDocument
import com.cra.drac.interfaces.IEntry
import com.cra.drac.interfaces.ISession

class HttpHandler {
	ISession session
	IDatabase database
	 
	HttpHandler(IDatabase database){
		this.session = database.getSession()
		this.database = database
	}
	
	List<IEntry> getEntries(String url){
		def result = []
		def http = new HTTPBuilder( session.serverName()+url )
		http.request(GET,JSON) { req ->
			
			response.success = { resp, reader ->
				
				if(reader instanceof ArrayList){
					 reader.each {
						 result << new Entry(database, it)
					 }
				} 
			   
			}
			
			// called only for a 404 (not found) status code:
			response.'404' = { resp ->
				println 'Not found'
			}
			
			response.'400' = { resp, reader ->
				throw new BackendException(reader.message)
			}
			 
			http.handler.failure = { resp ->
				println "Unexpected failure: ${resp.statusLine}"
			}
		}
		return result
	}
	
	IDocument getDocument(String url){
		IDocument document
		def http = new HTTPBuilder( session.serverName()+url )
		http.request(GET,JSON) { req ->
			
			response.success = { resp, reader ->
				document = new Document(session, reader)			   
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
}

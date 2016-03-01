package com.cra.drac.util

import com.cra.drac.api.Entry
import com.cra.drac.interfaces.IDatabase
import com.cra.drac.interfaces.IDocument
import com.cra.drac.interfaces.IEntry
import com.cra.drac.interfaces.ISession
import com.cra.drac.ws.UrlFetch
import groovyx.net.http.HTTPBuilder

import static groovyx.net.http.ContentType.JSON
import static groovyx.net.http.Method.GET

class HttpHandler {
	ISession session
	IDatabase database
	 
	HttpHandler(IDatabase database){
		this.session = database.getSession()
		this.database = database
	}


    int getCount(String url){
        int count = UrlFetch.getCount(session.serverName()+url)
        return count
    }

	List<IEntry> getEntries(String url, Class clazz){
		def result = []

        String res = UrlFetch.get(session.serverName()+url)
        List entries = session.getObjectMapper().readValue(res, ArrayList.class)
        entries.each {
            if(clazz.getName()=='Entry'){
                result << new Entry(database, it)
            } else {
                result << clazz.newInstance(database, it)
            }
        }

		return result
	}
	
	IDocument getDocument(String url, Class clazz){
		IDocument document
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
}

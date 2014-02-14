package com.cra.drac.api

import static groovyx.net.http.ContentType.JSON
import static groovyx.net.http.Method.GET

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.naming.ldap.SortControl;

import com.cra.drac.interfaces.IDatabase
import com.cra.drac.interfaces.IDocument
import com.cra.drac.interfaces.IDocumentHandler
import com.cra.drac.interfaces.ISession
import com.cra.drac.util.DocumentHandler
import com.cra.drac.util.HttpHandler

class Database implements IDatabase {
	ISession session
	String database
	String databasePath
	String view
	String query
	String unid
	int start = 0
	int count = 30
	String key
	Date date
	int max = 100
	String columnName
	boolean ascending = true
	
	Database(ISession session, String database){
		this.session = session
		this.database = database
		databasePath = database+'/api/data/'
	}
	
	private void reset(){
		view = null
		query = null
		unid = null
		start = 0
		count = 30
		key = null
		date = null
		max = 100
		columnName = null
		ascending = true
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
			url += "collections/name/${view}?compact=true"
			if(key){
				url += "&keys=${key}"
			}
			if(query){ // ft query inside the view
				url += "&search=${URLEncoder.encode(query,'UTF-8')}&searchmaxdocs=${max+2}"
			}
			if(columnName){
				url += "&sortcolumn=${columnName}&sortorder=${ascending?'ascending':'descending'}"
			}
			url += "&start=${start}&count=${count}"
		} else if(query){
			url += "documents?compact=true&search=${URLEncoder.encode(query,'UTF-8')}&searchmaxdocs=${max+2}"
			if(date){
				SimpleDateFormat dfe = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
				url += "&since=${dfe.format(date)}"
			}
		}
		reset() // call reset to clear for next call	
		println url	
		return new HttpHandler(this).getEntries(url) 
	}

	@Override
	public IDocumentHandler document() {
		return new DocumentHandler(this)
	}

	@Override
	public IDatabase since(Date date) {
		this.date = date
		return this
	}

	@Override
	public IDatabase max(int max) {
		this.max = max
		return this
	}

	@Override
	public IDatabase sortcolumn(String columnName) {
		this.columnName = columnName
		return this
	}

	@Override
	public IDatabase ascending(boolean ascending) {
		this.ascending = ascending
		return this
	}

}

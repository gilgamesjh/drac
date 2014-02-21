package com.cra.drac.api

import static groovyx.net.http.ContentType.JSON
import static groovyx.net.http.Method.GET

import com.cra.drac.interfaces.IDatabase
import com.cra.drac.interfaces.IDocument
import com.cra.drac.interfaces.IDocumentHandler
import com.cra.drac.interfaces.IEntry
import com.cra.drac.interfaces.ISession
import com.cra.drac.util.DocumentHandler
import com.cra.drac.util.HttpHandler
import com.cra.drac.util.PathQueryBuilder

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
	public List<IEntry> execute() {
		return execute(Entry)
	}
	
	public List<IDocument> execute(Class clazz){
		String url = new PathQueryBuilder(this).build()
		reset() // call reset to clear for next call
		println url
		return new HttpHandler(this).getEntries(url, clazz)
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

	@Override
	public List<IDocument> executeOne() {
		return executeOne(Document)
	}

	@Override
	public List<IDocument> executeOne(Class<?> clazz) {
		count = 1
		String url = new PathQueryBuilder(this).build()
		reset() // call reset to clear for next call
		println url
		List<IDocument> entries =new HttpHandler(this).getEntries(url)
		if(entries && entries.size()>0){
			return document.get(entries[0].'@unid', clazz)
		}
	}

	@Override
	public List<IDocument> executeMany() {
		return executeMany(Document)
	}

	@Override
	public List<IDocument> executeMany(Class<?> clazz) {
		return execute().collect{
			document().get(it.'@unid', clazz)
		}
	}

}

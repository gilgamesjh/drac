package com.cra.drac

import com.cra.drac.api.Database
import com.cra.drac.api.Session
import com.cra.drac.content.Content
import com.cra.drac.interfaces.IDatabase
import com.cra.drac.interfaces.IEntry
import com.cra.drac.interfaces.ISession

class Test {

	static main(args) {
		ISession session = new Session(serverName:'http://www.foo.bar/')
		IDatabase database = new Database(session, 'cms/cms.nsf')
		
		/*
		def entries = database
		.view('content_by_title')
		.count(5)
		.executeMany()
		
		entries.each { IDocument doc ->
			println doc.title
		}
		*/
		
		
		List<IEntry> docs = database
		.view('content_by_title')
		.execute().each { IEntry entry ->
			println entry.title
		}
		
		/*		
		Content content = database.createDocument(Content, [
			Form:'Content'	
		])
		content.status = 'STATUS.Draft'
		content.title = 'Dette er en remote test 9'
		content.owner = 'CN=Bjørn Tore Olsen Cintra/OU=WPC'
		content = content.save()
		
		println content.'@unid'
		*/
		
		Content content = database.document().get('8C2E5EFA26A70931C1257C89007F9E72', Content)
		content.title = 'Å hei, vi endrer'
		content = content.save()
		println content.getRawFields()
		
		/*
		entries = database
		.query('[Form]="Publication"')
		.max(10)
		.execute()
				
		entries.each { IEntry entry ->
			println entry.getDocument().Subject
		}
		*/
		
		/*
		entries = database
		.view('publications_by_subject')
		.query('[Subject] CONTAINS Kragerø')
		.sortcolumn('Title')
		.ascending(true)
		.max(10)
		.execute()
		
		entries.each { IEntry entry ->
			println entry.Title
		}*/
	}

}

package com.cra.drac

import com.cra.drac.api.Database
import com.cra.drac.api.Session
import com.cra.drac.interfaces.IDatabase
import com.cra.drac.interfaces.IDocument
import com.cra.drac.interfaces.IEntry
import com.cra.drac.interfaces.ISession

class Test {

	static main(args) {
		ISession session = new Session(serverName:'http://www.foo.bar/')
		IDatabase database = new Database(session, 'iSite/klepp/kleppk.nsf')
		
		def entries = database
		.view('publications_by_subject')
		.start(30)
		.count(10)
		.execute()
		
		entries.each { IEntry entry ->
			String unid = entry['@unid']
			IDocument doc = database.document().get(unid)
			println doc.Subject
		}
		
		
		entries = database
		.query('[Form]="Publication"')
		.max(10)
		.execute()
				
		entries.each { IEntry entry ->
			println entry.getDocument().Subject
		}
		
		entries = database
		.view('publications_by_subject')
		.query('[Subject] CONTAINS Kragerø')
		.sortcolumn('Title')
		.ascending(true)
		.max(10)
		.execute()
		
		entries.each { IEntry entry ->
			println entry.Title
		}
	}

}

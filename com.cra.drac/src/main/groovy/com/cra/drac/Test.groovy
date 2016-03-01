package com.cra.drac

import com.cra.drac.api.Database
import com.cra.drac.api.RichText
import com.cra.drac.api.Session
import com.cra.drac.content.Content
import com.cra.drac.interfaces.IDatabase
import com.cra.drac.interfaces.IEntry
import com.cra.drac.interfaces.ISession

class Test {

	static main(args) {
		//ISession session = new Session().serverName('http://www.foo.bar/')
		//IDatabase database = new Database(session, 'cms/cms.nsf')

        ISession session = new Session().serverName('http://test.foo.bar/')
        IDatabase database = new Database(session, 'iSite/klepp/kleppk.nsf')
		
		int count = database
		.view('comments_by_parent')
        .key('4007D78434104A2DC1257D9D004A4606')
		.executeCount()

        println count
		
				
		/*Content content = database.createDocument(Content, [
			Form:'Content'	
		])
		content.status = 'STATUS.Draft'
		content.title = 'Test med rich text'
		content.owner = 'CN=Bjørn Tore Olsen Cintra/OU=WPC'
		content.validFrom = Calendar.instance.getTime()
		content.body = new RichText(data:'<p>Hello world</p>')
		content = content.save()
		
		println content.'@unid'
        */
		def entries = database
                .view('comments_by_parent')
                .key('4007D78434104A2DC1257D9D004A4606')
		.count(30)
		.executeMany(Content)
		
		entries.each { Content doc ->
			println doc.getRawFields()
		}
		

		//Content content = database.document().get('8C2E5EFA26A70931C1257C89007F9E72', Content)
		//content.title = 'Å hei, vi endrer'
		//content = content.save()
		//println content.getRawFields()
		
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

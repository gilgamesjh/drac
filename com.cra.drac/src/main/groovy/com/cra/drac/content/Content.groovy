package com.cra.drac.content

import com.cra.drac.api.Document
import com.cra.drac.interfaces.IDatabase
import com.cra.drac.interfaces.IDocument;

class Content extends Document {
	Content(IDatabase database, def rawDocument){
		super(database, rawDocument)
	}
	
	public IDocument save() {
		if(rawDocument.version){
			rawDocument.version ++
		} else {
			rawDocument.version = 1
		}
		return database.document().save(this)
	}
}

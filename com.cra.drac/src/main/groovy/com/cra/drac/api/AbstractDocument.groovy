package com.cra.drac.api

import com.cra.drac.interfaces.IDatabase
import com.cra.drac.interfaces.IDocument
import com.cra.drac.interfaces.ISession
import com.cra.drac.util.DocumentHandler

abstract class AbstractDocument {
	IDocument getDocument(IDatabase database, String unid){
		return new DocumentHandler(database).get(unid)
	}
}

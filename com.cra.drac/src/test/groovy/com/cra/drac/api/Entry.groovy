package com.cra.drac.api

import groovy.transform.ToString

import com.cra.drac.interfaces.IDatabase
import com.cra.drac.interfaces.IDocument
import com.cra.drac.interfaces.IEntry
import com.cra.drac.interfaces.ISession

@ToString
class Entry extends AbstractDocument implements IEntry {
	def rawEntry
	private ISession session
	private IDatabase database
	
	Entry(IDatabase database, rawEntry){
		this.rawEntry = rawEntry
		this.session = database.getSession()
		this.database = database
	}
	
	def propertyMissing(String name) { 
		return getItemValue(name)
	}
	
	
	
	@Override
	public List<String> getFieldNames() {
		return rawEntry.keySet().toArray()
	}

	@Override
	public Map<String, Object> getRawFields() {
		return rawEntry
	}

	@Override
	public Object getItemValue(String key) {
		if(rawEntry[key]){
			return rawEntry[key]
		}
		return null
	}

	@Override
	public IDocument getDocument() {
		getDocument(database, getItemValue('@unid'))
	}
	
	
}

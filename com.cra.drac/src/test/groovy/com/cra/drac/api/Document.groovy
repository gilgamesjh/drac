package com.cra.drac.api

import java.util.List;
import java.util.Map;

import groovy.transform.ToString

import com.cra.drac.interfaces.IDocument
import com.cra.drac.interfaces.IEntry
import com.cra.drac.interfaces.ISession

@ToString
class Document implements IDocument, IEntry {
	private ISession session
	def rawDocument
	
	Document(ISession session, def rawDocument){
		this.session = session
		this.rawDocument = rawDocument
	}
	
	def propertyMissing(String name) {
		return getItemValue(name)
	}

	@Override
	public IDocument getDocument() {
		return this
	}

	@Override
	public List<String> getFieldNames() {
		return rawDocument.keySet().toArray()
	}

	@Override
	public Map<String, Object> getRawFields() {
		return rawDocument
	}

	@Override
	public Object getItemValue(String key) {
		if(rawDocument[key]){
			return rawDocument[key]
		}
		return null
	}
}

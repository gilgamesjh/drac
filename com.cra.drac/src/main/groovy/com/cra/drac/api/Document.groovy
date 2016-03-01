package com.cra.drac.api

import groovy.transform.ToString

import org.apache.commons.lang3.StringEscapeUtils

import com.cra.drac.interfaces.IDatabase
import com.cra.drac.interfaces.IDocument
import com.cra.drac.interfaces.IEntry
import com.cra.drac.interfaces.ISession

@ToString
class Document implements IDocument, IEntry {
	ISession session
	IDatabase database
	def rawDocument
	
	Document(IDatabase database, def rawDocument){
		this.session = database.getSession()
		this.database = database
		this.rawDocument = [:]
		rawDocument.each { String k, v ->
			if(v instanceof Map){
				switch(v.type) {
					case 'datetime':
						this.rawDocument[k.toLowerCase()] = new Date().parse("yyyy-MM-dd'T'HH:mm:ss'Z'", v.data)
						break;
					case 'richtext':
						this.rawDocument[k.toLowerCase()] = new RichText(data: StringEscapeUtils.unescapeHtml4(v.data))
						break;
					default:
						this.rawDocument[k.toLowerCase()] = v
					
				}
			} else {
				this.rawDocument[k.toLowerCase()] = v
			}
		}
	}
	
	def propertyMissing(String name) {
		return getItemValue(name)
	}
	
	def propertyMissing(String name, value) { 
		rawDocument[name] = value 
	}
	
	def putAt(String name, value){
		rawDocument[name] = value
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

	@Override
	public void replaceValue(String name, Object value) {
		rawDocument[name] = value
		
	}

	@Override
	public IDocument save() {
		return database.document().save(this)
	}
}

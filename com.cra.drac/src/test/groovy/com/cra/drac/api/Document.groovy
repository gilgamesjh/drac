package com.cra.drac.api

import groovy.transform.ToString

import org.apache.commons.lang3.StringEscapeUtils

import com.cra.drac.interfaces.IDocument
import com.cra.drac.interfaces.IEntry
import com.cra.drac.interfaces.ISession

@ToString
class Document implements IDocument, IEntry {
	private ISession session
	def rawDocument
	
	Document(ISession session, def rawDocument){
		this.session = session
		this.rawDocument = [:]
		rawDocument.each { k, v ->
			if(v instanceof Map){
				switch(v.type) {
					case 'datetime':
						this.rawDocument[k] = new Date().parse("yyyy-MM-dd'T'HH:mm:ss'Z'", v.data)
						break;
					case 'richtext':
						this.rawDocument[k] = new RichText(data: StringEscapeUtils.unescapeHtml4(v.data))
						break;
					default:
						this.rawDocument[k] = v
					
				}
			} else {
				this.rawDocument[k] = v
			}
		}
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

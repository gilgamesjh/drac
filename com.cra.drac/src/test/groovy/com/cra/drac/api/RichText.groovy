package com.cra.drac.api

import com.cra.drac.interfaces.IRichText

class RichText implements IRichText{
	String data
	static final String TYPE = 'richtext' 
	
	@Override
	public void setData(String data) {
		this.data = data
	}

	@Override
	public String getData() {
		return data
	}

	@Override
	public String getType() {
		return TYPE
	}

	@Override
	String toString(){
		return data
	}
}

package com.cra.drac.interfaces;

public interface IDocument {
	void replaceValue(String name, Object value);
	IDocument save();
}

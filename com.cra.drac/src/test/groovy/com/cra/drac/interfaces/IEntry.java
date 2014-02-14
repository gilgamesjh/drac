package com.cra.drac.interfaces;

import java.util.List;
import java.util.Map;

public interface IEntry {
	IDocument getDocument();
	List<String> getFieldNames();
	Map<String,Object> getRawFields();
	Object getItemValue(String key);
}

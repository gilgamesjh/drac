package com.cra.drac.interfaces;

import java.util.Map;

public interface IDocumentHandler {
	IDocument get(String unid);
	IDocument get(String unid, Class<?> clazz);
	void delete();
	IDocument save(String unid, Map<String,Object> items, Class<?> clazz);
	IDocument save(IDocument document);
	IDocument save(String unid, Map<String,Object> items);
}

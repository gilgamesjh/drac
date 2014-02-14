package com.cra.drac.interfaces;

import java.util.Map;

public interface IDocumentHandler {
	IDocument get(String unid);
	void delete();
	IDocument patch(String unid, Map<String,Object> items);
	IDocument put(String unid, IDocument document);
	IDocument put(String unid, Map<String,Object> items);
}

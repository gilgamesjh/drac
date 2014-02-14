package com.cra.drac.interfaces;

import java.util.Date;
import java.util.List;

public interface IDatabase {
	IDocumentHandler document();
	IDatabase view(String view);
	IDatabase query(String query);
	IDatabase since(Date date);
	IDatabase max(int max);
	IDatabase sortcolumn(String columnName);
	IDatabase ascending(boolean ascending);
	IDatabase start(int start);
	IDatabase count(int count);
	IDatabase key(String key);
	List<IDocument> execute();
	String getDatabase();
	ISession getSession();
}

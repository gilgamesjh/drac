package com.cra.drac.interfaces;

import java.util.List;

public interface IDatabase {
	IDatabase view(String view);
	IDatabase query(String query);
	IDatabase start(int start);
	IDatabase count(int count);
	IDatabase key(String key);
	List<IDocument> execute();
}

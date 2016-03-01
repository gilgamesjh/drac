package com.cra.drac.interfaces;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface IDatabase {
	/**
	 * Gets a handle to the document manipulator, allowing to get/patch/delete a document 
	 * @return
	 */
	IDocumentHandler document();
	
	/**
	 * Sets a view for further processing
	 * @param view String name of view to use
	 * @return IDatabase for chaining
	 */
	IDatabase view(String view);
	
	/**
	 * Sets a ft-query to us for querying. If a view is also set, the query will be
	 * executed against this view
	 * @param query String with valid Domino ft-query
	 * @return IDatabase for chaining
	 */
	IDatabase query(String query);
		
	/**
	 * Sets a date to limit a query against. Ignored if query is not used
	 * @param date Date with limit date
	 * @return IDatabase for chaining
	 */
	IDatabase since(Date date);
	
	/**
	 * Sets the max elements to return in a search. Ignored if query is not used
	 * @param max int max returned
	 * @return IDatabase for chaining
	 */
	IDatabase max(int max);
	
	/**
	 * Name of a column to sort against in a query. Must be set to sortable in the view design.
	 * Ignored if query is not used
	 * @param columnName String name of column to sort against
	 * @return IDatabase for chaining
	 */
	IDatabase sortcolumn(String columnName);
	
	/**
	 * Allows to sort either ascending or descending. The sortable column must be set to sort accordingly
	 * in the view design. Ignored it query is not used.
	 * @param ascending boolean true for ascending, false for descending
	 * @return IDatabase for chaining
	 */
	IDatabase ascending(boolean ascending);
	
	/**
	 * Where in the backing view the result starts.
	 * @param start int start position
	 * @return IDatabase for chaining
	 */
	IDatabase start(int start);
	
	/**
	 * How many elements to return
	 * @param count int number of elements
	 * @return IDatabase for chaining
	 */
	IDatabase count(int count);
	
	/**
	 * Used to limit the result against a key. Ignored when query is used
	 * @param key String key
	 * @return IDatabase for chaining
	 */
	IDatabase key(String key);
	
	/**
	 * Builds and executes a query with the given parameters. It will also reset all previous choices.
	 * @return List of IEntry elements from a view
	 */
	List<IEntry> execute();
	
	/**
	 * Builds and executes a query with the given parameters. Returns the first document from the list of entries.
	 * Automatically sets the count to 1, but allows start 
	 * @return Document
	 */
	IDocument executeOne();
	
	/**
	 * Same as executeOne(), but allows to override the IDocument to allow for custom types
	 * @param clazz Class that extends Document
	 * @return A single IDocument
	 */
	IDocument executeOne(Class<?> clazz);
	
	/**
	 * Builds and executes a query, returns a list of IDocuments (Document). This will fetch a matching IDoument for
	 * each IEntry that is returned, so if you only need a subset of IDocuments, use execute() instead, and get the
	 * documents yourself.
	 * @return List of IDocument (Document)
	 */
	List<IDocument> executeMany();
	
	/**
	 * Same as executeMany(), but allows to override the returned IDocument.
	 * @param clazz Class that extends Document
	 * @return List of IDocument
	 */
	List<IDocument> executeMany(Class<?> clazz);

    /**
     * Executes the query, but only returns the headers for counting
     * @return integer with coutn or 0 if not present in result
     */
    int executeCount();

	/**
	 * Current database path
	 * @return String with database path
	 */
	String getDatabase();
	
	/**
	 * Current session
	 * @return ISession with current session
	 */
	ISession getSession();
	
	IDocument createDocument();
	
	IDocument createDocument(Class<?> clazz);
	
	IDocument createDocument(Class<?> clazz, Map<String,Object> initialValue);
}

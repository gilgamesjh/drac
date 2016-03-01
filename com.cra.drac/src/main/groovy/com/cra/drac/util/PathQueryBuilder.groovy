package com.cra.drac.util

import java.text.SimpleDateFormat

import com.cra.drac.interfaces.IDatabase

class PathQueryBuilder {
	IDatabase database
	
	PathQueryBuilder(IDatabase database){
		this.database = database
	}
	
	String build(){
		return buildCount() + "&entrycount=false"
	}

    String buildCount(){
        // build a url
        String url = database.databasePath
        if(database.view){
            url += "collections/name/${database.view}?compact=true"
            if(database.key){
                url += "&keys=${database.key}"
            }
            if(database.query){ // ft query inside the view
                url += "&search=${URLEncoder.encode(database.query,'UTF-8')}&searchmaxdocs=${database.max+2}"
            }
            if(database.columnName){
                url += "&sortcolumn=${database.columnName}&sortorder=${database.ascending?'ascending':'descending'}"
            }
            url += "&start=${database.start}&count=${database.count}"
        } else if(database.query){
            url += "documents?compact=true&search=${URLEncoder.encode(database.query,'UTF-8')}&searchmaxdocs=${database.max+2}"
            if(database.date){
                SimpleDateFormat dfe = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                url += "&since=${dfe.format(database.date)}"
            }
        }
        return url
    }
}

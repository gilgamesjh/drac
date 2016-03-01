package com.cra.drac.ws

import com.ning.http.client.AsyncHandler
import com.ning.http.client.HttpResponseBodyPart
import com.ning.http.client.HttpResponseHeaders
import com.ning.http.client.HttpResponseStatus
import com.sun.net.httpserver.Headers

/**
 * Created by bcintra on 01.03.16.
 */
class CountAsyncHandler<Integer> implements AsyncHandler {
    private Integer count = 0

    @Override
    void onThrowable(Throwable t) {

    }

    @Override
    AsyncHandler.STATE onBodyPartReceived(HttpResponseBodyPart bodyPart) throws Exception {
        return AsyncHandler.STATE.ABORT
    }

    @Override
    AsyncHandler.STATE onStatusReceived(HttpResponseStatus responseStatus) throws Exception {
        int statusCode = responseStatus.getStatusCode();
        if(statusCode==200){
            return AsyncHandler.STATE.CONTINUE
        }
        return AsyncHandler.STATE.ABORT
    }

    @Override
    AsyncHandler.STATE onHeadersReceived(HttpResponseHeaders headers) throws Exception {
        if(headers.getHeaders().containsKey('Content-Range')){
            String contentRange = headers.getHeaders().getFirstValue('Content-Range')
            if(contentRange.indexOf('/')>-1){
                String countPart = contentRange.substring(contentRange.indexOf('/')+1)
                try {
                    count = countPart.toInteger()
                } catch (NumberFormatException nfe){
                    count = 0
                }
            }

        }
        return AsyncHandler.STATE.ABORT
    }

    @Override
    Integer onCompleted() throws Exception {
        return count
    }
}

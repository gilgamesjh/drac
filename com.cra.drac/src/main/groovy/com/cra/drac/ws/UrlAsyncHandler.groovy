package com.cra.drac.ws

import com.ning.http.client.AsyncHandler
import com.ning.http.client.HttpResponseBodyPart
import com.ning.http.client.HttpResponseHeaders
import com.ning.http.client.HttpResponseStatus

/**
 * Created by bcintra on 05.05.15.
 */
class UrlAsyncHandler<String> implements AsyncHandler{
    private ByteArrayOutputStream bytes = new ByteArrayOutputStream();

    @Override
    public AsyncHandler.STATE onStatusReceived(HttpResponseStatus status) throws Exception {
        int statusCode = status.getStatusCode();
        // The Status have been read
        // If you don't want to read the headers,body or stop processing the response
        if (statusCode == 200) { // only continue on 200
            return AsyncHandler.STATE.CONTINUE;
        }
        return AsyncHandler.STATE.ABORT;
    }

    @Override
    public AsyncHandler.STATE onHeadersReceived(HttpResponseHeaders h) throws Exception {
        // The headers have been read
        return AsyncHandler.STATE.CONTINUE;
    }

    @Override
    public AsyncHandler.STATE onBodyPartReceived(HttpResponseBodyPart bodyPart) throws Exception {
        bytes.write(bodyPart.getBodyPartBytes());
        return AsyncHandler.STATE.CONTINUE;
    }

    @Override
    public String onCompleted() throws Exception {
        // Will be invoked once the response has been fully read or a ResponseComplete exception
        // has been thrown.
        // NOTE: should probably use Content-Encoding from headers
        return bytes.toString("UTF-8");
    }

    @Override
    public void onThrowable(Throwable t) {
    }
}

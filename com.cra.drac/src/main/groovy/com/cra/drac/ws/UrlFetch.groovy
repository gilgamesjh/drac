package com.cra.drac.ws

import com.ning.http.client.AsyncHttpClient
import com.ning.http.client.AsyncHttpClientConfig

import java.util.concurrent.Future

/**
 * Created by bcintra on 05.05.15.
 */
class UrlFetch {
    final static int WS_CONNECT_TIMEOUT = 5000
    final static int WS_REQUEST_TIMEOUT = 5000

    static String get(String url, int connectTimeout, int requestTimeout){
        AsyncHttpClientConfig cf = new AsyncHttpClientConfig.Builder()
                .setAcceptAnyCertificate(true)
                .setConnectTimeout(connectTimeout)
                .setRequestTimeout(requestTimeout)
                .build()

        AsyncHttpClient c = new AsyncHttpClient(cf);
        Future<String> f = c.prepareGet(url).execute(new UrlAsyncHandler<String>());

        return f.get();
    }

    static String get(String url){
        return get(url, WS_CONNECT_TIMEOUT, WS_REQUEST_TIMEOUT)
    }

    static int getCount(String url){
        AsyncHttpClientConfig cf = new AsyncHttpClientConfig.Builder()
                .setAcceptAnyCertificate(true)
                .setConnectTimeout(WS_CONNECT_TIMEOUT)
                .setRequestTimeout(WS_REQUEST_TIMEOUT)
                .build()

        AsyncHttpClient c = new AsyncHttpClient(cf);
        Future<Integer> f = c.prepareGet(url).execute(new CountAsyncHandler<Integer>());

        return f.get();
    }
}

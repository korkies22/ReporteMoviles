/*
 * Decompiled with CFR 0_134.
 */
package io.fabric.sdk.android.services.network;

import io.fabric.sdk.android.services.network.HttpRequest;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;

public static interface HttpRequest.ConnectionFactory {
    public static final HttpRequest.ConnectionFactory DEFAULT = new HttpRequest.ConnectionFactory(){

        @Override
        public HttpURLConnection create(URL uRL) throws IOException {
            return (HttpURLConnection)uRL.openConnection();
        }

        @Override
        public HttpURLConnection create(URL uRL, Proxy proxy) throws IOException {
            return (HttpURLConnection)uRL.openConnection(proxy);
        }
    };

    public HttpURLConnection create(URL var1) throws IOException;

    public HttpURLConnection create(URL var1, Proxy var2) throws IOException;

}

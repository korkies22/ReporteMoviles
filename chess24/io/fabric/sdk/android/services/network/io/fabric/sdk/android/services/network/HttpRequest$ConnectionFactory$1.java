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

static final class HttpRequest.ConnectionFactory
implements HttpRequest.ConnectionFactory {
    HttpRequest.ConnectionFactory() {
    }

    @Override
    public HttpURLConnection create(URL uRL) throws IOException {
        return (HttpURLConnection)uRL.openConnection();
    }

    @Override
    public HttpURLConnection create(URL uRL, Proxy proxy) throws IOException {
        return (HttpURLConnection)uRL.openConnection(proxy);
    }
}

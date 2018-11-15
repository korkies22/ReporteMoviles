/*
 * Decompiled with CFR 0_134.
 */
package com.facebook.internal;

import com.facebook.internal.ImageResponseCache;
import com.facebook.internal.Utility;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;

private static class ImageResponseCache.BufferedHttpInputStream
extends BufferedInputStream {
    HttpURLConnection connection;

    ImageResponseCache.BufferedHttpInputStream(InputStream inputStream, HttpURLConnection httpURLConnection) {
        super(inputStream, 8192);
        this.connection = httpURLConnection;
    }

    @Override
    public void close() throws IOException {
        super.close();
        Utility.disconnectQuietly(this.connection);
    }
}

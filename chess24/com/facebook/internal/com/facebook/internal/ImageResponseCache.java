/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.net.Uri
 */
package com.facebook.internal;

import android.content.Context;
import android.net.Uri;
import com.facebook.LoggingBehavior;
import com.facebook.internal.FileLruCache;
import com.facebook.internal.Logger;
import com.facebook.internal.Utility;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

class ImageResponseCache {
    static final String TAG = "ImageResponseCache";
    private static FileLruCache imageCache;

    ImageResponseCache() {
    }

    static void clearCache(Context context) {
        try {
            ImageResponseCache.getCache(context).clearCache();
            return;
        }
        catch (IOException iOException) {
            LoggingBehavior loggingBehavior = LoggingBehavior.CACHE;
            String string = TAG;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("clearCache failed ");
            stringBuilder.append(iOException.getMessage());
            Logger.log(loggingBehavior, 5, string, stringBuilder.toString());
            return;
        }
    }

    static FileLruCache getCache(Context object) throws IOException {
        synchronized (ImageResponseCache.class) {
            if (imageCache == null) {
                imageCache = new FileLruCache(TAG, new FileLruCache.Limits());
            }
            object = imageCache;
            return object;
        }
    }

    static InputStream getCachedImageStream(Uri object, Context context) {
        if (object != null && ImageResponseCache.isCDNURL(object)) {
            try {
                object = ImageResponseCache.getCache(context).get(object.toString());
                return object;
            }
            catch (IOException iOException) {
                Logger.log(LoggingBehavior.CACHE, 5, TAG, iOException.toString());
            }
        }
        return null;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    static InputStream interceptAndCacheImageStream(Context object, HttpURLConnection httpURLConnection) throws IOException {
        InputStream inputStream2;
        if (httpURLConnection.getResponseCode() != 200) return null;
        Uri uri = Uri.parse((String)httpURLConnection.getURL().toString());
        InputStream inputStream = inputStream2 = httpURLConnection.getInputStream();
        try {
            if (!ImageResponseCache.isCDNURL(uri)) return inputStream;
            return ImageResponseCache.getCache((Context)object).interceptAndPut(uri.toString(), new BufferedHttpInputStream(inputStream2, httpURLConnection));
        }
        catch (IOException iOException) {
            return inputStream2;
        }
    }

    private static boolean isCDNURL(Uri object) {
        if (object != null) {
            if ((object = object.getHost()).endsWith("fbcdn.net")) {
                return true;
            }
            if (object.startsWith("fbcdn") && object.endsWith("akamaihd.net")) {
                return true;
            }
        }
        return false;
    }

    private static class BufferedHttpInputStream
    extends BufferedInputStream {
        HttpURLConnection connection;

        BufferedHttpInputStream(InputStream inputStream, HttpURLConnection httpURLConnection) {
            super(inputStream, 8192);
            this.connection = httpURLConnection;
        }

        @Override
        public void close() throws IOException {
            super.close();
            Utility.disconnectQuietly(this.connection);
        }
    }

}

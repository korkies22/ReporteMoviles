/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  org.json.JSONException
 *  org.json.JSONObject
 *  org.json.JSONTokener
 */
package com.facebook.internal;

import com.facebook.LoggingBehavior;
import com.facebook.internal.FileLruCache;
import com.facebook.internal.Logger;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

private static final class FileLruCache.StreamHeader {
    private static final int HEADER_VERSION = 0;

    private FileLruCache.StreamHeader() {
    }

    static JSONObject readHeader(InputStream object) throws IOException {
        block7 : {
            int n;
            if (object.read() != 0) {
                return null;
            }
            int n2 = 0;
            int n3 = n = 0;
            while (n < 3) {
                int n4 = object.read();
                if (n4 == -1) {
                    Logger.log(LoggingBehavior.CACHE, FileLruCache.TAG, "readHeader: stream.read returned -1 while reading header size");
                    return null;
                }
                n3 = (n3 << 8) + (n4 & 255);
                ++n;
            }
            Object object2 = new byte[n3];
            for (n = n2; n < ((byte[])object2).length; n += n3) {
                n3 = object.read((byte[])object2, n, ((byte[])object2).length - n);
                if (n3 >= 1) continue;
                object = LoggingBehavior.CACHE;
                String string = FileLruCache.TAG;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("readHeader: stream.read stopped at ");
                stringBuilder.append((Object)n);
                stringBuilder.append(" when expected ");
                stringBuilder.append(((byte[])object2).length);
                Logger.log((LoggingBehavior)((Object)object), string, stringBuilder.toString());
                return null;
            }
            object = new JSONTokener(new String((byte[])object2));
            try {
                object = object.nextValue();
                if (object instanceof JSONObject) break block7;
                object2 = LoggingBehavior.CACHE;
                String string = FileLruCache.TAG;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("readHeader: expected JSONObject, got ");
                stringBuilder.append(object.getClass().getCanonicalName());
                Logger.log((LoggingBehavior)((Object)object2), string, stringBuilder.toString());
                return null;
            }
            catch (JSONException jSONException) {
                throw new IOException(jSONException.getMessage());
            }
        }
        object = (JSONObject)object;
        return object;
    }

    static void writeHeader(OutputStream outputStream, JSONObject arrby) throws IOException {
        arrby = arrby.toString().getBytes();
        outputStream.write(0);
        outputStream.write(arrby.length >> 16 & 255);
        outputStream.write(arrby.length >> 8 & 255);
        outputStream.write(arrby.length >> 0 & 255);
        outputStream.write(arrby);
    }
}

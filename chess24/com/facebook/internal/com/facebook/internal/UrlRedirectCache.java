/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.net.Uri
 */
package com.facebook.internal;

import android.net.Uri;
import com.facebook.LoggingBehavior;
import com.facebook.internal.FileLruCache;
import com.facebook.internal.Logger;
import com.facebook.internal.Utility;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

class UrlRedirectCache {
    private static final String REDIRECT_CONTENT_TAG;
    static final String TAG = "UrlRedirectCache";
    private static FileLruCache urlRedirectCache;

    static {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(TAG);
        stringBuilder.append("_Redirect");
        REDIRECT_CONTENT_TAG = stringBuilder.toString();
    }

    UrlRedirectCache() {
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    static void cacheUriRedirect(Uri object, Uri uri) {
        block8 : {
            void var1_5;
            Object var3_7;
            block7 : {
                if (object == null) return;
                if (uri == null) {
                    return;
                }
                var3_7 = null;
                Object var2_8 = null;
                object = UrlRedirectCache.getCache().openPutStream(object.toString(), REDIRECT_CONTENT_TAG);
                try {
                    object.write(uri.toString().getBytes());
                }
                catch (Throwable throwable) {
                    break block7;
                }
                Utility.closeQuietly((Closeable)object);
                return;
                catch (Throwable throwable) {
                    object = var2_8;
                }
            }
            Utility.closeQuietly((Closeable)object);
            throw var1_5;
            catch (IOException iOException) {
                object = var3_7;
                break block8;
            }
            catch (IOException iOException) {}
        }
        Utility.closeQuietly((Closeable)object);
    }

    static void clearCache() {
        try {
            UrlRedirectCache.getCache().clearCache();
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

    static FileLruCache getCache() throws IOException {
        synchronized (UrlRedirectCache.class) {
            if (urlRedirectCache == null) {
                urlRedirectCache = new FileLruCache(TAG, new FileLruCache.Limits());
            }
            FileLruCache fileLruCache = urlRedirectCache;
            return fileLruCache;
        }
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    static Uri getRedirectedUri(Uri object) {
        Object object2;
        block16 : {
            block15 : {
                FileLruCache fileLruCache;
                boolean bl;
                block14 : {
                    if (object == null) {
                        return null;
                    }
                    object2 = object.toString();
                    try {
                        fileLruCache = UrlRedirectCache.getCache();
                        object = null;
                        bl = false;
                        break block14;
                    }
                    catch (Throwable throwable) {
                        object2 = null;
                        break block15;
                    }
                    catch (IOException iOException) {}
                    object2 = null;
                    break block16;
                }
                do {
                    Object object3 = fileLruCache.get((String)object2, REDIRECT_CONTENT_TAG);
                    if (object3 == null) break;
                    bl = true;
                    object2 = new InputStreamReader((InputStream)object3);
                    try {
                        int n;
                        object = new char[128];
                        object3 = new StringBuilder();
                        while ((n = object2.read((char[])object, 0, ((Object)object).length)) > 0) {
                            object3.append((char[])object, 0, n);
                        }
                        Utility.closeQuietly((Closeable)object2);
                        object3 = object3.toString();
                        object = object2;
                        object2 = object3;
                    }
                    catch (Throwable throwable) {
                        break block15;
                    }
                } while (true);
                if (!bl) {
                    Utility.closeQuietly((Closeable)object);
                    return null;
                }
                try {
                    object2 = Uri.parse((String)object2);
                }
                catch (Throwable throwable) {
                    object2 = object;
                    object = throwable;
                }
                Utility.closeQuietly((Closeable)object);
                return object2;
            }
            Utility.closeQuietly((Closeable)object2);
            throw object;
            catch (IOException iOException) {}
            object2 = object;
            break block16;
            catch (IOException iOException) {}
        }
        Utility.closeQuietly((Closeable)object2);
        return null;
    }
}

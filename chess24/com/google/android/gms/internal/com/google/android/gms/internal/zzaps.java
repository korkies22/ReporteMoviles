/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.StrictMode
 *  android.os.StrictMode$ThreadPolicy
 */
package com.google.android.gms.internal;

import android.os.StrictMode;
import java.util.concurrent.Callable;

public class zzaps {
    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static <T> T zzb(Callable<T> callable) {
        T t;
        StrictMode.ThreadPolicy threadPolicy = StrictMode.getThreadPolicy();
        try {
            StrictMode.setThreadPolicy((StrictMode.ThreadPolicy)StrictMode.ThreadPolicy.LAX);
            t = callable.call();
        }
        catch (Throwable throwable) {
            StrictMode.setThreadPolicy((StrictMode.ThreadPolicy)threadPolicy);
            throw throwable;
        }
        catch (Throwable throwable) {}
        StrictMode.setThreadPolicy((StrictMode.ThreadPolicy)threadPolicy);
        return t;
        StrictMode.setThreadPolicy((StrictMode.ThreadPolicy)threadPolicy);
        return null;
    }
}

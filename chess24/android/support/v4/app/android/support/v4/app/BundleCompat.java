/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Bundle
 *  android.os.IBinder
 *  android.util.Log
 */
package android.support.v4.app;

import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public final class BundleCompat {
    private BundleCompat() {
    }

    @Nullable
    public static IBinder getBinder(@NonNull Bundle bundle, @Nullable String string) {
        if (Build.VERSION.SDK_INT >= 18) {
            return bundle.getBinder(string);
        }
        return BundleCompatBaseImpl.getBinder(bundle, string);
    }

    public static void putBinder(@NonNull Bundle bundle, @Nullable String string, @Nullable IBinder iBinder) {
        if (Build.VERSION.SDK_INT >= 18) {
            bundle.putBinder(string, iBinder);
            return;
        }
        BundleCompatBaseImpl.putBinder(bundle, string, iBinder);
    }

    static class BundleCompatBaseImpl {
        private static final String TAG = "BundleCompatBaseImpl";
        private static Method sGetIBinderMethod;
        private static boolean sGetIBinderMethodFetched;
        private static Method sPutIBinderMethod;
        private static boolean sPutIBinderMethodFetched;

        BundleCompatBaseImpl() {
        }

        public static IBinder getBinder(Bundle bundle, String string) {
            if (!sGetIBinderMethodFetched) {
                try {
                    sGetIBinderMethod = Bundle.class.getMethod("getIBinder", String.class);
                    sGetIBinderMethod.setAccessible(true);
                }
                catch (NoSuchMethodException noSuchMethodException) {
                    Log.i((String)TAG, (String)"Failed to retrieve getIBinder method", (Throwable)noSuchMethodException);
                }
                sGetIBinderMethodFetched = true;
            }
            if (sGetIBinderMethod != null) {
                try {
                    bundle = (IBinder)sGetIBinderMethod.invoke((Object)bundle, string);
                    return bundle;
                }
                catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException exception) {
                    Log.i((String)TAG, (String)"Failed to invoke getIBinder via reflection", (Throwable)exception);
                    sGetIBinderMethod = null;
                }
            }
            return null;
        }

        public static void putBinder(Bundle bundle, String string, IBinder iBinder) {
            if (!sPutIBinderMethodFetched) {
                try {
                    sPutIBinderMethod = Bundle.class.getMethod("putIBinder", String.class, IBinder.class);
                    sPutIBinderMethod.setAccessible(true);
                }
                catch (NoSuchMethodException noSuchMethodException) {
                    Log.i((String)TAG, (String)"Failed to retrieve putIBinder method", (Throwable)noSuchMethodException);
                }
                sPutIBinderMethodFetched = true;
            }
            if (sPutIBinderMethod != null) {
                try {
                    sPutIBinderMethod.invoke((Object)bundle, new Object[]{string, iBinder});
                    return;
                }
                catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException exception) {
                    Log.i((String)TAG, (String)"Failed to invoke putIBinder via reflection", (Throwable)exception);
                    sPutIBinderMethod = null;
                }
            }
        }
    }

}

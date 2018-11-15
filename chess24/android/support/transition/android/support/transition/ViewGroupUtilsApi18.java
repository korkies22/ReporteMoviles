/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.util.Log
 *  android.view.ViewGroup
 */
package android.support.transition;

import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.transition.ViewGroupOverlayApi18;
import android.support.transition.ViewGroupOverlayImpl;
import android.support.transition.ViewGroupUtilsApi14;
import android.util.Log;
import android.view.ViewGroup;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@RequiresApi(value=18)
class ViewGroupUtilsApi18
extends ViewGroupUtilsApi14 {
    private static final String TAG = "ViewUtilsApi18";
    private static Method sSuppressLayoutMethod;
    private static boolean sSuppressLayoutMethodFetched;

    ViewGroupUtilsApi18() {
    }

    private void fetchSuppressLayoutMethod() {
        if (!sSuppressLayoutMethodFetched) {
            try {
                sSuppressLayoutMethod = ViewGroup.class.getDeclaredMethod("suppressLayout", Boolean.TYPE);
                sSuppressLayoutMethod.setAccessible(true);
            }
            catch (NoSuchMethodException noSuchMethodException) {
                Log.i((String)TAG, (String)"Failed to retrieve suppressLayout method", (Throwable)noSuchMethodException);
            }
            sSuppressLayoutMethodFetched = true;
        }
    }

    @Override
    public ViewGroupOverlayImpl getOverlay(@NonNull ViewGroup viewGroup) {
        return new ViewGroupOverlayApi18(viewGroup);
    }

    @Override
    public void suppressLayout(@NonNull ViewGroup viewGroup, boolean bl) {
        this.fetchSuppressLayoutMethod();
        if (sSuppressLayoutMethod != null) {
            try {
                sSuppressLayoutMethod.invoke((Object)viewGroup, bl);
                return;
            }
            catch (InvocationTargetException invocationTargetException) {
                Log.i((String)TAG, (String)"Error invoking suppressLayout method", (Throwable)invocationTargetException);
                return;
            }
            catch (IllegalAccessException illegalAccessException) {
                Log.i((String)TAG, (String)"Failed to invoke suppressLayout method", (Throwable)illegalAccessException);
            }
        }
    }
}

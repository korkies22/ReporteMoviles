/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.util.Log
 *  android.view.View
 */
package android.support.transition;

import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.transition.ViewUtilsApi18;
import android.util.Log;
import android.view.View;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@RequiresApi(value=19)
class ViewUtilsApi19
extends ViewUtilsApi18 {
    private static final String TAG = "ViewUtilsApi19";
    private static Method sGetTransitionAlphaMethod;
    private static boolean sGetTransitionAlphaMethodFetched;
    private static Method sSetTransitionAlphaMethod;
    private static boolean sSetTransitionAlphaMethodFetched;

    ViewUtilsApi19() {
    }

    private void fetchGetTransitionAlphaMethod() {
        if (!sGetTransitionAlphaMethodFetched) {
            try {
                sGetTransitionAlphaMethod = View.class.getDeclaredMethod("getTransitionAlpha", new Class[0]);
                sGetTransitionAlphaMethod.setAccessible(true);
            }
            catch (NoSuchMethodException noSuchMethodException) {
                Log.i((String)TAG, (String)"Failed to retrieve getTransitionAlpha method", (Throwable)noSuchMethodException);
            }
            sGetTransitionAlphaMethodFetched = true;
        }
    }

    private void fetchSetTransitionAlphaMethod() {
        if (!sSetTransitionAlphaMethodFetched) {
            try {
                sSetTransitionAlphaMethod = View.class.getDeclaredMethod("setTransitionAlpha", Float.TYPE);
                sSetTransitionAlphaMethod.setAccessible(true);
            }
            catch (NoSuchMethodException noSuchMethodException) {
                Log.i((String)TAG, (String)"Failed to retrieve setTransitionAlpha method", (Throwable)noSuchMethodException);
            }
            sSetTransitionAlphaMethodFetched = true;
        }
    }

    @Override
    public void clearNonTransitionAlpha(@NonNull View view) {
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public float getTransitionAlpha(@NonNull View view) {
        this.fetchGetTransitionAlphaMethod();
        if (sGetTransitionAlphaMethod == null) return super.getTransitionAlpha(view);
        try {
            return ((Float)sGetTransitionAlphaMethod.invoke((Object)view, new Object[0])).floatValue();
        }
        catch (InvocationTargetException invocationTargetException) {
            throw new RuntimeException(invocationTargetException.getCause());
        }
        catch (IllegalAccessException illegalAccessException) {
            return super.getTransitionAlpha(view);
        }
    }

    @Override
    public void saveNonTransitionAlpha(@NonNull View view) {
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void setTransitionAlpha(@NonNull View view, float f) {
        this.fetchSetTransitionAlphaMethod();
        if (sSetTransitionAlphaMethod == null) {
            view.setAlpha(f);
            return;
        }
        try {
            sSetTransitionAlphaMethod.invoke((Object)view, Float.valueOf(f));
            return;
        }
        catch (InvocationTargetException invocationTargetException) {
            throw new RuntimeException(invocationTargetException.getCause());
        }
        catch (IllegalAccessException illegalAccessException) {
            return;
        }
    }
}

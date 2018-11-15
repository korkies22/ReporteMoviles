/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.graphics.Matrix
 *  android.util.Log
 *  android.view.View
 */
package android.support.transition;

import android.graphics.Matrix;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.transition.ViewUtilsApi19;
import android.util.Log;
import android.view.View;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@RequiresApi(value=21)
class ViewUtilsApi21
extends ViewUtilsApi19 {
    private static final String TAG = "ViewUtilsApi21";
    private static Method sSetAnimationMatrixMethod;
    private static boolean sSetAnimationMatrixMethodFetched;
    private static Method sTransformMatrixToGlobalMethod;
    private static boolean sTransformMatrixToGlobalMethodFetched;
    private static Method sTransformMatrixToLocalMethod;
    private static boolean sTransformMatrixToLocalMethodFetched;

    ViewUtilsApi21() {
    }

    private void fetchSetAnimationMatrix() {
        if (!sSetAnimationMatrixMethodFetched) {
            try {
                sSetAnimationMatrixMethod = View.class.getDeclaredMethod("setAnimationMatrix", Matrix.class);
                sSetAnimationMatrixMethod.setAccessible(true);
            }
            catch (NoSuchMethodException noSuchMethodException) {
                Log.i((String)TAG, (String)"Failed to retrieve setAnimationMatrix method", (Throwable)noSuchMethodException);
            }
            sSetAnimationMatrixMethodFetched = true;
        }
    }

    private void fetchTransformMatrixToGlobalMethod() {
        if (!sTransformMatrixToGlobalMethodFetched) {
            try {
                sTransformMatrixToGlobalMethod = View.class.getDeclaredMethod("transformMatrixToGlobal", Matrix.class);
                sTransformMatrixToGlobalMethod.setAccessible(true);
            }
            catch (NoSuchMethodException noSuchMethodException) {
                Log.i((String)TAG, (String)"Failed to retrieve transformMatrixToGlobal method", (Throwable)noSuchMethodException);
            }
            sTransformMatrixToGlobalMethodFetched = true;
        }
    }

    private void fetchTransformMatrixToLocalMethod() {
        if (!sTransformMatrixToLocalMethodFetched) {
            try {
                sTransformMatrixToLocalMethod = View.class.getDeclaredMethod("transformMatrixToLocal", Matrix.class);
                sTransformMatrixToLocalMethod.setAccessible(true);
            }
            catch (NoSuchMethodException noSuchMethodException) {
                Log.i((String)TAG, (String)"Failed to retrieve transformMatrixToLocal method", (Throwable)noSuchMethodException);
            }
            sTransformMatrixToLocalMethodFetched = true;
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public void setAnimationMatrix(@NonNull View view, Matrix matrix) {
        this.fetchSetAnimationMatrix();
        if (sSetAnimationMatrixMethod == null) return;
        try {
            sSetAnimationMatrixMethod.invoke((Object)view, new Object[]{matrix});
            return;
        }
        catch (IllegalAccessException illegalAccessException) {
            throw new RuntimeException(illegalAccessException.getCause());
        }
        catch (InvocationTargetException invocationTargetException) {
            return;
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public void transformMatrixToGlobal(@NonNull View view, @NonNull Matrix matrix) {
        this.fetchTransformMatrixToGlobalMethod();
        if (sTransformMatrixToGlobalMethod == null) return;
        try {
            sTransformMatrixToGlobalMethod.invoke((Object)view, new Object[]{matrix});
            return;
        }
        catch (InvocationTargetException invocationTargetException) {
            throw new RuntimeException(invocationTargetException.getCause());
        }
        catch (IllegalAccessException illegalAccessException) {
            return;
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public void transformMatrixToLocal(@NonNull View view, @NonNull Matrix matrix) {
        this.fetchTransformMatrixToLocalMethod();
        if (sTransformMatrixToLocalMethod == null) return;
        try {
            sTransformMatrixToLocalMethod.invoke((Object)view, new Object[]{matrix});
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

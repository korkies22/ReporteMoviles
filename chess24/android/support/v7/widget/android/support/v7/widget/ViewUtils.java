/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.graphics.Rect
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.util.Log
 *  android.view.View
 */
package android.support.v7.widget;

import android.graphics.Rect;
import android.os.Build;
import android.support.annotation.RestrictTo;
import android.support.v4.view.ViewCompat;
import android.util.Log;
import android.view.View;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
public class ViewUtils {
    private static final String TAG = "ViewUtils";
    private static Method sComputeFitSystemWindowsMethod;

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    static {
        if (Build.VERSION.SDK_INT < 18) return;
        try {
            sComputeFitSystemWindowsMethod = View.class.getDeclaredMethod("computeFitSystemWindows", Rect.class, Rect.class);
            if (sComputeFitSystemWindowsMethod.isAccessible()) return;
            sComputeFitSystemWindowsMethod.setAccessible(true);
            return;
        }
        catch (NoSuchMethodException noSuchMethodException) {}
        Log.d((String)TAG, (String)"Could not find method computeFitSystemWindows. Oh well.");
    }

    private ViewUtils() {
    }

    public static void computeFitSystemWindows(View view, Rect rect, Rect rect2) {
        if (sComputeFitSystemWindowsMethod != null) {
            try {
                sComputeFitSystemWindowsMethod.invoke((Object)view, new Object[]{rect, rect2});
                return;
            }
            catch (Exception exception) {
                Log.d((String)TAG, (String)"Could not invoke computeFitSystemWindows", (Throwable)exception);
            }
        }
    }

    public static boolean isLayoutRtl(View view) {
        if (ViewCompat.getLayoutDirection(view) == 1) {
            return true;
        }
        return false;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static void makeOptionalFitsSystemWindows(View view) {
        if (Build.VERSION.SDK_INT >= 16) {
            try {
                Method method = view.getClass().getMethod("makeOptionalFitsSystemWindows", new Class[0]);
                if (!method.isAccessible()) {
                    method.setAccessible(true);
                }
                method.invoke((Object)view, new Object[0]);
                return;
            }
            catch (IllegalAccessException illegalAccessException) {
                Log.d((String)TAG, (String)"Could not invoke makeOptionalFitsSystemWindows", (Throwable)illegalAccessException);
                return;
            }
            catch (InvocationTargetException invocationTargetException) {
                Log.d((String)TAG, (String)"Could not invoke makeOptionalFitsSystemWindows", (Throwable)invocationTargetException);
                return;
            }
            catch (NoSuchMethodException noSuchMethodException) {}
            Log.d((String)TAG, (String)"Could not find method makeOptionalFitsSystemWindows. Oh well...");
        }
    }
}

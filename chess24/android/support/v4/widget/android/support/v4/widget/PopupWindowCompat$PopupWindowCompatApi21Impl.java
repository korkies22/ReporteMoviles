/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.util.Log
 *  android.widget.PopupWindow
 */
package android.support.v4.widget;

import android.support.annotation.RequiresApi;
import android.support.v4.widget.PopupWindowCompat;
import android.util.Log;
import android.widget.PopupWindow;
import java.lang.reflect.Field;

@RequiresApi(value=21)
static class PopupWindowCompat.PopupWindowCompatApi21Impl
extends PopupWindowCompat.PopupWindowCompatApi19Impl {
    private static final String TAG = "PopupWindowCompatApi21";
    private static Field sOverlapAnchorField;

    static {
        try {
            sOverlapAnchorField = PopupWindow.class.getDeclaredField("mOverlapAnchor");
            sOverlapAnchorField.setAccessible(true);
        }
        catch (NoSuchFieldException noSuchFieldException) {
            Log.i((String)TAG, (String)"Could not fetch mOverlapAnchor field from PopupWindow", (Throwable)noSuchFieldException);
        }
    }

    PopupWindowCompat.PopupWindowCompatApi21Impl() {
    }

    @Override
    public boolean getOverlapAnchor(PopupWindow popupWindow) {
        if (sOverlapAnchorField != null) {
            try {
                boolean bl = (Boolean)sOverlapAnchorField.get((Object)popupWindow);
                return bl;
            }
            catch (IllegalAccessException illegalAccessException) {
                Log.i((String)TAG, (String)"Could not get overlap anchor field in PopupWindow", (Throwable)illegalAccessException);
            }
        }
        return false;
    }

    @Override
    public void setOverlapAnchor(PopupWindow popupWindow, boolean bl) {
        if (sOverlapAnchorField != null) {
            try {
                sOverlapAnchorField.set((Object)popupWindow, bl);
                return;
            }
            catch (IllegalAccessException illegalAccessException) {
                Log.i((String)TAG, (String)"Could not set overlap anchor field in PopupWindow", (Throwable)illegalAccessException);
            }
        }
    }
}

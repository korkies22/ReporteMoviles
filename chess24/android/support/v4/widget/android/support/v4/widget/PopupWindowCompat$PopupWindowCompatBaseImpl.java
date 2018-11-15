/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.widget.PopupWindow
 */
package android.support.v4.widget;

import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.PopupWindowCompat;
import android.view.View;
import android.widget.PopupWindow;
import java.lang.reflect.Method;

static class PopupWindowCompat.PopupWindowCompatBaseImpl {
    private static Method sGetWindowLayoutTypeMethod;
    private static boolean sGetWindowLayoutTypeMethodAttempted;
    private static Method sSetWindowLayoutTypeMethod;
    private static boolean sSetWindowLayoutTypeMethodAttempted;

    PopupWindowCompat.PopupWindowCompatBaseImpl() {
    }

    public boolean getOverlapAnchor(PopupWindow popupWindow) {
        return false;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public int getWindowLayoutType(PopupWindow popupWindow) {
        if (!sGetWindowLayoutTypeMethodAttempted) {
            try {
                sGetWindowLayoutTypeMethod = PopupWindow.class.getDeclaredMethod("getWindowLayoutType", new Class[0]);
                sGetWindowLayoutTypeMethod.setAccessible(true);
            }
            catch (Exception exception) {}
            sGetWindowLayoutTypeMethodAttempted = true;
        }
        if (sGetWindowLayoutTypeMethod == null) {
            return 0;
        }
        try {
            return (Integer)sGetWindowLayoutTypeMethod.invoke((Object)popupWindow, new Object[0]);
        }
        catch (Exception exception) {
            return 0;
        }
    }

    public void setOverlapAnchor(PopupWindow popupWindow, boolean bl) {
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public void setWindowLayoutType(PopupWindow popupWindow, int n) {
        if (!sSetWindowLayoutTypeMethodAttempted) {
            try {
                sSetWindowLayoutTypeMethod = PopupWindow.class.getDeclaredMethod("setWindowLayoutType", Integer.TYPE);
                sSetWindowLayoutTypeMethod.setAccessible(true);
            }
            catch (Exception exception) {}
            sSetWindowLayoutTypeMethodAttempted = true;
        }
        if (sSetWindowLayoutTypeMethod == null) return;
        try {
            sSetWindowLayoutTypeMethod.invoke((Object)popupWindow, n);
            return;
        }
        catch (Exception exception) {
            return;
        }
    }

    public void showAsDropDown(PopupWindow popupWindow, View view, int n, int n2, int n3) {
        int n4 = n;
        if ((GravityCompat.getAbsoluteGravity(n3, ViewCompat.getLayoutDirection(view)) & 7) == 5) {
            n4 = n - (popupWindow.getWidth() - view.getWidth());
        }
        popupWindow.showAsDropDown(view, n4, n2);
    }
}

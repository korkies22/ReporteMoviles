/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.util.Log
 *  android.view.View
 *  android.widget.PopupWindow
 */
package android.support.v4.widget;

import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewCompat;
import android.util.Log;
import android.view.View;
import android.widget.PopupWindow;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public final class PopupWindowCompat {
    static final PopupWindowCompatBaseImpl IMPL = Build.VERSION.SDK_INT >= 23 ? new PopupWindowCompatApi23Impl() : (Build.VERSION.SDK_INT >= 21 ? new PopupWindowCompatApi21Impl() : (Build.VERSION.SDK_INT >= 19 ? new PopupWindowCompatApi19Impl() : new PopupWindowCompatBaseImpl()));

    private PopupWindowCompat() {
    }

    public static boolean getOverlapAnchor(@NonNull PopupWindow popupWindow) {
        return IMPL.getOverlapAnchor(popupWindow);
    }

    public static int getWindowLayoutType(@NonNull PopupWindow popupWindow) {
        return IMPL.getWindowLayoutType(popupWindow);
    }

    public static void setOverlapAnchor(@NonNull PopupWindow popupWindow, boolean bl) {
        IMPL.setOverlapAnchor(popupWindow, bl);
    }

    public static void setWindowLayoutType(@NonNull PopupWindow popupWindow, int n) {
        IMPL.setWindowLayoutType(popupWindow, n);
    }

    public static void showAsDropDown(@NonNull PopupWindow popupWindow, @NonNull View view, int n, int n2, int n3) {
        IMPL.showAsDropDown(popupWindow, view, n, n2, n3);
    }

    @RequiresApi(value=19)
    static class PopupWindowCompatApi19Impl
    extends PopupWindowCompatBaseImpl {
        PopupWindowCompatApi19Impl() {
        }

        @Override
        public void showAsDropDown(PopupWindow popupWindow, View view, int n, int n2, int n3) {
            popupWindow.showAsDropDown(view, n, n2, n3);
        }
    }

    @RequiresApi(value=21)
    static class PopupWindowCompatApi21Impl
    extends PopupWindowCompatApi19Impl {
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

        PopupWindowCompatApi21Impl() {
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

    @RequiresApi(value=23)
    static class PopupWindowCompatApi23Impl
    extends PopupWindowCompatApi21Impl {
        PopupWindowCompatApi23Impl() {
        }

        @Override
        public boolean getOverlapAnchor(PopupWindow popupWindow) {
            return popupWindow.getOverlapAnchor();
        }

        @Override
        public int getWindowLayoutType(PopupWindow popupWindow) {
            return popupWindow.getWindowLayoutType();
        }

        @Override
        public void setOverlapAnchor(PopupWindow popupWindow, boolean bl) {
            popupWindow.setOverlapAnchor(bl);
        }

        @Override
        public void setWindowLayoutType(PopupWindow popupWindow, int n) {
            popupWindow.setWindowLayoutType(n);
        }
    }

    static class PopupWindowCompatBaseImpl {
        private static Method sGetWindowLayoutTypeMethod;
        private static boolean sGetWindowLayoutTypeMethodAttempted;
        private static Method sSetWindowLayoutTypeMethod;
        private static boolean sSetWindowLayoutTypeMethodAttempted;

        PopupWindowCompatBaseImpl() {
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

}

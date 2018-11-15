/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.hardware.display.DisplayManager
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.view.Display
 *  android.view.WindowManager
 */
package android.support.v4.hardware.display;

import android.content.Context;
import android.hardware.display.DisplayManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.Display;
import android.view.WindowManager;
import java.util.WeakHashMap;

public abstract class DisplayManagerCompat {
    public static final String DISPLAY_CATEGORY_PRESENTATION = "android.hardware.display.category.PRESENTATION";
    private static final WeakHashMap<Context, DisplayManagerCompat> sInstances = new WeakHashMap();

    DisplayManagerCompat() {
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @NonNull
    public static DisplayManagerCompat getInstance(@NonNull Context context) {
        WeakHashMap<Context, DisplayManagerCompat> weakHashMap = sInstances;
        synchronized (weakHashMap) {
            DisplayManagerCompat displayManagerCompat;
            DisplayManagerCompat displayManagerCompat2 = displayManagerCompat = sInstances.get((Object)context);
            if (displayManagerCompat == null) {
                displayManagerCompat2 = Build.VERSION.SDK_INT >= 17 ? new DisplayManagerCompatApi17Impl(context) : new DisplayManagerCompatApi14Impl(context);
                sInstances.put(context, displayManagerCompat2);
            }
            return displayManagerCompat2;
        }
    }

    @Nullable
    public abstract Display getDisplay(int var1);

    @NonNull
    public abstract Display[] getDisplays();

    @NonNull
    public abstract Display[] getDisplays(String var1);

    private static class DisplayManagerCompatApi14Impl
    extends DisplayManagerCompat {
        private final WindowManager mWindowManager;

        DisplayManagerCompatApi14Impl(Context context) {
            this.mWindowManager = (WindowManager)context.getSystemService("window");
        }

        @Override
        public Display getDisplay(int n) {
            Display display = this.mWindowManager.getDefaultDisplay();
            if (display.getDisplayId() == n) {
                return display;
            }
            return null;
        }

        @Override
        public Display[] getDisplays() {
            return new Display[]{this.mWindowManager.getDefaultDisplay()};
        }

        @Override
        public Display[] getDisplays(String string) {
            if (string == null) {
                return this.getDisplays();
            }
            return new Display[0];
        }
    }

    @RequiresApi(value=17)
    private static class DisplayManagerCompatApi17Impl
    extends DisplayManagerCompat {
        private final DisplayManager mDisplayManager;

        DisplayManagerCompatApi17Impl(Context context) {
            this.mDisplayManager = (DisplayManager)context.getSystemService("display");
        }

        @Override
        public Display getDisplay(int n) {
            return this.mDisplayManager.getDisplay(n);
        }

        @Override
        public Display[] getDisplays() {
            return this.mDisplayManager.getDisplays();
        }

        @Override
        public Display[] getDisplays(String string) {
            return this.mDisplayManager.getDisplays(string);
        }
    }

}

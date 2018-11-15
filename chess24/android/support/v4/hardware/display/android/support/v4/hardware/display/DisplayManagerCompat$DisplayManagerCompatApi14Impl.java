/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.view.Display
 *  android.view.WindowManager
 */
package android.support.v4.hardware.display;

import android.content.Context;
import android.support.v4.hardware.display.DisplayManagerCompat;
import android.view.Display;
import android.view.WindowManager;

private static class DisplayManagerCompat.DisplayManagerCompatApi14Impl
extends DisplayManagerCompat {
    private final WindowManager mWindowManager;

    DisplayManagerCompat.DisplayManagerCompatApi14Impl(Context context) {
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

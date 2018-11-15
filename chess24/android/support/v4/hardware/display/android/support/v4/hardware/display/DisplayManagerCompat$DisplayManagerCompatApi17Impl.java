/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.hardware.display.DisplayManager
 *  android.view.Display
 */
package android.support.v4.hardware.display;

import android.content.Context;
import android.hardware.display.DisplayManager;
import android.support.annotation.RequiresApi;
import android.support.v4.hardware.display.DisplayManagerCompat;
import android.view.Display;

@RequiresApi(value=17)
private static class DisplayManagerCompat.DisplayManagerCompatApi17Impl
extends DisplayManagerCompat {
    private final DisplayManager mDisplayManager;

    DisplayManagerCompat.DisplayManagerCompatApi17Impl(Context context) {
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

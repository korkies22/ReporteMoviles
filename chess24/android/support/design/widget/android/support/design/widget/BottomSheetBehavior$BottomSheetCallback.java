/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 */
package android.support.design.widget;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.view.View;

public static abstract class BottomSheetBehavior.BottomSheetCallback {
    public abstract void onSlide(@NonNull View var1, float var2);

    public abstract void onStateChanged(@NonNull View var1, int var2);
}

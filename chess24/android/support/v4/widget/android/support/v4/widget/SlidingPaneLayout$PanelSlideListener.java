/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 */
package android.support.v4.widget;

import android.support.annotation.NonNull;
import android.support.v4.widget.SlidingPaneLayout;
import android.view.View;

public static interface SlidingPaneLayout.PanelSlideListener {
    public void onPanelClosed(@NonNull View var1);

    public void onPanelOpened(@NonNull View var1);

    public void onPanelSlide(@NonNull View var1, float var2);
}

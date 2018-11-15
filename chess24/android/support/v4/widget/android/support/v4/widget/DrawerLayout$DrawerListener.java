/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 */
package android.support.v4.widget;

import android.support.annotation.NonNull;
import android.support.v4.widget.DrawerLayout;
import android.view.View;

public static interface DrawerLayout.DrawerListener {
    public void onDrawerClosed(@NonNull View var1);

    public void onDrawerOpened(@NonNull View var1);

    public void onDrawerSlide(@NonNull View var1, float var2);

    public void onDrawerStateChanged(int var1);
}

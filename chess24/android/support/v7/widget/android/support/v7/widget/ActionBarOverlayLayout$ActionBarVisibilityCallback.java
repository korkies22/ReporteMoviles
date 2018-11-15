/*
 * Decompiled with CFR 0_134.
 */
package android.support.v7.widget;

import android.support.v7.widget.ActionBarOverlayLayout;

public static interface ActionBarOverlayLayout.ActionBarVisibilityCallback {
    public void enableContentAnimations(boolean var1);

    public void hideForSystem();

    public void onContentScrollStarted();

    public void onContentScrollStopped();

    public void onWindowVisibilityChanged(int var1);

    public void showForSystem();
}

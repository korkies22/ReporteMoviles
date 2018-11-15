/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.graphics.Matrix
 *  android.view.View
 *  android.view.ViewGroup
 */
package android.support.transition;

import android.graphics.Matrix;
import android.support.transition.GhostViewImpl;
import android.view.View;
import android.view.ViewGroup;

public static interface GhostViewImpl.Creator {
    public GhostViewImpl addGhost(View var1, ViewGroup var2, Matrix var3);

    public void removeGhost(View var1);
}

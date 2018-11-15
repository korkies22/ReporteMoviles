/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 */
package android.support.v7.widget;

import android.support.v7.widget.ViewBoundsCheck;
import android.view.View;

static interface ViewBoundsCheck.Callback {
    public View getChildAt(int var1);

    public int getChildCount();

    public int getChildEnd(View var1);

    public int getChildStart(View var1);

    public View getParent();

    public int getParentEnd();

    public int getParentStart();
}

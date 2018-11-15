/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.ViewGroup
 */
package android.support.v4.view;

import android.support.annotation.RequiresApi;
import android.support.v4.view.ViewGroupCompat;
import android.view.ViewGroup;

@RequiresApi(value=21)
static class ViewGroupCompat.ViewGroupCompatApi21Impl
extends ViewGroupCompat.ViewGroupCompatApi18Impl {
    ViewGroupCompat.ViewGroupCompatApi21Impl() {
    }

    @Override
    public int getNestedScrollAxes(ViewGroup viewGroup) {
        return viewGroup.getNestedScrollAxes();
    }

    @Override
    public boolean isTransitionGroup(ViewGroup viewGroup) {
        return viewGroup.isTransitionGroup();
    }

    @Override
    public void setTransitionGroup(ViewGroup viewGroup, boolean bl) {
        viewGroup.setTransitionGroup(bl);
    }
}

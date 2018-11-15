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

@RequiresApi(value=18)
static class ViewGroupCompat.ViewGroupCompatApi18Impl
extends ViewGroupCompat.ViewGroupCompatBaseImpl {
    ViewGroupCompat.ViewGroupCompatApi18Impl() {
    }

    @Override
    public int getLayoutMode(ViewGroup viewGroup) {
        return viewGroup.getLayoutMode();
    }

    @Override
    public void setLayoutMode(ViewGroup viewGroup, int n) {
        viewGroup.setLayoutMode(n);
    }
}

/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 */
package android.support.v4.view;

import android.support.annotation.RequiresApi;
import android.support.v4.view.ViewCompat;
import android.view.View;

@RequiresApi(value=15)
static class ViewCompat.ViewCompatApi15Impl
extends ViewCompat.ViewCompatBaseImpl {
    ViewCompat.ViewCompatApi15Impl() {
    }

    @Override
    public boolean hasOnClickListeners(View view) {
        return view.hasOnClickListeners();
    }
}

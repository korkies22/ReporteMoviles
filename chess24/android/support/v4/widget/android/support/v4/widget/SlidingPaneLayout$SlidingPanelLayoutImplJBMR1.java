/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.graphics.Paint
 *  android.view.View
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 */
package android.support.v4.widget;

import android.graphics.Paint;
import android.support.annotation.RequiresApi;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.SlidingPaneLayout;
import android.view.View;
import android.view.ViewGroup;

@RequiresApi(value=17)
static class SlidingPaneLayout.SlidingPanelLayoutImplJBMR1
extends SlidingPaneLayout.SlidingPanelLayoutImplBase {
    SlidingPaneLayout.SlidingPanelLayoutImplJBMR1() {
    }

    @Override
    public void invalidateChildRegion(SlidingPaneLayout slidingPaneLayout, View view) {
        ViewCompat.setLayerPaint(view, ((SlidingPaneLayout.LayoutParams)view.getLayoutParams()).dimPaint);
    }
}

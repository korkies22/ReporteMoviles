/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 */
package android.support.v4.widget;

import android.support.v4.view.ViewCompat;
import android.support.v4.widget.SlidingPaneLayout;
import android.view.View;

static class SlidingPaneLayout.SlidingPanelLayoutImplBase
implements SlidingPaneLayout.SlidingPanelLayoutImpl {
    SlidingPaneLayout.SlidingPanelLayoutImplBase() {
    }

    @Override
    public void invalidateChildRegion(SlidingPaneLayout slidingPaneLayout, View view) {
        ViewCompat.postInvalidateOnAnimation((View)slidingPaneLayout, view.getLeft(), view.getTop(), view.getRight(), view.getBottom());
    }
}

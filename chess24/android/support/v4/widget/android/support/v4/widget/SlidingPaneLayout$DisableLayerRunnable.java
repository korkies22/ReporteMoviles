/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.graphics.Paint
 *  android.view.View
 *  android.view.ViewParent
 */
package android.support.v4.widget;

import android.graphics.Paint;
import android.support.v4.widget.SlidingPaneLayout;
import android.view.View;
import android.view.ViewParent;
import java.util.ArrayList;

private class SlidingPaneLayout.DisableLayerRunnable
implements Runnable {
    final View mChildView;

    SlidingPaneLayout.DisableLayerRunnable(View view) {
        this.mChildView = view;
    }

    @Override
    public void run() {
        if (this.mChildView.getParent() == SlidingPaneLayout.this) {
            this.mChildView.setLayerType(0, null);
            SlidingPaneLayout.this.invalidateChildRegion(this.mChildView);
        }
        SlidingPaneLayout.this.mPostedRunnables.remove(this);
    }
}

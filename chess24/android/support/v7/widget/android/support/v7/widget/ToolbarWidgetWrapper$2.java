/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 */
package android.support.v7.widget;

import android.support.v4.view.ViewPropertyAnimatorListenerAdapter;
import android.support.v7.widget.Toolbar;
import android.view.View;

class ToolbarWidgetWrapper
extends ViewPropertyAnimatorListenerAdapter {
    private boolean mCanceled = false;
    final /* synthetic */ int val$visibility;

    ToolbarWidgetWrapper(int n) {
        this.val$visibility = n;
    }

    @Override
    public void onAnimationCancel(View view) {
        this.mCanceled = true;
    }

    @Override
    public void onAnimationEnd(View view) {
        if (!this.mCanceled) {
            ToolbarWidgetWrapper.this.mToolbar.setVisibility(this.val$visibility);
        }
    }

    @Override
    public void onAnimationStart(View view) {
        ToolbarWidgetWrapper.this.mToolbar.setVisibility(0);
    }
}

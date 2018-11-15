/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 */
package android.support.design.widget;

import android.support.design.widget.SnackbarManager;
import android.support.design.widget.SwipeDismissBehavior;
import android.view.View;

class BaseTransientBottomBar
implements SwipeDismissBehavior.OnDismissListener {
    BaseTransientBottomBar() {
    }

    @Override
    public void onDismiss(View view) {
        view.setVisibility(8);
        BaseTransientBottomBar.this.dispatchDismiss(0);
    }

    @Override
    public void onDragStateChanged(int n) {
        switch (n) {
            default: {
                return;
            }
            case 1: 
            case 2: {
                SnackbarManager.getInstance().pauseTimeout(BaseTransientBottomBar.this.mManagerCallback);
                return;
            }
            case 0: 
        }
        SnackbarManager.getInstance().restoreTimeoutIfPaused(BaseTransientBottomBar.this.mManagerCallback);
    }
}

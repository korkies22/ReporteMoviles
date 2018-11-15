/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.graphics.Rect
 */
package android.support.v7.app;

import android.graphics.Rect;
import android.support.v7.widget.FitWindowsViewGroup;

class AppCompatDelegateImplV9
implements FitWindowsViewGroup.OnFitSystemWindowsListener {
    AppCompatDelegateImplV9() {
    }

    @Override
    public void onFitSystemWindows(Rect rect) {
        rect.top = AppCompatDelegateImplV9.this.updateStatusGuard(rect.top);
    }
}

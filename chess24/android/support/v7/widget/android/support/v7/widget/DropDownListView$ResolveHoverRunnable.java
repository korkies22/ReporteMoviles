/*
 * Decompiled with CFR 0_134.
 */
package android.support.v7.widget;

import android.support.v7.widget.DropDownListView;

private class DropDownListView.ResolveHoverRunnable
implements Runnable {
    private DropDownListView.ResolveHoverRunnable() {
    }

    public void cancel() {
        DropDownListView.this.mResolveHoverRunnable = null;
        DropDownListView.this.removeCallbacks((Runnable)this);
    }

    public void post() {
        DropDownListView.this.post((Runnable)this);
    }

    @Override
    public void run() {
        DropDownListView.this.mResolveHoverRunnable = null;
        DropDownListView.this.drawableStateChanged();
    }
}

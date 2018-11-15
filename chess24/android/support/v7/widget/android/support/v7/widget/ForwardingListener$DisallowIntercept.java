/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.ViewParent
 */
package android.support.v7.widget;

import android.support.v7.widget.ForwardingListener;
import android.view.View;
import android.view.ViewParent;

private class ForwardingListener.DisallowIntercept
implements Runnable {
    ForwardingListener.DisallowIntercept() {
    }

    @Override
    public void run() {
        ViewParent viewParent = ForwardingListener.this.mSrc.getParent();
        if (viewParent != null) {
            viewParent.requestDisallowInterceptTouchEvent(true);
        }
    }
}

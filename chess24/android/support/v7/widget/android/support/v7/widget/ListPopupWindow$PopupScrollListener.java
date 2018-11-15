/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Handler
 *  android.view.View
 *  android.widget.AbsListView
 *  android.widget.AbsListView$OnScrollListener
 *  android.widget.PopupWindow
 */
package android.support.v7.widget;

import android.os.Handler;
import android.support.v7.widget.ListPopupWindow;
import android.view.View;
import android.widget.AbsListView;
import android.widget.PopupWindow;

private class ListPopupWindow.PopupScrollListener
implements AbsListView.OnScrollListener {
    ListPopupWindow.PopupScrollListener() {
    }

    public void onScroll(AbsListView absListView, int n, int n2, int n3) {
    }

    public void onScrollStateChanged(AbsListView absListView, int n) {
        if (n == 1 && !ListPopupWindow.this.isInputMethodNotNeeded() && ListPopupWindow.this.mPopup.getContentView() != null) {
            ListPopupWindow.this.mHandler.removeCallbacks((Runnable)ListPopupWindow.this.mResizePopupRunnable);
            ListPopupWindow.this.mResizePopupRunnable.run();
        }
    }
}

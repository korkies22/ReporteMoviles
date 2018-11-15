/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Handler
 *  android.view.MotionEvent
 *  android.view.View
 *  android.view.View$OnTouchListener
 *  android.widget.PopupWindow
 */
package android.support.v7.widget;

import android.os.Handler;
import android.support.v7.widget.ListPopupWindow;
import android.view.MotionEvent;
import android.view.View;
import android.widget.PopupWindow;

private class ListPopupWindow.PopupTouchInterceptor
implements View.OnTouchListener {
    ListPopupWindow.PopupTouchInterceptor() {
    }

    public boolean onTouch(View view, MotionEvent motionEvent) {
        int n = motionEvent.getAction();
        int n2 = (int)motionEvent.getX();
        int n3 = (int)motionEvent.getY();
        if (n == 0 && ListPopupWindow.this.mPopup != null && ListPopupWindow.this.mPopup.isShowing() && n2 >= 0 && n2 < ListPopupWindow.this.mPopup.getWidth() && n3 >= 0 && n3 < ListPopupWindow.this.mPopup.getHeight()) {
            ListPopupWindow.this.mHandler.postDelayed((Runnable)ListPopupWindow.this.mResizePopupRunnable, 250L);
        } else if (n == 1) {
            ListPopupWindow.this.mHandler.removeCallbacks((Runnable)ListPopupWindow.this.mResizePopupRunnable);
        }
        return false;
    }
}

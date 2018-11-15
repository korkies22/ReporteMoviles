/*
 * Decompiled with CFR 0_134.
 */
package android.support.v7.widget;

import android.support.v7.widget.ListPopupWindow;

private class ListPopupWindow.ListSelectorHider
implements Runnable {
    ListPopupWindow.ListSelectorHider() {
    }

    @Override
    public void run() {
        ListPopupWindow.this.clearListSelection();
    }
}

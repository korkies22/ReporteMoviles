/*
 * Decompiled with CFR 0_134.
 */
package android.support.v4.widget;

import android.support.v4.widget.DrawerLayout;

class DrawerLayout.ViewDragCallback
implements Runnable {
    DrawerLayout.ViewDragCallback() {
    }

    @Override
    public void run() {
        ViewDragCallback.this.peekDrawer();
    }
}

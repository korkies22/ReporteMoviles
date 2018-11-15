/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.IBinder
 *  android.view.View
 */
package android.support.v7.widget;

import android.os.IBinder;
import android.view.View;

class ListPopupWindow
implements Runnable {
    ListPopupWindow() {
    }

    @Override
    public void run() {
        View view = ListPopupWindow.this.getAnchorView();
        if (view != null && view.getWindowToken() != null) {
            ListPopupWindow.this.show();
        }
    }
}

/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Handler
 *  android.os.Message
 */
package android.support.design.widget;

import android.os.Handler;
import android.os.Message;
import android.support.design.widget.SnackbarManager;

class BaseTransientBottomBar
implements SnackbarManager.Callback {
    BaseTransientBottomBar() {
    }

    @Override
    public void dismiss(int n) {
        android.support.design.widget.BaseTransientBottomBar.sHandler.sendMessage(android.support.design.widget.BaseTransientBottomBar.sHandler.obtainMessage(1, n, 0, (Object)BaseTransientBottomBar.this));
    }

    @Override
    public void show() {
        android.support.design.widget.BaseTransientBottomBar.sHandler.sendMessage(android.support.design.widget.BaseTransientBottomBar.sHandler.obtainMessage(0, (Object)BaseTransientBottomBar.this));
    }
}

/*
 * Decompiled with CFR 0_134.
 */
package android.support.design.widget;

import android.support.design.widget.SnackbarManager;
import java.lang.ref.WeakReference;

private static class SnackbarManager.SnackbarRecord {
    final WeakReference<SnackbarManager.Callback> callback;
    int duration;
    boolean paused;

    SnackbarManager.SnackbarRecord(int n, SnackbarManager.Callback callback) {
        this.callback = new WeakReference<SnackbarManager.Callback>(callback);
        this.duration = n;
    }

    boolean isSnackbar(SnackbarManager.Callback callback) {
        if (callback != null && this.callback.get() == callback) {
            return true;
        }
        return false;
    }
}

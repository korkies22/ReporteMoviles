/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.ActionMode
 *  android.view.ActionMode$Callback
 *  android.view.Window
 *  android.view.Window$Callback
 */
package android.support.v7.app;

import android.support.v7.app.AppCompatDelegateImplV14;
import android.support.v7.app.AppCompatDelegateImplV23;
import android.view.ActionMode;
import android.view.Window;

class AppCompatDelegateImplV23.AppCompatWindowCallbackV23
extends AppCompatDelegateImplV14.AppCompatWindowCallbackV14 {
    AppCompatDelegateImplV23.AppCompatWindowCallbackV23(Window.Callback callback) {
        super(AppCompatDelegateImplV23.this, callback);
    }

    @Override
    public ActionMode onWindowStartingActionMode(ActionMode.Callback callback) {
        return null;
    }

    @Override
    public ActionMode onWindowStartingActionMode(ActionMode.Callback callback, int n) {
        if (AppCompatDelegateImplV23.this.isHandleNativeActionModesEnabled() && n == 0) {
            return this.startAsSupportActionMode(callback);
        }
        return super.onWindowStartingActionMode(callback, n);
    }
}

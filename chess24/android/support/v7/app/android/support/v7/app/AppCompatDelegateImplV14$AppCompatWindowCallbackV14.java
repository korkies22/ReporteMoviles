/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.view.ActionMode
 *  android.view.ActionMode$Callback
 *  android.view.Window
 *  android.view.Window$Callback
 */
package android.support.v7.app;

import android.content.Context;
import android.support.v7.app.AppCompatDelegateImplBase;
import android.support.v7.app.AppCompatDelegateImplV14;
import android.support.v7.view.ActionMode;
import android.support.v7.view.SupportActionModeWrapper;
import android.view.ActionMode;
import android.view.Window;

class AppCompatDelegateImplV14.AppCompatWindowCallbackV14
extends AppCompatDelegateImplBase.AppCompatWindowCallbackBase {
    AppCompatDelegateImplV14.AppCompatWindowCallbackV14(Window.Callback callback) {
        super(AppCompatDelegateImplV14.this, callback);
    }

    @Override
    public ActionMode onWindowStartingActionMode(ActionMode.Callback callback) {
        if (AppCompatDelegateImplV14.this.isHandleNativeActionModesEnabled()) {
            return this.startAsSupportActionMode(callback);
        }
        return super.onWindowStartingActionMode(callback);
    }

    final ActionMode startAsSupportActionMode(ActionMode.Callback object) {
        android.support.v7.view.ActionMode actionMode = AppCompatDelegateImplV14.this.startSupportActionMode((ActionMode.Callback)(object = new SupportActionModeWrapper.CallbackWrapper(AppCompatDelegateImplV14.this.mContext, (ActionMode.Callback)object)));
        if (actionMode != null) {
            return object.getActionModeWrapper(actionMode);
        }
        return null;
    }
}

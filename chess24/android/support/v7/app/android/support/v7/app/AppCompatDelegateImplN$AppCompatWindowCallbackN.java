/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.KeyboardShortcutGroup
 *  android.view.Menu
 *  android.view.Window
 *  android.view.Window$Callback
 */
package android.support.v7.app;

import android.support.v7.app.AppCompatDelegateImplN;
import android.support.v7.app.AppCompatDelegateImplV23;
import android.support.v7.app.AppCompatDelegateImplV9;
import android.support.v7.view.menu.MenuBuilder;
import android.view.KeyboardShortcutGroup;
import android.view.Menu;
import android.view.Window;
import java.util.List;

class AppCompatDelegateImplN.AppCompatWindowCallbackN
extends AppCompatDelegateImplV23.AppCompatWindowCallbackV23 {
    AppCompatDelegateImplN.AppCompatWindowCallbackN(Window.Callback callback) {
        super(AppCompatDelegateImplN.this, callback);
    }

    @Override
    public void onProvideKeyboardShortcuts(List<KeyboardShortcutGroup> list, Menu menu, int n) {
        AppCompatDelegateImplV9.PanelFeatureState panelFeatureState = AppCompatDelegateImplN.this.getPanelState(0, true);
        if (panelFeatureState != null && panelFeatureState.menu != null) {
            super.onProvideKeyboardShortcuts(list, panelFeatureState.menu, n);
            return;
        }
        super.onProvideKeyboardShortcuts(list, menu, n);
    }
}

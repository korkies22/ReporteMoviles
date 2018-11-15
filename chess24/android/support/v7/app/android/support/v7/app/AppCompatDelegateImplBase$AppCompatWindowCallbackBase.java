/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.KeyEvent
 *  android.view.Menu
 *  android.view.View
 *  android.view.Window
 *  android.view.Window$Callback
 */
package android.support.v7.app;

import android.support.v7.app.AppCompatDelegateImplBase;
import android.support.v7.view.WindowCallbackWrapper;
import android.support.v7.view.menu.MenuBuilder;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.Window;

class AppCompatDelegateImplBase.AppCompatWindowCallbackBase
extends WindowCallbackWrapper {
    AppCompatDelegateImplBase.AppCompatWindowCallbackBase(Window.Callback callback) {
        super(callback);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        if (!AppCompatDelegateImplBase.this.dispatchKeyEvent(keyEvent) && !super.dispatchKeyEvent(keyEvent)) {
            return false;
        }
        return true;
    }

    @Override
    public boolean dispatchKeyShortcutEvent(KeyEvent keyEvent) {
        if (!super.dispatchKeyShortcutEvent(keyEvent) && !AppCompatDelegateImplBase.this.onKeyShortcut(keyEvent.getKeyCode(), keyEvent)) {
            return false;
        }
        return true;
    }

    @Override
    public void onContentChanged() {
    }

    @Override
    public boolean onCreatePanelMenu(int n, Menu menu) {
        if (n == 0 && !(menu instanceof MenuBuilder)) {
            return false;
        }
        return super.onCreatePanelMenu(n, menu);
    }

    @Override
    public boolean onMenuOpened(int n, Menu menu) {
        super.onMenuOpened(n, menu);
        AppCompatDelegateImplBase.this.onMenuOpened(n, menu);
        return true;
    }

    @Override
    public void onPanelClosed(int n, Menu menu) {
        super.onPanelClosed(n, menu);
        AppCompatDelegateImplBase.this.onPanelClosed(n, menu);
    }

    @Override
    public boolean onPreparePanel(int n, View view, Menu menu) {
        MenuBuilder menuBuilder = menu instanceof MenuBuilder ? (MenuBuilder)menu : null;
        if (n == 0 && menuBuilder == null) {
            return false;
        }
        if (menuBuilder != null) {
            menuBuilder.setOverrideVisibleItems(true);
        }
        boolean bl = super.onPreparePanel(n, view, menu);
        if (menuBuilder != null) {
            menuBuilder.setOverrideVisibleItems(false);
        }
        return bl;
    }
}

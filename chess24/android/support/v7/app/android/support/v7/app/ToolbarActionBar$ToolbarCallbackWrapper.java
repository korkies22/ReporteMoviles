/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.view.Menu
 *  android.view.View
 *  android.view.Window
 *  android.view.Window$Callback
 */
package android.support.v7.app;

import android.content.Context;
import android.support.v7.app.ToolbarActionBar;
import android.support.v7.view.WindowCallbackWrapper;
import android.support.v7.widget.DecorToolbar;
import android.view.Menu;
import android.view.View;
import android.view.Window;

private class ToolbarActionBar.ToolbarCallbackWrapper
extends WindowCallbackWrapper {
    public ToolbarActionBar.ToolbarCallbackWrapper(Window.Callback callback) {
        super(callback);
    }

    @Override
    public View onCreatePanelView(int n) {
        if (n == 0) {
            return new View(ToolbarActionBar.this.mDecorToolbar.getContext());
        }
        return super.onCreatePanelView(n);
    }

    @Override
    public boolean onPreparePanel(int n, View view, Menu menu) {
        boolean bl = super.onPreparePanel(n, view, menu);
        if (bl && !ToolbarActionBar.this.mToolbarMenuPrepared) {
            ToolbarActionBar.this.mDecorToolbar.setMenuPrepared();
            ToolbarActionBar.this.mToolbarMenuPrepared = true;
        }
        return bl;
    }
}

/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.Menu
 *  android.view.Window
 *  android.view.Window$Callback
 */
package android.support.v7.app;

import android.support.v7.app.AppCompatDelegateImplV9;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.view.menu.MenuPresenter;
import android.view.Menu;
import android.view.Window;

private final class AppCompatDelegateImplV9.ActionMenuPresenterCallback
implements MenuPresenter.Callback {
    AppCompatDelegateImplV9.ActionMenuPresenterCallback() {
    }

    @Override
    public void onCloseMenu(MenuBuilder menuBuilder, boolean bl) {
        AppCompatDelegateImplV9.this.checkCloseActionMenu(menuBuilder);
    }

    @Override
    public boolean onOpenSubMenu(MenuBuilder menuBuilder) {
        Window.Callback callback = AppCompatDelegateImplV9.this.getWindowCallback();
        if (callback != null) {
            callback.onMenuOpened(108, (Menu)menuBuilder);
        }
        return true;
    }
}

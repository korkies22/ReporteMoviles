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

private final class AppCompatDelegateImplV9.PanelMenuPresenterCallback
implements MenuPresenter.Callback {
    AppCompatDelegateImplV9.PanelMenuPresenterCallback() {
    }

    @Override
    public void onCloseMenu(MenuBuilder object, boolean bl) {
        MenuBuilder menuBuilder = object.getRootMenu();
        boolean bl2 = menuBuilder != object;
        AppCompatDelegateImplV9 appCompatDelegateImplV9 = AppCompatDelegateImplV9.this;
        if (bl2) {
            object = menuBuilder;
        }
        if ((object = appCompatDelegateImplV9.findMenuPanel((Menu)object)) != null) {
            if (bl2) {
                AppCompatDelegateImplV9.this.callOnPanelClosed(object.featureId, (AppCompatDelegateImplV9.PanelFeatureState)object, menuBuilder);
                AppCompatDelegateImplV9.this.closePanel((AppCompatDelegateImplV9.PanelFeatureState)object, true);
                return;
            }
            AppCompatDelegateImplV9.this.closePanel((AppCompatDelegateImplV9.PanelFeatureState)object, bl);
        }
    }

    @Override
    public boolean onOpenSubMenu(MenuBuilder menuBuilder) {
        Window.Callback callback;
        if (menuBuilder == null && AppCompatDelegateImplV9.this.mHasActionBar && (callback = AppCompatDelegateImplV9.this.getWindowCallback()) != null && !AppCompatDelegateImplV9.this.isDestroyed()) {
            callback.onMenuOpened(108, (Menu)menuBuilder);
        }
        return true;
    }
}

/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.MenuItem
 */
package android.support.v7.widget;

import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.view.menu.MenuPresenter;
import android.support.v7.view.menu.SubMenuBuilder;
import android.support.v7.widget.ActionMenuPresenter;
import android.view.MenuItem;

private class ActionMenuPresenter.PopupPresenterCallback
implements MenuPresenter.Callback {
    ActionMenuPresenter.PopupPresenterCallback() {
    }

    @Override
    public void onCloseMenu(MenuBuilder menuBuilder, boolean bl) {
        MenuPresenter.Callback callback;
        if (menuBuilder instanceof SubMenuBuilder) {
            menuBuilder.getRootMenu().close(false);
        }
        if ((callback = ActionMenuPresenter.this.getCallback()) != null) {
            callback.onCloseMenu(menuBuilder, bl);
        }
    }

    @Override
    public boolean onOpenSubMenu(MenuBuilder menuBuilder) {
        boolean bl = false;
        if (menuBuilder == null) {
            return false;
        }
        ActionMenuPresenter.this.mOpenSubMenuId = ((SubMenuBuilder)menuBuilder).getItem().getItemId();
        MenuPresenter.Callback callback = ActionMenuPresenter.this.getCallback();
        if (callback != null) {
            bl = callback.onOpenSubMenu(menuBuilder);
        }
        return bl;
    }
}

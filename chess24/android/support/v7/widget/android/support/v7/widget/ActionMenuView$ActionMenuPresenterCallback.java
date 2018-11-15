/*
 * Decompiled with CFR 0_134.
 */
package android.support.v7.widget;

import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.view.menu.MenuPresenter;
import android.support.v7.widget.ActionMenuView;

private static class ActionMenuView.ActionMenuPresenterCallback
implements MenuPresenter.Callback {
    ActionMenuView.ActionMenuPresenterCallback() {
    }

    @Override
    public void onCloseMenu(MenuBuilder menuBuilder, boolean bl) {
    }

    @Override
    public boolean onOpenSubMenu(MenuBuilder menuBuilder) {
        return false;
    }
}

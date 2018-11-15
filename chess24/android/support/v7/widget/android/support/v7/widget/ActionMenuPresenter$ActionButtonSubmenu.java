/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.view.MenuItem
 *  android.view.View
 */
package android.support.v7.widget;

import android.content.Context;
import android.support.v7.appcompat.R;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.view.menu.MenuItemImpl;
import android.support.v7.view.menu.MenuPopupHelper;
import android.support.v7.view.menu.MenuPresenter;
import android.support.v7.view.menu.MenuView;
import android.support.v7.view.menu.SubMenuBuilder;
import android.support.v7.widget.ActionMenuPresenter;
import android.view.MenuItem;
import android.view.View;

private class ActionMenuPresenter.ActionButtonSubmenu
extends MenuPopupHelper {
    public ActionMenuPresenter.ActionButtonSubmenu(Context object, SubMenuBuilder subMenuBuilder, View view) {
        super((Context)object, subMenuBuilder, view, false, R.attr.actionOverflowMenuStyle);
        if (!((MenuItemImpl)subMenuBuilder.getItem()).isActionButton()) {
            object = ActionMenuPresenter.this.mOverflowButton == null ? (View)ActionMenuPresenter.this.mMenuView : ActionMenuPresenter.this.mOverflowButton;
            this.setAnchorView((View)object);
        }
        this.setPresenterCallback(ActionMenuPresenter.this.mPopupPresenterCallback);
    }

    @Override
    protected void onDismiss() {
        ActionMenuPresenter.this.mActionButtonPopup = null;
        ActionMenuPresenter.this.mOpenSubMenuId = 0;
        super.onDismiss();
    }
}

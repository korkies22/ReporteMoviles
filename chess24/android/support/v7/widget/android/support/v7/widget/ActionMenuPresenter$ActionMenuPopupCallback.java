/*
 * Decompiled with CFR 0_134.
 */
package android.support.v7.widget;

import android.support.v7.view.menu.ActionMenuItemView;
import android.support.v7.view.menu.MenuPopup;
import android.support.v7.view.menu.ShowableListMenu;
import android.support.v7.widget.ActionMenuPresenter;

private class ActionMenuPresenter.ActionMenuPopupCallback
extends ActionMenuItemView.PopupCallback {
    ActionMenuPresenter.ActionMenuPopupCallback() {
    }

    @Override
    public ShowableListMenu getPopup() {
        if (ActionMenuPresenter.this.mActionButtonPopup != null) {
            return ActionMenuPresenter.this.mActionButtonPopup.getPopup();
        }
        return null;
    }
}

/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 */
package android.support.v7.view.menu;

import android.support.v7.view.menu.ActionMenuItemView;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.view.menu.MenuItemImpl;
import android.support.v7.view.menu.ShowableListMenu;
import android.support.v7.widget.ForwardingListener;
import android.view.View;

private class ActionMenuItemView.ActionMenuItemForwardingListener
extends ForwardingListener {
    public ActionMenuItemView.ActionMenuItemForwardingListener() {
        super((View)ActionMenuItemView.this);
    }

    @Override
    public ShowableListMenu getPopup() {
        if (ActionMenuItemView.this.mPopupCallback != null) {
            return ActionMenuItemView.this.mPopupCallback.getPopup();
        }
        return null;
    }

    @Override
    protected boolean onForwardingStarted() {
        Object object = ActionMenuItemView.this.mItemInvoker;
        boolean bl = false;
        if (object != null && ActionMenuItemView.this.mItemInvoker.invokeItem(ActionMenuItemView.this.mItemData)) {
            object = this.getPopup();
            boolean bl2 = bl;
            if (object != null) {
                bl2 = bl;
                if (object.isShowing()) {
                    bl2 = true;
                }
            }
            return bl2;
        }
        return false;
    }
}

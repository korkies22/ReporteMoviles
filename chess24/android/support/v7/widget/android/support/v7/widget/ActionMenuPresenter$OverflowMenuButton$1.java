/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 */
package android.support.v7.widget;

import android.support.v7.view.menu.MenuPopup;
import android.support.v7.view.menu.ShowableListMenu;
import android.support.v7.widget.ActionMenuPresenter;
import android.support.v7.widget.ForwardingListener;
import android.view.View;

class ActionMenuPresenter.OverflowMenuButton
extends ForwardingListener {
    final /* synthetic */ ActionMenuPresenter val$this$0;

    ActionMenuPresenter.OverflowMenuButton(View view, ActionMenuPresenter actionMenuPresenter) {
        this.val$this$0 = actionMenuPresenter;
        super(view);
    }

    @Override
    public ShowableListMenu getPopup() {
        if (OverflowMenuButton.this.this$0.mOverflowPopup == null) {
            return null;
        }
        return OverflowMenuButton.this.this$0.mOverflowPopup.getPopup();
    }

    @Override
    public boolean onForwardingStarted() {
        OverflowMenuButton.this.this$0.showOverflowMenu();
        return true;
    }

    @Override
    public boolean onForwardingStopped() {
        if (OverflowMenuButton.this.this$0.mPostedOpenRunnable != null) {
            return false;
        }
        OverflowMenuButton.this.this$0.hideOverflowMenu();
        return true;
    }
}

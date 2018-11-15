/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.view.View
 */
package android.support.v7.widget;

import android.content.Context;
import android.support.v7.appcompat.R;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.view.menu.MenuPopupHelper;
import android.support.v7.view.menu.MenuPresenter;
import android.support.v7.widget.ActionMenuPresenter;
import android.view.View;

private class ActionMenuPresenter.OverflowPopup
extends MenuPopupHelper {
    public ActionMenuPresenter.OverflowPopup(Context context, MenuBuilder menuBuilder, View view, boolean bl) {
        super(context, menuBuilder, view, bl, R.attr.actionOverflowMenuStyle);
        this.setGravity(8388613);
        this.setPresenterCallback(ActionMenuPresenter.this.mPopupPresenterCallback);
    }

    @Override
    protected void onDismiss() {
        if (ActionMenuPresenter.this.mMenu != null) {
            ActionMenuPresenter.this.mMenu.close();
        }
        ActionMenuPresenter.this.mOverflowPopup = null;
        super.onDismiss();
    }
}

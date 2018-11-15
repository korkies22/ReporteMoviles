/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.IBinder
 *  android.view.View
 */
package android.support.v7.widget;

import android.os.IBinder;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.view.menu.MenuView;
import android.support.v7.widget.ActionMenuPresenter;
import android.view.View;

private class ActionMenuPresenter.OpenOverflowRunnable
implements Runnable {
    private ActionMenuPresenter.OverflowPopup mPopup;

    public ActionMenuPresenter.OpenOverflowRunnable(ActionMenuPresenter.OverflowPopup overflowPopup) {
        this.mPopup = overflowPopup;
    }

    @Override
    public void run() {
        View view;
        if (ActionMenuPresenter.this.mMenu != null) {
            ActionMenuPresenter.this.mMenu.changeMenuMode();
        }
        if ((view = (View)ActionMenuPresenter.this.mMenuView) != null && view.getWindowToken() != null && this.mPopup.tryShow()) {
            ActionMenuPresenter.this.mOverflowPopup = this.mPopup;
        }
        ActionMenuPresenter.this.mPostedOpenRunnable = null;
    }
}

/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.View$MeasureSpec
 *  android.view.ViewGroup
 *  android.view.ViewParent
 */
package de.cisha.android.ui.patterns.navigationbar;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import de.cisha.android.ui.patterns.dialogs.ArrowBottomContainerView;
import de.cisha.android.ui.patterns.navigationbar.MenuBarItem;

class MenuBar
implements Runnable {
    final /* synthetic */ MenuBarItem val$mainGroupItem;
    final /* synthetic */ ArrowBottomContainerView val$popup;

    MenuBar(MenuBarItem menuBarItem, ArrowBottomContainerView arrowBottomContainerView) {
        this.val$mainGroupItem = menuBarItem;
        this.val$popup = arrowBottomContainerView;
    }

    @Override
    public void run() {
        MenuBar.this._lastSelectedMainGroupItem = this.val$mainGroupItem;
        MenuBar.this._lastSelectedMainGroupItemWasSelectedBefore = this.val$mainGroupItem.isSelected();
        this.val$mainGroupItem.setSelected(true);
        if (this.val$popup.getParent() == null) {
            MenuBar.this._popupViewGroup.addView((View)this.val$popup, -2, -2);
            this.val$popup.setDrawsArrow(true);
            this.val$popup.measure(View.MeasureSpec.makeMeasureSpec((int)MenuBar.this._popupViewGroup.getWidth(), (int)Integer.MIN_VALUE), View.MeasureSpec.makeMeasureSpec((int)MenuBar.this._popupViewGroup.getHeight(), (int)Integer.MIN_VALUE));
            this.val$popup.clipArrowToView((View)this.val$mainGroupItem);
            MenuBar.this._currentPopup = this.val$popup;
        }
    }
}

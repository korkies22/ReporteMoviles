/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.MenuItem
 */
package android.support.v7.view.menu;

import android.support.v7.view.menu.CascadingMenuPopup;
import android.support.v7.view.menu.MenuBuilder;
import android.view.MenuItem;

class CascadingMenuPopup
implements Runnable {
    final /* synthetic */ MenuItem val$item;
    final /* synthetic */ MenuBuilder val$menu;
    final /* synthetic */ CascadingMenuPopup.CascadingMenuInfo val$nextInfo;

    CascadingMenuPopup(CascadingMenuPopup.CascadingMenuInfo cascadingMenuInfo, MenuItem menuItem, MenuBuilder menuBuilder) {
        this.val$nextInfo = cascadingMenuInfo;
        this.val$item = menuItem;
        this.val$menu = menuBuilder;
    }

    @Override
    public void run() {
        if (this.val$nextInfo != null) {
            3.this.this$0.mShouldCloseImmediately = true;
            this.val$nextInfo.menu.close(false);
            3.this.this$0.mShouldCloseImmediately = false;
        }
        if (this.val$item.isEnabled() && this.val$item.hasSubMenu()) {
            this.val$menu.performItemAction(this.val$item, 4);
        }
    }
}

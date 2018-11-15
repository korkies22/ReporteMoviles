/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.Menu
 *  android.view.Window
 *  android.view.Window$Callback
 */
package android.support.v7.app;

import android.support.v7.app.ToolbarActionBar;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.view.menu.MenuPresenter;
import android.support.v7.widget.DecorToolbar;
import android.view.Menu;
import android.view.Window;

private final class ToolbarActionBar.ActionMenuPresenterCallback
implements MenuPresenter.Callback {
    private boolean mClosingActionMenu;

    ToolbarActionBar.ActionMenuPresenterCallback() {
    }

    @Override
    public void onCloseMenu(MenuBuilder menuBuilder, boolean bl) {
        if (this.mClosingActionMenu) {
            return;
        }
        this.mClosingActionMenu = true;
        ToolbarActionBar.this.mDecorToolbar.dismissPopupMenus();
        if (ToolbarActionBar.this.mWindowCallback != null) {
            ToolbarActionBar.this.mWindowCallback.onPanelClosed(108, (Menu)menuBuilder);
        }
        this.mClosingActionMenu = false;
    }

    @Override
    public boolean onOpenSubMenu(MenuBuilder menuBuilder) {
        if (ToolbarActionBar.this.mWindowCallback != null) {
            ToolbarActionBar.this.mWindowCallback.onMenuOpened(108, (Menu)menuBuilder);
            return true;
        }
        return false;
    }
}

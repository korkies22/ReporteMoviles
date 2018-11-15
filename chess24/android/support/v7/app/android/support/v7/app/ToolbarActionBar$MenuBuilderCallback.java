/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.Menu
 *  android.view.MenuItem
 *  android.view.View
 *  android.view.Window
 *  android.view.Window$Callback
 */
package android.support.v7.app;

import android.support.v7.app.ToolbarActionBar;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.widget.DecorToolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;

private final class ToolbarActionBar.MenuBuilderCallback
implements MenuBuilder.Callback {
    ToolbarActionBar.MenuBuilderCallback() {
    }

    @Override
    public boolean onMenuItemSelected(MenuBuilder menuBuilder, MenuItem menuItem) {
        return false;
    }

    @Override
    public void onMenuModeChange(MenuBuilder menuBuilder) {
        if (ToolbarActionBar.this.mWindowCallback != null) {
            if (ToolbarActionBar.this.mDecorToolbar.isOverflowMenuShowing()) {
                ToolbarActionBar.this.mWindowCallback.onPanelClosed(108, (Menu)menuBuilder);
                return;
            }
            if (ToolbarActionBar.this.mWindowCallback.onPreparePanel(0, null, (Menu)menuBuilder)) {
                ToolbarActionBar.this.mWindowCallback.onMenuOpened(108, (Menu)menuBuilder);
            }
        }
    }
}

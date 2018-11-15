/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.MenuItem
 *  android.view.Window
 *  android.view.Window$Callback
 */
package android.support.v7.app;

import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.Window;

class ToolbarActionBar
implements Toolbar.OnMenuItemClickListener {
    ToolbarActionBar() {
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        return ToolbarActionBar.this.mWindowCallback.onMenuItemSelected(0, menuItem);
    }
}

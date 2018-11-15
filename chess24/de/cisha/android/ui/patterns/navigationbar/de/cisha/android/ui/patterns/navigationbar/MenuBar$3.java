/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.View$OnLongClickListener
 */
package de.cisha.android.ui.patterns.navigationbar;

import android.view.View;

class MenuBar
implements View.OnLongClickListener {
    MenuBar() {
    }

    public boolean onLongClick(View view) {
        MenuBar.this.itemLongClicked(view);
        return false;
    }
}

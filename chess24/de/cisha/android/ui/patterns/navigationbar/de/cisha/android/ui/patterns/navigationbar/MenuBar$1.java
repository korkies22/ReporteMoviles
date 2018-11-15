/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.View$OnClickListener
 */
package de.cisha.android.ui.patterns.navigationbar;

import android.view.View;

class MenuBar
implements View.OnClickListener {
    MenuBar() {
    }

    public void onClick(View view) {
        MenuBar.this.itemInMoreGroupClicked(view);
    }
}

/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.View$OnClickListener
 */
package de.cisha.android.board.mainmenu.view;

import android.view.View;

class MenuGroupView
implements View.OnClickListener {
    MenuGroupView() {
    }

    public void onClick(View view) {
        MenuGroupView.this.toggle();
    }
}

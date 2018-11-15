/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.View$OnClickListener
 */
package de.cisha.android.board.mainmenu;

import android.view.View;

class AbstractMainMenuActivity
implements View.OnClickListener {
    AbstractMainMenuActivity() {
    }

    public void onClick(View view) {
        AbstractMainMenuActivity.this.onBackPressed();
    }
}

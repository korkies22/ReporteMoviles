/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.View$OnClickListener
 */
package de.cisha.android.board;

import android.view.View;
import de.cisha.android.board.AbstractContentFragment;
import de.cisha.android.board.account.StoreFragment;

class MainActivity
implements View.OnClickListener {
    MainActivity() {
    }

    public void onClick(View view) {
        MainActivity.this.showFragment(new StoreFragment(), false, true);
    }
}

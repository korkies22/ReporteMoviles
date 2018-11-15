/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.View$OnClickListener
 */
package de.cisha.android.board;

import android.view.View;

class MainActivity
implements View.OnClickListener {
    MainActivity() {
    }

    public void onClick(View view) {
        MainActivity.this.onBackPressed();
    }
}

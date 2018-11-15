/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.View$OnClickListener
 */
package android.support.v7.widget;

import android.view.View;

class Toolbar
implements View.OnClickListener {
    Toolbar() {
    }

    public void onClick(View view) {
        Toolbar.this.collapseActionView();
    }
}

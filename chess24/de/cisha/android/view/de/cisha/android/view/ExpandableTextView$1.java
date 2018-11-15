/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.View$OnClickListener
 */
package de.cisha.android.view;

import android.view.View;

class ExpandableTextView
implements View.OnClickListener {
    ExpandableTextView() {
    }

    public void onClick(View view) {
        ExpandableTextView.this.toggleMode();
    }
}

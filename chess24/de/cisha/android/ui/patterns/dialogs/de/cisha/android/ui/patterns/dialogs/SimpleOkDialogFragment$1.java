/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.View$OnClickListener
 */
package de.cisha.android.ui.patterns.dialogs;

import android.view.View;

class SimpleOkDialogFragment
implements View.OnClickListener {
    SimpleOkDialogFragment() {
    }

    public void onClick(View view) {
        SimpleOkDialogFragment.this.dismiss();
    }
}

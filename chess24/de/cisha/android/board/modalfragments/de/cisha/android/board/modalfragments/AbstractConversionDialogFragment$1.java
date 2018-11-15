/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.View$OnClickListener
 */
package de.cisha.android.board.modalfragments;

import android.view.View;

class AbstractConversionDialogFragment
implements View.OnClickListener {
    AbstractConversionDialogFragment() {
    }

    public void onClick(View view) {
        AbstractConversionDialogFragment.this.dismiss();
    }
}

/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.View$OnClickListener
 */
package de.cisha.android.board.playzone;

import android.view.View;

class AbstractPlayzoneFragment
implements View.OnClickListener {
    AbstractPlayzoneFragment() {
    }

    public void onClick(View view) {
        AbstractPlayzoneFragment.this.dismissResignDialog();
    }
}

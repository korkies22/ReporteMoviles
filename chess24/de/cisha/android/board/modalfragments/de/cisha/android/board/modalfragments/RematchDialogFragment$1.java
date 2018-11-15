/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.View$OnClickListener
 */
package de.cisha.android.board.modalfragments;

import android.view.View;

class RematchDialogFragment
implements View.OnClickListener {
    RematchDialogFragment() {
    }

    public void onClick(View view) {
        if (RematchDialogFragment.this._rematchClicklistener != null) {
            RematchDialogFragment.this._rematchClicklistener.onClick(view);
        }
        RematchDialogFragment.this.dismiss();
    }
}

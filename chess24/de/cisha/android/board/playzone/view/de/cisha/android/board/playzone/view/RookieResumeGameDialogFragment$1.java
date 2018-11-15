/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.View$OnClickListener
 */
package de.cisha.android.board.playzone.view;

import android.view.View;

class RookieResumeGameDialogFragment
implements View.OnClickListener {
    RookieResumeGameDialogFragment() {
    }

    public void onClick(View view) {
        RookieResumeGameDialogFragment.this.notifyListenerDiscard();
        RookieResumeGameDialogFragment.this.dismiss();
    }
}

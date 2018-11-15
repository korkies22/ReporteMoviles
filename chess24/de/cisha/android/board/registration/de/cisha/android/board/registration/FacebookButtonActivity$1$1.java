/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.View$OnClickListener
 */
package de.cisha.android.board.registration;

import android.view.View;
import de.cisha.android.board.modalfragments.RookieDialogFrament;
import de.cisha.android.board.registration.FacebookButtonActivity;

class FacebookButtonActivity
implements View.OnClickListener {
    final /* synthetic */ RookieDialogFrament val$alertFragement;

    FacebookButtonActivity(RookieDialogFrament rookieDialogFrament) {
        this.val$alertFragement = rookieDialogFrament;
    }

    public void onClick(View view) {
        this.val$alertFragement.dismiss();
    }
}

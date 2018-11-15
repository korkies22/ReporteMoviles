/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.Intent
 *  android.view.View
 *  android.view.View$OnClickListener
 */
package de.cisha.android.board.modalfragments;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import de.cisha.android.board.registration.LoginActivity;

class RookieDialogFrament
implements View.OnClickListener {
    RookieDialogFrament() {
    }

    public void onClick(View view) {
        RookieDialogFrament.this.dismiss();
        RookieDialogFrament.this.startActivity(new Intent((Context)RookieDialogFrament.this.getActivity(), LoginActivity.class));
    }
}

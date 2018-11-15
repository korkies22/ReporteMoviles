/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Intent
 *  android.view.View
 *  android.view.View$OnClickListener
 */
package de.cisha.android.board.modalfragments;

import android.content.Intent;
import android.view.View;

class RookieDialogFrament
implements View.OnClickListener {
    RookieDialogFrament() {
    }

    public void onClick(View view) {
        RookieDialogFrament.this.startActivity(new Intent("android.settings.WIRELESS_SETTINGS"));
    }
}

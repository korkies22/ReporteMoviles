/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Intent
 *  android.content.res.Resources
 *  android.net.Uri
 *  android.view.View
 *  android.view.View$OnClickListener
 */
package de.cisha.android.board.registration;

import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.view.View;

class RegistrationActivity
implements View.OnClickListener {
    RegistrationActivity() {
    }

    public void onClick(View view) {
        view = new Intent("android.intent.action.VIEW", Uri.parse((String)RegistrationActivity.this.getResources().getString(2131690393)));
        RegistrationActivity.this.startActivity((Intent)view);
    }
}

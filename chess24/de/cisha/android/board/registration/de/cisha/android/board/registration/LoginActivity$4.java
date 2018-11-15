/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.Intent
 *  android.view.View
 *  android.view.View$OnClickListener
 */
package de.cisha.android.board.registration;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import de.cisha.android.board.registration.ForgotLoginDataActivity;

class LoginActivity
implements View.OnClickListener {
    LoginActivity() {
    }

    public void onClick(View view) {
        view = new Intent(LoginActivity.this.getApplicationContext(), ForgotLoginDataActivity.class);
        LoginActivity.this.startActivity((Intent)view);
    }
}

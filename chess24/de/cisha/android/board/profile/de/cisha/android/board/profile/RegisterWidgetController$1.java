/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.Intent
 *  android.view.View
 *  android.view.View$OnClickListener
 */
package de.cisha.android.board.profile;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import de.cisha.android.board.registration.LoginActivity;

class RegisterWidgetController
implements View.OnClickListener {
    RegisterWidgetController() {
    }

    public void onClick(View view) {
        view = view.getContext();
        Intent intent = new Intent((Context)view, LoginActivity.class);
        intent.setFlags(131072);
        view.startActivity(intent);
    }
}

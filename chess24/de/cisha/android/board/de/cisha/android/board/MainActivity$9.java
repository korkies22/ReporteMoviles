/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.Intent
 *  android.view.View
 *  android.view.View$OnClickListener
 */
package de.cisha.android.board;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import de.cisha.android.board.registration.LoginActivity;

class MainActivity
implements View.OnClickListener {
    MainActivity() {
    }

    public void onClick(View view) {
        view = new Intent(MainActivity.this.getApplicationContext(), LoginActivity.class);
        view.setFlags(131072);
        MainActivity.this.startActivity((Intent)view);
    }
}

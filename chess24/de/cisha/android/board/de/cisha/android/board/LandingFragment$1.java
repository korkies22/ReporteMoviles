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
import android.support.v4.app.FragmentActivity;
import android.view.View;
import de.cisha.android.board.registration.LoginActivity;

class LandingFragment
implements View.OnClickListener {
    LandingFragment() {
    }

    public void onClick(View view) {
        LandingFragment.this.startActivity(new Intent((Context)LandingFragment.this.getActivity(), LoginActivity.class));
    }
}

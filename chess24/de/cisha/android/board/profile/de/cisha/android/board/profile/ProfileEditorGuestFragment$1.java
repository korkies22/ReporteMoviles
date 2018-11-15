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
import android.support.v4.app.FragmentActivity;
import android.view.View;
import de.cisha.android.board.registration.LoginActivity;

class ProfileEditorGuestFragment
implements View.OnClickListener {
    ProfileEditorGuestFragment() {
    }

    public void onClick(View view) {
        view = new Intent((Context)ProfileEditorGuestFragment.this.getActivity(), LoginActivity.class);
        view.setFlags(131072);
        ProfileEditorGuestFragment.this.startActivity((Intent)view);
    }
}

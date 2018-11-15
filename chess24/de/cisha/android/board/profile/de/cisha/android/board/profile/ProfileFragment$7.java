/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Intent
 */
package de.cisha.android.board.profile;

import android.content.Intent;
import de.cisha.android.ui.patterns.dialogs.RookieMoreDialogFragment;

class ProfileFragment
implements RookieMoreDialogFragment.ListOption {
    ProfileFragment() {
    }

    @Override
    public void executeAction() {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        ProfileFragment.this.startActivityForResult(intent, 5738);
    }

    @Override
    public String getString() {
        return ProfileFragment.this.getString(2131690233);
    }
}

/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.Intent
 */
package de.cisha.android.board.playzone.remote;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import de.cisha.android.board.action.BoardAction;
import de.cisha.android.board.registration.LoginActivity;

class PairingSetupFragment
implements BoardAction {
    PairingSetupFragment() {
    }

    @Override
    public void perform() {
        Intent intent = new Intent(PairingSetupFragment.this.getActivity().getApplicationContext(), LoginActivity.class);
        intent.setFlags(131072);
        PairingSetupFragment.this.startActivity(intent);
    }
}

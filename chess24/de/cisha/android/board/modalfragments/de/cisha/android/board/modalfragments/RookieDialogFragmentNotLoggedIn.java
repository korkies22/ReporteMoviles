/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 */
package de.cisha.android.board.modalfragments;

import android.os.Bundle;
import de.cisha.android.board.modalfragments.RookieDialogFrament;
import de.cisha.android.ui.patterns.dialogs.RookieInfoDialogFragment;

public class RookieDialogFragmentNotLoggedIn
extends RookieDialogFrament {
    @Override
    protected RookieInfoDialogFragment.RookieType getRookieType() {
        return RookieInfoDialogFragment.RookieType.NO_LOGIN;
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.setButtonTypes(RookieDialogFrament.RookieButtonType.CANCEL, RookieDialogFrament.RookieButtonType.LOGIN);
        this.setTitle("Not logged in!");
        this.setText("You are not logged in! Please log in to continue.");
    }
}

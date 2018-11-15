/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 */
package de.cisha.android.board.modalfragments;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import de.cisha.android.board.modalfragments.RookieDialogFrament;
import de.cisha.android.ui.patterns.dialogs.RookieInfoDialogFragment;

public class RookieDialogFragmentNoInternet
extends RookieDialogFrament {
    @Override
    protected RookieInfoDialogFragment.RookieType getRookieType() {
        return RookieInfoDialogFragment.RookieType.NO_INTERNET;
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.setButtonTypes(RookieDialogFrament.RookieButtonType.CANCEL, RookieDialogFrament.RookieButtonType.RELOAD);
        this.setTitle(this.getActivity().getString(2131689951));
        this.setText(this.getActivity().getString(2131689950));
    }
}

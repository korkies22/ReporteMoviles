// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.modalfragments;

import android.os.Bundle;
import de.cisha.android.ui.patterns.dialogs.RookieInfoDialogFragment;

public class RookieDialogFragmentNoInternet extends RookieDialogFrament
{
    @Override
    protected RookieType getRookieType() {
        return RookieType.NO_INTERNET;
    }
    
    @Override
    public void onCreate(final Bundle bundle) {
        super.onCreate(bundle);
        this.setButtonTypes(RookieButtonType.CANCEL, RookieButtonType.RELOAD);
        this.setTitle(this.getActivity().getString(2131689951));
        this.setText(this.getActivity().getString(2131689950));
    }
}

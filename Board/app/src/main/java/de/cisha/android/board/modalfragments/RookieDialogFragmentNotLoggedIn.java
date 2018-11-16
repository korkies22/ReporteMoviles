// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.modalfragments;

import android.os.Bundle;
import de.cisha.android.ui.patterns.dialogs.RookieInfoDialogFragment;

public class RookieDialogFragmentNotLoggedIn extends RookieDialogFrament
{
    @Override
    protected RookieType getRookieType() {
        return RookieType.NO_LOGIN;
    }
    
    @Override
    public void onCreate(final Bundle bundle) {
        super.onCreate(bundle);
        this.setButtonTypes(RookieButtonType.CANCEL, RookieButtonType.LOGIN);
        this.setTitle("Not logged in!");
        this.setText("You are not logged in! Please log in to continue.");
    }
}

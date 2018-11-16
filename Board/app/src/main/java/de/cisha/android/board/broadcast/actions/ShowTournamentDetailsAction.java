// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.broadcast.actions;

import de.cisha.android.board.AbstractContentFragment;
import android.os.Bundle;
import de.cisha.android.board.broadcast.TournamentDetailsFragment;
import de.cisha.android.board.broadcast.model.TournamentInfo;
import de.cisha.android.board.IContentPresenter;
import de.cisha.android.board.action.BoardAction;

public class ShowTournamentDetailsAction implements BoardAction
{
    private IContentPresenter _contentPresenter;
    private TournamentInfo _info;
    
    public ShowTournamentDetailsAction(final IContentPresenter contentPresenter, final TournamentInfo info) {
        this._contentPresenter = contentPresenter;
        this._info = info;
    }
    
    @Override
    public void perform() {
        if (this._info != null && this._contentPresenter != null) {
            final TournamentDetailsFragment tournamentDetailsFragment = new TournamentDetailsFragment();
            final Bundle arguments = new Bundle();
            arguments.putString("TOURNAMENTDETAIL_TOURNAMENTKEY", this._info.getTournamentId().getUuid());
            tournamentDetailsFragment.setArguments(arguments);
            this._contentPresenter.showFragment(tournamentDetailsFragment, true, true);
        }
    }
}

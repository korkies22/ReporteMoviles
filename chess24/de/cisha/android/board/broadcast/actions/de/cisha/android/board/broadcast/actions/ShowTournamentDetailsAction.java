/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 */
package de.cisha.android.board.broadcast.actions;

import android.os.Bundle;
import de.cisha.android.board.AbstractContentFragment;
import de.cisha.android.board.IContentPresenter;
import de.cisha.android.board.action.BoardAction;
import de.cisha.android.board.broadcast.TournamentDetailsFragment;
import de.cisha.android.board.broadcast.model.TournamentID;
import de.cisha.android.board.broadcast.model.TournamentInfo;

public class ShowTournamentDetailsAction
implements BoardAction {
    private IContentPresenter _contentPresenter;
    private TournamentInfo _info;

    public ShowTournamentDetailsAction(IContentPresenter iContentPresenter, TournamentInfo tournamentInfo) {
        this._contentPresenter = iContentPresenter;
        this._info = tournamentInfo;
    }

    @Override
    public void perform() {
        if (this._info != null && this._contentPresenter != null) {
            TournamentDetailsFragment tournamentDetailsFragment = new TournamentDetailsFragment();
            Bundle bundle = new Bundle();
            bundle.putString("TOURNAMENTDETAIL_TOURNAMENTKEY", this._info.getTournamentId().getUuid());
            tournamentDetailsFragment.setArguments(bundle);
            this._contentPresenter.showFragment(tournamentDetailsFragment, true, true);
        }
    }
}

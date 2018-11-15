/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.ViewGroup
 */
package de.cisha.android.board.broadcast.statistics;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import de.cisha.android.board.broadcast.AbstractTeamPairingsFragment;
import de.cisha.android.board.broadcast.model.Tournament;
import de.cisha.android.board.broadcast.model.TournamentHolder;
import de.cisha.android.board.broadcast.model.TournamentTeam;
import de.cisha.android.board.broadcast.model.TournamentTeamPairing;
import java.util.ArrayList;
import java.util.List;

public class SingleTeamPairingsFragment
extends AbstractTeamPairingsFragment
implements TournamentHolder.TournamentTeamSelectionListener {
    private List<TournamentTeamPairing> _pairings = new ArrayList<TournamentTeamPairing>(0);

    @Override
    public void allGameInfosChanged() {
        super.allGameInfosChanged();
        this.selectedTeamChanged(this.getTournamentHolder().getSelectedTeam());
    }

    @Override
    protected List<TournamentTeamPairing> getTeamPairingList() {
        return this._pairings;
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        layoutInflater = super.onCreateView(layoutInflater, viewGroup, bundle);
        this.showRoundsChoser(false);
        return layoutInflater;
    }

    @Override
    public void onResume() {
        super.onResume();
        this.selectedTeamChanged(this.getTournamentHolder().getSelectedTeam());
        this.showRoundsChoser(false);
    }

    @Override
    public void onStart() {
        super.onStart();
        this.selectedTeamChanged(this.getTournamentHolder().getSelectedTeam());
        this.getTournamentHolder().addTeamSelectionListener(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        this.getTournamentHolder().removeTeamSelectionListener(this);
    }

    @Override
    public void selectedTeamChanged(TournamentTeam tournamentTeam) {
        this._pairings = tournamentTeam != null && this.getTournamentHolder().hasTournament() ? this.getTournamentHolder().getTournament().getPairingsForTeam(tournamentTeam) : new ArrayList<TournamentTeamPairing>(0);
        super.allGameInfosChanged();
    }
}

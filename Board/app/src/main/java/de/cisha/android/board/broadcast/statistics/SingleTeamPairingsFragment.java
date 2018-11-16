// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.broadcast.statistics;

import de.cisha.android.board.broadcast.model.TournamentTeam;
import android.view.View;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import java.util.ArrayList;
import de.cisha.android.board.broadcast.model.TournamentTeamPairing;
import java.util.List;
import de.cisha.android.board.broadcast.model.TournamentHolder;
import de.cisha.android.board.broadcast.AbstractTeamPairingsFragment;

public class SingleTeamPairingsFragment extends AbstractTeamPairingsFragment implements TournamentTeamSelectionListener
{
    private List<TournamentTeamPairing> _pairings;
    
    public SingleTeamPairingsFragment() {
        this._pairings = new ArrayList<TournamentTeamPairing>(0);
    }
    
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
    public View onCreateView(final LayoutInflater layoutInflater, final ViewGroup viewGroup, final Bundle bundle) {
        final View onCreateView = super.onCreateView(layoutInflater, viewGroup, bundle);
        this.showRoundsChoser(false);
        return onCreateView;
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
        this.getTournamentHolder().addTeamSelectionListener((TournamentHolder.TournamentTeamSelectionListener)this);
    }
    
    @Override
    public void onStop() {
        super.onStop();
        this.getTournamentHolder().removeTeamSelectionListener((TournamentHolder.TournamentTeamSelectionListener)this);
    }
    
    @Override
    public void selectedTeamChanged(final TournamentTeam tournamentTeam) {
        if (tournamentTeam != null && this.getTournamentHolder().hasTournament()) {
            this._pairings = this.getTournamentHolder().getTournament().getPairingsForTeam(tournamentTeam);
        }
        else {
            this._pairings = new ArrayList<TournamentTeamPairing>(0);
        }
        super.allGameInfosChanged();
    }
}

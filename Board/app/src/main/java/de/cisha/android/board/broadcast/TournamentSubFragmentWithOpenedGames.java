// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.broadcast;

import android.support.v4.app.Fragment;
import de.cisha.chess.util.Logger;
import android.app.Activity;
import de.cisha.android.board.broadcast.model.TournamentGameID;

public class TournamentSubFragmentWithOpenedGames extends TournamentSubFragment implements GameSelectedListener
{
    private GameSelectedListener _listener;
    
    @Override
    public void gameSelected(final TournamentGameID tournamentGameID) {
        if (this._listener != null) {
            this._listener.gameSelected(tournamentGameID);
        }
    }
    
    @Override
    public void onAttach(final Activity activity) {
        super.onAttach(activity);
        final Fragment parentFragment = this.getParentFragment();
        if (parentFragment instanceof GameSelectedListener) {
            this._listener = (GameSelectedListener)parentFragment;
            return;
        }
        if (activity instanceof GameSelectedListener) {
            this._listener = (GameSelectedListener)activity;
            return;
        }
        Logger.getInstance().error(this.getClass().getName(), "Wrong parent fragment class - can't register gameSelectionListener");
    }
    
    @Override
    public void onDetach() {
        this._listener = null;
        super.onDetach();
    }
}

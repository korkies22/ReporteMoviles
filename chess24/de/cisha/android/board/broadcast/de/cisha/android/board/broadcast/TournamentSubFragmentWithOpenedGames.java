/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 */
package de.cisha.android.board.broadcast;

import android.app.Activity;
import android.support.v4.app.Fragment;
import de.cisha.android.board.broadcast.GameSelectedListener;
import de.cisha.android.board.broadcast.TournamentSubFragment;
import de.cisha.android.board.broadcast.model.TournamentGameID;
import de.cisha.chess.util.Logger;

public class TournamentSubFragmentWithOpenedGames
extends TournamentSubFragment
implements GameSelectedListener {
    private GameSelectedListener _listener;

    @Override
    public void gameSelected(TournamentGameID tournamentGameID) {
        if (this._listener != null) {
            this._listener.gameSelected(tournamentGameID);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Fragment fragment = this.getParentFragment();
        if (fragment instanceof GameSelectedListener) {
            this._listener = (GameSelectedListener)((Object)fragment);
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

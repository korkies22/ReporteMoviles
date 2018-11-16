// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.broadcast;

import android.support.v4.app.Fragment;
import de.cisha.chess.util.Logger;
import android.app.Activity;
import de.cisha.android.board.broadcast.model.TournamentHolder;
import de.cisha.android.board.BaseFragment;

public class TournamentSubFragment extends BaseFragment
{
    private TournamentDetailsHolder.ContentFragmentSetter _contentSetter;
    private TournamentHolder _tournamentHolder;
    
    public TournamentSubFragment() {
        this._tournamentHolder = new TournamentHolder();
    }
    
    protected TournamentDetailsHolder.ContentFragmentSetter getContentFragmentSetter() {
        return this._contentSetter;
    }
    
    protected TournamentHolder getTournamentHolder() {
        return this._tournamentHolder;
    }
    
    @Override
    public void onAttach(final Activity activity) {
        super.onAttach(activity);
        final Fragment parentFragment = this.getParentFragment();
        if (parentFragment instanceof TournamentDetailsHolder) {
            final TournamentDetailsHolder tournamentDetailsHolder = (TournamentDetailsHolder)parentFragment;
            this._tournamentHolder = tournamentDetailsHolder.getTournamentHolder();
            this._contentSetter = tournamentDetailsHolder.getContentFragmentSetter();
            return;
        }
        if (activity instanceof TournamentDetailsHolder) {
            final TournamentDetailsHolder tournamentDetailsHolder2 = (TournamentDetailsHolder)activity;
            this._tournamentHolder = tournamentDetailsHolder2.getTournamentHolder();
            this._contentSetter = tournamentDetailsHolder2.getContentFragmentSetter();
            return;
        }
        Logger.getInstance().error(this.getClass().getName(), "attached to wrong parent - can't get Tournament");
        this._tournamentHolder = new TournamentHolder();
    }
    
    @Override
    public void onDetach() {
        this._contentSetter = null;
        super.onDetach();
    }
}

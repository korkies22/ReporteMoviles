/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 */
package de.cisha.android.board.broadcast;

import android.app.Activity;
import android.support.v4.app.Fragment;
import de.cisha.android.board.BaseFragment;
import de.cisha.android.board.broadcast.TournamentDetailsHolder;
import de.cisha.android.board.broadcast.model.TournamentHolder;
import de.cisha.chess.util.Logger;

public class TournamentSubFragment
extends BaseFragment {
    private TournamentDetailsHolder.ContentFragmentSetter _contentSetter;
    private TournamentHolder _tournamentHolder = new TournamentHolder();

    protected TournamentDetailsHolder.ContentFragmentSetter getContentFragmentSetter() {
        return this._contentSetter;
    }

    protected TournamentHolder getTournamentHolder() {
        return this._tournamentHolder;
    }

    @Override
    public void onAttach(Activity object) {
        super.onAttach((Activity)object);
        Fragment fragment = this.getParentFragment();
        if (fragment instanceof TournamentDetailsHolder) {
            object = (TournamentDetailsHolder)((Object)fragment);
            this._tournamentHolder = object.getTournamentHolder();
            this._contentSetter = object.getContentFragmentSetter();
            return;
        }
        if (object instanceof TournamentDetailsHolder) {
            object = (TournamentDetailsHolder)object;
            this._tournamentHolder = object.getTournamentHolder();
            this._contentSetter = object.getContentFragmentSetter();
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

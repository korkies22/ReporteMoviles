/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.view.View
 *  android.view.ViewGroup
 *  android.widget.BaseAdapter
 */
package de.cisha.android.board.broadcast;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import de.cisha.android.board.broadcast.TournamentGamesListFragment;
import de.cisha.android.board.broadcast.model.TournamentGameID;
import de.cisha.android.board.broadcast.model.TournamentGameInfo;
import de.cisha.android.board.broadcast.view.TournamentGameInfoView;
import java.util.List;

class TournamentGamesListFragment.GameListAdapter
extends BaseAdapter {
    TournamentGamesListFragment.GameListAdapter() {
    }

    public int getCount() {
        return TournamentGamesListFragment.this._gamesList.size();
    }

    public Object getItem(int n) {
        return TournamentGamesListFragment.this._gamesList.get(n);
    }

    public long getItemId(int n) {
        return 0L;
    }

    public View getView(int n, View object, ViewGroup object2) {
        object2 = object;
        if (object == null) {
            object2 = new TournamentGameInfoView((Context)TournamentGamesListFragment.this.getActivity(), (TournamentGameInfo)TournamentGamesListFragment.this._gamesList.get(n));
        }
        object = (TournamentGameInfoView)((Object)object2);
        object2 = (TournamentGameInfo)TournamentGamesListFragment.this._gamesList.get(n);
        object.setGameInfo((TournamentGameInfo)object2);
        TournamentGamesListFragment.this.registerGameView(object2.getGameID(), (TournamentGameInfoView)((Object)object));
        return object;
    }
}

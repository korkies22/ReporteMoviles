/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.view.View
 *  android.view.ViewGroup
 *  android.widget.BaseAdapter
 *  android.widget.ListAdapter
 */
package de.cisha.android.board.broadcast;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import de.cisha.android.board.broadcast.AbstractTournamentGamesListFragment;
import de.cisha.android.board.broadcast.model.TournamentGameID;
import de.cisha.android.board.broadcast.model.TournamentGameInfo;
import de.cisha.android.board.broadcast.model.TournamentHolder;
import de.cisha.android.board.broadcast.view.TournamentGameInfoView;
import java.util.LinkedList;
import java.util.List;

public class TournamentGamesListFragment
extends AbstractTournamentGamesListFragment {
    private GameListAdapter _adapter = new GameListAdapter();
    private List<TournamentGameInfo> _gamesList = new LinkedList<TournamentGameInfo>();

    @Override
    public void allGameInfosChanged() {
        this._gamesList = this.getTournamentHolder().getGamesForCurrentRound();
        this.runOnUiThreadBetweenStartAndDestroy(new Runnable(){

            @Override
            public void run() {
                TournamentGamesListFragment.this._adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    protected ListAdapter getListAdapter() {
        return this._adapter;
    }

    @Override
    protected void listItemClicked(int n) {
        if (n < this._gamesList.size()) {
            this.openGame(this._gamesList.get(n).getGameID());
        }
    }

    class GameListAdapter
    extends BaseAdapter {
        GameListAdapter() {
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

}

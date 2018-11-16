// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.broadcast;

import android.content.Context;
import de.cisha.android.board.broadcast.view.TournamentGameInfoView;
import android.view.ViewGroup;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import java.util.LinkedList;
import de.cisha.android.board.broadcast.model.TournamentGameInfo;
import java.util.List;

public class TournamentGamesListFragment extends AbstractTournamentGamesListFragment
{
    private GameListAdapter _adapter;
    private List<TournamentGameInfo> _gamesList;
    
    public TournamentGamesListFragment() {
        this._adapter = new GameListAdapter();
        this._gamesList = new LinkedList<TournamentGameInfo>();
    }
    
    @Override
    public void allGameInfosChanged() {
        this._gamesList = this.getTournamentHolder().getGamesForCurrentRound();
        this.runOnUiThreadBetweenStartAndDestroy(new Runnable() {
            @Override
            public void run() {
                TournamentGamesListFragment.this._adapter.notifyDataSetChanged();
            }
        });
    }
    
    @Override
    protected ListAdapter getListAdapter() {
        return (ListAdapter)this._adapter;
    }
    
    @Override
    protected void listItemClicked(final int n) {
        if (n < this._gamesList.size()) {
            this.openGame(this._gamesList.get(n).getGameID());
        }
    }
    
    class GameListAdapter extends BaseAdapter
    {
        public int getCount() {
            return TournamentGamesListFragment.this._gamesList.size();
        }
        
        public Object getItem(final int n) {
            return TournamentGamesListFragment.this._gamesList.get(n);
        }
        
        public long getItemId(final int n) {
            return 0L;
        }
        
        public View getView(final int n, final View view, final ViewGroup viewGroup) {
            Object o = view;
            if (view == null) {
                o = new TournamentGameInfoView((Context)TournamentGamesListFragment.this.getActivity(), TournamentGamesListFragment.this._gamesList.get(n));
            }
            final TournamentGameInfoView tournamentGameInfoView = (TournamentGameInfoView)o;
            final TournamentGameInfo gameInfo = TournamentGamesListFragment.this._gamesList.get(n);
            tournamentGameInfoView.setGameInfo(gameInfo);
            TournamentGamesListFragment.this.registerGameView(gameInfo.getGameID(), tournamentGameInfoView);
            return (View)tournamentGameInfoView;
        }
    }
}

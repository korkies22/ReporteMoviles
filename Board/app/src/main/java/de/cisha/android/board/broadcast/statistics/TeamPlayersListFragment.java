// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.broadcast.statistics;

import de.cisha.android.board.broadcast.standings.view.StandingsRankingItem;
import android.widget.BaseAdapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.content.Context;
import java.util.LinkedList;
import android.view.View;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import java.util.Iterator;
import java.util.SortedMap;
import java.util.ArrayList;
import de.cisha.android.board.broadcast.model.TournamentTeam;
import de.cisha.android.board.broadcast.TournamentDetailsHolder;
import android.support.v4.app.Fragment;
import android.widget.ListView;
import de.cisha.android.board.broadcast.model.TournamentPlayer;
import java.util.List;
import de.cisha.android.board.broadcast.model.TournamentHolder;
import de.cisha.android.board.broadcast.TournamentSubFragment;

public class TeamPlayersListFragment extends TournamentSubFragment implements TournamentTeamSelectionListener
{
    private List<TournamentPlayer> _players;
    private PlayersListAdapter _playersListAdapter;
    private ListView _playersListView;
    
    private void selectPlayerAtPosition(final int n) {
        final TournamentDetailsHolder.ContentFragmentSetter contentFragmentSetter = this.getContentFragmentSetter();
        if (n < this._players.size() && contentFragmentSetter != null) {
            this.getTournamentHolder().setSelectedPlayer(this._players.get(n));
            contentFragmentSetter.setContentFragment(new SinglePlayerStatisticFragment(), true, "SinglePlayerFragment", "TournamentPlayer");
        }
    }
    
    private void teamChanged(final TournamentTeam tournamentTeam) {
        if (tournamentTeam != null) {
            final SortedMap<String, TournamentPlayer> playersByBoard = tournamentTeam.getPlayersByBoard();
            this._players = new ArrayList<TournamentPlayer>(playersByBoard.size());
            final Iterator<String> iterator = playersByBoard.keySet().iterator();
            while (iterator.hasNext()) {
                this._players.add(playersByBoard.get(iterator.next()));
            }
        }
        else {
            this._players = new ArrayList<TournamentPlayer>(0);
        }
        if (this._playersListAdapter != null) {
            this._playersListAdapter.notifyDataSetChanged();
        }
    }
    
    @Override
    public View onCreateView(final LayoutInflater layoutInflater, final ViewGroup viewGroup, final Bundle bundle) {
        this._players = new LinkedList<TournamentPlayer>();
        this._playersListView = new ListView((Context)this.getActivity());
        this._playersListAdapter = new PlayersListAdapter();
        this._playersListView.setAdapter((ListAdapter)this._playersListAdapter);
        this._playersListView.setOnItemClickListener((AdapterView.OnItemClickListener)new AdapterView.OnItemClickListener() {
            public void onItemClick(final AdapterView<?> adapterView, final View view, final int n, final long n2) {
                TeamPlayersListFragment.this.selectPlayerAtPosition(n);
            }
        });
        return (View)this._playersListView;
    }
    
    @Override
    public void onStart() {
        super.onStart();
        this.teamChanged(this.getTournamentHolder().getSelectedTeam());
        this.getTournamentHolder().addTeamSelectionListener((TournamentHolder.TournamentTeamSelectionListener)this);
    }
    
    @Override
    public void onStop() {
        super.onStop();
        this.getTournamentHolder().removeTeamSelectionListener((TournamentHolder.TournamentTeamSelectionListener)this);
    }
    
    @Override
    public void selectedTeamChanged(final TournamentTeam tournamentTeam) {
        this.teamChanged(tournamentTeam);
    }
    
    private class PlayersListAdapter extends BaseAdapter
    {
        public int getCount() {
            return TeamPlayersListFragment.this._players.size();
        }
        
        public Object getItem(final int n) {
            return TeamPlayersListFragment.this._players.get(n);
        }
        
        public long getItemId(final int n) {
            return TeamPlayersListFragment.this._players.get(n).hashCode();
        }
        
        public View getView(final int n, final View view, final ViewGroup viewGroup) {
            StandingsRankingItem standingsRankingItem = (StandingsRankingItem)view;
            final TournamentPlayer tournamentPlayer = TeamPlayersListFragment.this._players.get(n);
            if (view == null) {
                standingsRankingItem = new StandingsRankingItem((Context)TeamPlayersListFragment.this.getActivity());
                standingsRankingItem.hideLastTwoRows();
            }
            if (tournamentPlayer.getCountry() != null) {
                standingsRankingItem.setShowsFlag(true);
                standingsRankingItem.setFlagImage(tournamentPlayer.getCountry().getImageId());
            }
            else {
                standingsRankingItem.setShowsFlag(false);
            }
            final StringBuilder sb = new StringBuilder();
            sb.append("");
            sb.append(n + 1);
            standingsRankingItem.setItemText(sb.toString(), tournamentPlayer.getNameWithTitleAndRating(), "");
            return (View)standingsRankingItem;
        }
    }
}

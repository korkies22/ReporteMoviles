/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.Bundle
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.ViewGroup
 *  android.widget.AdapterView
 *  android.widget.AdapterView$OnItemClickListener
 *  android.widget.BaseAdapter
 *  android.widget.ListAdapter
 *  android.widget.ListView
 */
package de.cisha.android.board.broadcast.statistics;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import com.neovisionaries.i18n.CountryCode;
import de.cisha.android.board.broadcast.TournamentDetailsHolder;
import de.cisha.android.board.broadcast.TournamentSubFragment;
import de.cisha.android.board.broadcast.model.TournamentHolder;
import de.cisha.android.board.broadcast.model.TournamentPlayer;
import de.cisha.android.board.broadcast.model.TournamentTeam;
import de.cisha.android.board.broadcast.standings.view.StandingsRankingItem;
import de.cisha.android.board.broadcast.statistics.SinglePlayerStatisticFragment;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.SortedMap;

public class TeamPlayersListFragment
extends TournamentSubFragment
implements TournamentHolder.TournamentTeamSelectionListener {
    private List<TournamentPlayer> _players;
    private PlayersListAdapter _playersListAdapter;
    private ListView _playersListView;

    private void selectPlayerAtPosition(int n) {
        TournamentDetailsHolder.ContentFragmentSetter contentFragmentSetter = this.getContentFragmentSetter();
        if (n < this._players.size() && contentFragmentSetter != null) {
            TournamentPlayer tournamentPlayer = this._players.get(n);
            this.getTournamentHolder().setSelectedPlayer(tournamentPlayer);
            contentFragmentSetter.setContentFragment(new SinglePlayerStatisticFragment(), true, "SinglePlayerFragment", "TournamentPlayer");
        }
    }

    private void teamChanged(TournamentTeam object) {
        if (object != null) {
            object = object.getPlayersByBoard();
            this._players = new ArrayList<TournamentPlayer>(object.size());
            Iterator iterator = object.keySet().iterator();
            while (iterator.hasNext()) {
                TournamentPlayer tournamentPlayer = (TournamentPlayer)object.get((String)iterator.next());
                this._players.add(tournamentPlayer);
            }
        } else {
            this._players = new ArrayList<TournamentPlayer>(0);
        }
        if (this._playersListAdapter != null) {
            this._playersListAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this._players = new LinkedList<TournamentPlayer>();
        this._playersListView = new ListView((Context)this.getActivity());
        this._playersListAdapter = new PlayersListAdapter();
        this._playersListView.setAdapter((ListAdapter)this._playersListAdapter);
        this._playersListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            public void onItemClick(AdapterView<?> adapterView, View view, int n, long l) {
                TeamPlayersListFragment.this.selectPlayerAtPosition(n);
            }
        });
        return this._playersListView;
    }

    @Override
    public void onStart() {
        super.onStart();
        this.teamChanged(this.getTournamentHolder().getSelectedTeam());
        this.getTournamentHolder().addTeamSelectionListener(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        this.getTournamentHolder().removeTeamSelectionListener(this);
    }

    @Override
    public void selectedTeamChanged(TournamentTeam tournamentTeam) {
        this.teamChanged(tournamentTeam);
    }

    private class PlayersListAdapter
    extends BaseAdapter {
        private PlayersListAdapter() {
        }

        public int getCount() {
            return TeamPlayersListFragment.this._players.size();
        }

        public Object getItem(int n) {
            return TeamPlayersListFragment.this._players.get(n);
        }

        public long getItemId(int n) {
            return ((TournamentPlayer)TeamPlayersListFragment.this._players.get(n)).hashCode();
        }

        public View getView(int n, View object, ViewGroup object2) {
            object2 = (StandingsRankingItem)((Object)object);
            TournamentPlayer tournamentPlayer = (TournamentPlayer)TeamPlayersListFragment.this._players.get(n);
            if (object == null) {
                object2 = new StandingsRankingItem((Context)TeamPlayersListFragment.this.getActivity());
                object2.hideLastTwoRows();
            }
            if (tournamentPlayer.getCountry() != null) {
                object2.setShowsFlag(true);
                object2.setFlagImage(tournamentPlayer.getCountry().getImageId());
            } else {
                object2.setShowsFlag(false);
            }
            object = new StringBuilder();
            object.append("");
            object.append(n + 1);
            object2.setItemText(object.toString(), tournamentPlayer.getNameWithTitleAndRating(), "");
            return object2;
        }
    }

}

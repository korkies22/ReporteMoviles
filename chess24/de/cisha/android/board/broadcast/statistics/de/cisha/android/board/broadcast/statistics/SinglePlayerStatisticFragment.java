/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.Bundle
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.View$OnClickListener
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
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import de.cisha.android.board.broadcast.TournamentSubFragmentWithOpenedGames;
import de.cisha.android.board.broadcast.model.Tournament;
import de.cisha.android.board.broadcast.model.TournamentGameID;
import de.cisha.android.board.broadcast.model.TournamentGameInfo;
import de.cisha.android.board.broadcast.model.TournamentHolder;
import de.cisha.android.board.broadcast.model.TournamentPlayer;
import de.cisha.android.board.broadcast.statistics.view.BroadcastPlayerView;
import de.cisha.android.board.broadcast.statistics.view.OpponentRowItem;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SinglePlayerStatisticFragment
extends TournamentSubFragmentWithOpenedGames
implements TournamentHolder.TournamentPlayerSelectionListener {
    public static final String TAG = "SinglePlayerStatisticFragment";
    private List<TournamentGameInfo> _gamesForPlayer = new ArrayList<TournamentGameInfo>(0);
    private OpponentListAdapter _opponentListAdapter = new OpponentListAdapter();
    private ListView _opponentListView;
    private TournamentPlayer _player;
    private BroadcastPlayerView _playerHeaderView;

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        layoutInflater = layoutInflater.inflate(2131427485, viewGroup, false);
        this._playerHeaderView = (BroadcastPlayerView)layoutInflater.findViewById(2131296675);
        this._opponentListView = (ListView)layoutInflater.findViewById(2131296674);
        this._opponentListView.setAdapter((ListAdapter)this._opponentListAdapter);
        this._opponentListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            public void onItemClick(AdapterView<?> object, View view, int n, long l) {
                if (n < SinglePlayerStatisticFragment.this._gamesForPlayer.size() && (object = ((TournamentGameInfo)SinglePlayerStatisticFragment.this._gamesForPlayer.get(n)).getOpponentOfPlayer(SinglePlayerStatisticFragment.this._player)) != null) {
                    SinglePlayerStatisticFragment.this.getTournamentHolder().setSelectedPlayer((TournamentPlayer)object);
                }
            }
        });
        return layoutInflater;
    }

    @Override
    public void onStart() {
        super.onStart();
        this.selectedPlayerChanged(this.getTournamentHolder().getSelectedPlayer());
        this.getTournamentHolder().addPlayerSelectionListener(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        this.getTournamentHolder().removePlayerSelectionListener(this);
    }

    @Override
    public void selectedPlayerChanged(final TournamentPlayer tournamentPlayer) {
        this.runOnUiThreadBetweenStartAndDestroy(new Runnable(){

            @Override
            public void run() {
                if (SinglePlayerStatisticFragment.this.getTournamentHolder().hasTournament()) {
                    SinglePlayerStatisticFragment.this._player = tournamentPlayer;
                    Tournament tournament = SinglePlayerStatisticFragment.this.getTournamentHolder().getTournament();
                    SinglePlayerStatisticFragment.this._playerHeaderView.setDataForPlayer(tournamentPlayer, tournament);
                    SinglePlayerStatisticFragment.this._gamesForPlayer = tournament.getGamesForPlayer(tournamentPlayer);
                    Collections.sort(SinglePlayerStatisticFragment.this._gamesForPlayer, new Comparator<TournamentGameInfo>(){

                        @Override
                        public int compare(TournamentGameInfo tournamentGameInfo, TournamentGameInfo tournamentGameInfo2) {
                            if (tournamentGameInfo != null && tournamentGameInfo2 != null) {
                                return tournamentGameInfo.getRoundNumber() - tournamentGameInfo2.getRoundNumber();
                            }
                            return 0;
                        }
                    });
                    SinglePlayerStatisticFragment.this._opponentListAdapter.notifyDataSetChanged();
                }
            }

        });
    }

    private class OpponentListAdapter
    extends BaseAdapter {
        private OpponentListAdapter() {
        }

        public int getCount() {
            return SinglePlayerStatisticFragment.this._gamesForPlayer.size();
        }

        public Object getItem(int n) {
            return SinglePlayerStatisticFragment.this._gamesForPlayer.get(n);
        }

        public long getItemId(int n) {
            return ((TournamentGameInfo)SinglePlayerStatisticFragment.this._gamesForPlayer.get(n)).hashCode();
        }

        public View getView(final int n, View object, ViewGroup object2) {
            object2 = (OpponentRowItem)((Object)object);
            object = object2;
            if (object2 == null) {
                object = new OpponentRowItem((Context)SinglePlayerStatisticFragment.this.getActivity());
            }
            object.setDataForOpponentOfPlayer(SinglePlayerStatisticFragment.this._player, (TournamentGameInfo)SinglePlayerStatisticFragment.this._gamesForPlayer.get(n));
            object.setGameSelectionListener(new View.OnClickListener(){

                public void onClick(View view) {
                    if (n < SinglePlayerStatisticFragment.this._gamesForPlayer.size()) {
                        SinglePlayerStatisticFragment.this.gameSelected(((TournamentGameInfo)SinglePlayerStatisticFragment.this._gamesForPlayer.get(n)).getGameID());
                    }
                }
            });
            return object;
        }

    }

}

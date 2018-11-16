// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.broadcast.statistics;

import android.view.View.OnClickListener;
import android.content.Context;
import de.cisha.android.board.broadcast.statistics.view.OpponentRowItem;
import android.widget.BaseAdapter;
import de.cisha.android.board.broadcast.model.Tournament;
import java.util.Collections;
import java.util.Comparator;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.view.View;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import java.util.ArrayList;
import de.cisha.android.board.broadcast.statistics.view.BroadcastPlayerView;
import de.cisha.android.board.broadcast.model.TournamentPlayer;
import android.widget.ListView;
import de.cisha.android.board.broadcast.model.TournamentGameInfo;
import java.util.List;
import de.cisha.android.board.broadcast.model.TournamentHolder;
import de.cisha.android.board.broadcast.TournamentSubFragmentWithOpenedGames;

public class SinglePlayerStatisticFragment extends TournamentSubFragmentWithOpenedGames implements TournamentPlayerSelectionListener
{
    public static final String TAG = "SinglePlayerStatisticFragment";
    private List<TournamentGameInfo> _gamesForPlayer;
    private OpponentListAdapter _opponentListAdapter;
    private ListView _opponentListView;
    private TournamentPlayer _player;
    private BroadcastPlayerView _playerHeaderView;
    
    public SinglePlayerStatisticFragment() {
        this._gamesForPlayer = new ArrayList<TournamentGameInfo>(0);
        this._opponentListAdapter = new OpponentListAdapter();
    }
    
    @Override
    public View onCreateView(final LayoutInflater layoutInflater, final ViewGroup viewGroup, final Bundle bundle) {
        final View inflate = layoutInflater.inflate(2131427485, viewGroup, false);
        this._playerHeaderView = (BroadcastPlayerView)inflate.findViewById(2131296675);
        (this._opponentListView = (ListView)inflate.findViewById(2131296674)).setAdapter((ListAdapter)this._opponentListAdapter);
        this._opponentListView.setOnItemClickListener((AdapterView.OnItemClickListener)new AdapterView.OnItemClickListener() {
            public void onItemClick(final AdapterView<?> adapterView, final View view, final int n, final long n2) {
                if (n < SinglePlayerStatisticFragment.this._gamesForPlayer.size()) {
                    final TournamentPlayer opponentOfPlayer = SinglePlayerStatisticFragment.this._gamesForPlayer.get(n).getOpponentOfPlayer(SinglePlayerStatisticFragment.this._player);
                    if (opponentOfPlayer != null) {
                        SinglePlayerStatisticFragment.this.getTournamentHolder().setSelectedPlayer(opponentOfPlayer);
                    }
                }
            }
        });
        return inflate;
    }
    
    @Override
    public void onStart() {
        super.onStart();
        this.selectedPlayerChanged(this.getTournamentHolder().getSelectedPlayer());
        this.getTournamentHolder().addPlayerSelectionListener((TournamentHolder.TournamentPlayerSelectionListener)this);
    }
    
    @Override
    public void onStop() {
        super.onStop();
        this.getTournamentHolder().removePlayerSelectionListener((TournamentHolder.TournamentPlayerSelectionListener)this);
    }
    
    @Override
    public void selectedPlayerChanged(final TournamentPlayer tournamentPlayer) {
        this.runOnUiThreadBetweenStartAndDestroy(new Runnable() {
            @Override
            public void run() {
                if (SinglePlayerStatisticFragment.this.getTournamentHolder().hasTournament()) {
                    SinglePlayerStatisticFragment.this._player = tournamentPlayer;
                    final Tournament tournament = SinglePlayerStatisticFragment.this.getTournamentHolder().getTournament();
                    SinglePlayerStatisticFragment.this._playerHeaderView.setDataForPlayer(tournamentPlayer, tournament);
                    SinglePlayerStatisticFragment.this._gamesForPlayer = tournament.getGamesForPlayer(tournamentPlayer);
                    Collections.sort((List<Object>)SinglePlayerStatisticFragment.this._gamesForPlayer, (Comparator<? super Object>)new Comparator<TournamentGameInfo>() {
                        @Override
                        public int compare(final TournamentGameInfo tournamentGameInfo, final TournamentGameInfo tournamentGameInfo2) {
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
    
    private class OpponentListAdapter extends BaseAdapter
    {
        public int getCount() {
            return SinglePlayerStatisticFragment.this._gamesForPlayer.size();
        }
        
        public Object getItem(final int n) {
            return SinglePlayerStatisticFragment.this._gamesForPlayer.get(n);
        }
        
        public long getItemId(final int n) {
            return SinglePlayerStatisticFragment.this._gamesForPlayer.get(n).hashCode();
        }
        
        public View getView(final int n, final View view, final ViewGroup viewGroup) {
            OpponentRowItem opponentRowItem;
            if ((opponentRowItem = (OpponentRowItem)view) == null) {
                opponentRowItem = new OpponentRowItem((Context)SinglePlayerStatisticFragment.this.getActivity());
            }
            opponentRowItem.setDataForOpponentOfPlayer(SinglePlayerStatisticFragment.this._player, SinglePlayerStatisticFragment.this._gamesForPlayer.get(n));
            opponentRowItem.setGameSelectionListener((View.OnClickListener)new View.OnClickListener() {
                public void onClick(final View view) {
                    if (n < SinglePlayerStatisticFragment.this._gamesForPlayer.size()) {
                        SinglePlayerStatisticFragment.this.gameSelected(SinglePlayerStatisticFragment.this._gamesForPlayer.get(n).getGameID());
                    }
                }
            });
            return (View)opponentRowItem;
        }
    }
}

// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.broadcast;

import de.cisha.android.board.broadcast.view.TournamentGameInfoView;
import android.view.View.OnClickListener;
import android.content.Context;
import android.view.ViewGroup;
import android.view.View;
import de.cisha.android.ui.list.SectionedListAdapter;
import android.widget.ListAdapter;
import java.util.Iterator;
import java.util.Collection;
import java.util.HashMap;
import java.util.ArrayList;
import de.cisha.android.board.broadcast.view.TournamentTeamMatchView;
import de.cisha.android.board.TwoSidedHashMap;
import de.cisha.android.board.broadcast.model.TournamentGameInfo;
import java.util.List;
import de.cisha.android.board.broadcast.model.TournamentTeamPairing;
import java.util.Map;

public abstract class AbstractTeamPairingsFragment extends AbstractTournamentGamesListFragment
{
    private Map<TournamentTeamPairing, List<TournamentGameInfo>> _gamesByPairing;
    private Map<TournamentGameInfo, TournamentTeamPairing> _mapGameTeam;
    private TwoSidedHashMap<TournamentTeamMatchView, TournamentTeamPairing> _mapViewPairing;
    private List<TournamentTeamPairing> _matchList;
    private TeamMatchListAdapter _teamMatchListAdapter;
    
    public AbstractTeamPairingsFragment() {
        this._matchList = new ArrayList<TournamentTeamPairing>();
        this._gamesByPairing = new HashMap<TournamentTeamPairing, List<TournamentGameInfo>>();
        this._mapViewPairing = new TwoSidedHashMap<TournamentTeamMatchView, TournamentTeamPairing>();
        this._mapGameTeam = new HashMap<TournamentGameInfo, TournamentTeamPairing>();
    }
    
    @Override
    public void allGameInfosChanged() {
        final List<TournamentTeamPairing> teamPairingList = this.getTeamPairingList();
        this._matchList.clear();
        this._matchList.addAll(teamPairingList);
        this._gamesByPairing.clear();
        for (final TournamentTeamPairing tournamentTeamPairing : teamPairingList) {
            this._gamesByPairing.put(tournamentTeamPairing, tournamentTeamPairing.getGames());
            final Iterator<TournamentGameInfo> iterator2 = tournamentTeamPairing.getGames().iterator();
            while (iterator2.hasNext()) {
                this._mapGameTeam.put(iterator2.next(), tournamentTeamPairing);
            }
        }
        if (this._teamMatchListAdapter != null) {
            this._teamMatchListAdapter.notifyDataSetChanged();
        }
    }
    
    @Override
    public void gameInfoChanged(final TournamentGameInfo tournamentGameInfo) {
        super.gameInfoChanged(tournamentGameInfo);
        this.runOnUiThreadBetweenStartAndDestroy(new Runnable() {
            @Override
            public void run() {
                final TournamentTeamPairing tournamentTeamPairing = AbstractTeamPairingsFragment.this._mapGameTeam.get(tournamentGameInfo);
                if (tournamentTeamPairing != null) {
                    final TournamentTeamMatchView tournamentTeamMatchView = AbstractTeamPairingsFragment.this._mapViewPairing.getKeyForValue(tournamentTeamPairing);
                    if (tournamentTeamMatchView != null) {
                        tournamentTeamMatchView.setMatchIsOngoing(tournamentTeamPairing.isOngoing());
                        tournamentTeamMatchView.setPointsText(tournamentTeamPairing.getPointsTeam1(), tournamentTeamPairing.getPointsTeam2());
                    }
                }
            }
        });
    }
    
    @Override
    protected ListAdapter getListAdapter() {
        if (this._teamMatchListAdapter == null) {
            this._teamMatchListAdapter = new TeamMatchListAdapter();
        }
        return (ListAdapter)this._teamMatchListAdapter;
    }
    
    protected abstract List<TournamentTeamPairing> getTeamPairingList();
    
    @Override
    protected void listItemClicked(final int n) {
        if (this._teamMatchListAdapter != null) {
            final TournamentGameInfo tournamentGameInfo = ((SectionedListAdapter<S, TournamentGameInfo>)this._teamMatchListAdapter).getValueForPosition(n);
            if (tournamentGameInfo != null) {
                this.openGame(tournamentGameInfo.getGameID());
            }
        }
    }
    
    protected void registerMatchView(final TournamentTeamPairing tournamentTeamPairing, final TournamentTeamMatchView tournamentTeamMatchView) {
        this._mapViewPairing.put(tournamentTeamMatchView, tournamentTeamPairing);
    }
    
    class TeamMatchListAdapter extends SectionedListAdapter<TournamentTeamPairing, TournamentGameInfo>
    {
        public TeamMatchListAdapter() {
            super(AbstractTeamPairingsFragment.this._gamesByPairing, AbstractTeamPairingsFragment.this._matchList, false);
        }
        
        @Override
        protected View getViewForSectionHeader(final TournamentTeamPairing tournamentTeamPairing, final View view, final ViewGroup viewGroup) {
            TournamentTeamMatchView tournamentTeamMatchView;
            if (view != null && view instanceof TournamentTeamMatchView) {
                tournamentTeamMatchView = (TournamentTeamMatchView)view;
            }
            else {
                tournamentTeamMatchView = new TournamentTeamMatchView((Context)AbstractTeamPairingsFragment.this.getActivity());
            }
            tournamentTeamMatchView.setMatchIsOngoing(tournamentTeamPairing.isOngoing());
            tournamentTeamMatchView.setPointsText(tournamentTeamPairing.getPointsTeam1(), tournamentTeamPairing.getPointsTeam2());
            String name;
            if (tournamentTeamPairing.getTeam1() != null) {
                name = tournamentTeamPairing.getTeam1().getName();
            }
            else {
                name = "";
            }
            String name2;
            if (tournamentTeamPairing.getTeam2() != null) {
                name2 = tournamentTeamPairing.getTeam2().getName();
            }
            else {
                name2 = "";
            }
            tournamentTeamMatchView.setTeamName(name, name2);
            tournamentTeamMatchView.setOnClickListener((View.OnClickListener)new View.OnClickListener() {
                public void onClick(final View view) {
                    ((SectionedListAdapter<TournamentTeamPairing, V>)TeamMatchListAdapter.this).toggleOpenCloseSection(tournamentTeamPairing);
                }
            });
            AbstractTeamPairingsFragment.this.registerMatchView(tournamentTeamPairing, tournamentTeamMatchView);
            return (View)tournamentTeamMatchView;
        }
        
        @Override
        protected View getViewForValue(final TournamentGameInfo gameInfo, final View view, final ViewGroup viewGroup) {
            Object o = null;
            Label_0029: {
                if (view != null) {
                    o = view;
                    if (view instanceof TournamentGameInfoView) {
                        break Label_0029;
                    }
                }
                o = new TournamentGameInfoView((Context)AbstractTeamPairingsFragment.this.getActivity(), gameInfo);
            }
            final TournamentGameInfoView tournamentGameInfoView = (TournamentGameInfoView)o;
            tournamentGameInfoView.setGameInfo(gameInfo);
            AbstractTeamPairingsFragment.this.registerGameView(gameInfo.getGameID(), tournamentGameInfoView);
            return (View)tournamentGameInfoView;
        }
    }
}

/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.widget.ListAdapter
 */
package de.cisha.android.board.broadcast;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import de.cisha.android.board.TwoSidedHashMap;
import de.cisha.android.board.broadcast.AbstractTournamentGamesListFragment;
import de.cisha.android.board.broadcast.model.TournamentGameID;
import de.cisha.android.board.broadcast.model.TournamentGameInfo;
import de.cisha.android.board.broadcast.model.TournamentTeam;
import de.cisha.android.board.broadcast.model.TournamentTeamPairing;
import de.cisha.android.board.broadcast.view.TournamentGameInfoView;
import de.cisha.android.board.broadcast.view.TournamentTeamMatchView;
import de.cisha.android.ui.list.SectionedListAdapter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public abstract class AbstractTeamPairingsFragment
extends AbstractTournamentGamesListFragment {
    private Map<TournamentTeamPairing, List<TournamentGameInfo>> _gamesByPairing = new HashMap<TournamentTeamPairing, List<TournamentGameInfo>>();
    private Map<TournamentGameInfo, TournamentTeamPairing> _mapGameTeam = new HashMap<TournamentGameInfo, TournamentTeamPairing>();
    private TwoSidedHashMap<TournamentTeamMatchView, TournamentTeamPairing> _mapViewPairing = new TwoSidedHashMap();
    private List<TournamentTeamPairing> _matchList = new ArrayList<TournamentTeamPairing>();
    private TeamMatchListAdapter _teamMatchListAdapter;

    @Override
    public void allGameInfosChanged() {
        Object object = this.getTeamPairingList();
        this._matchList.clear();
        this._matchList.addAll((Collection<TournamentTeamPairing>)object);
        this._gamesByPairing.clear();
        object = object.iterator();
        while (object.hasNext()) {
            TournamentTeamPairing tournamentTeamPairing = (TournamentTeamPairing)object.next();
            this._gamesByPairing.put(tournamentTeamPairing, tournamentTeamPairing.getGames());
            for (TournamentGameInfo tournamentGameInfo : tournamentTeamPairing.getGames()) {
                this._mapGameTeam.put(tournamentGameInfo, tournamentTeamPairing);
            }
        }
        if (this._teamMatchListAdapter != null) {
            this._teamMatchListAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void gameInfoChanged(final TournamentGameInfo tournamentGameInfo) {
        super.gameInfoChanged(tournamentGameInfo);
        this.runOnUiThreadBetweenStartAndDestroy(new Runnable(){

            @Override
            public void run() {
                TournamentTeamMatchView tournamentTeamMatchView;
                TournamentTeamPairing tournamentTeamPairing = (TournamentTeamPairing)AbstractTeamPairingsFragment.this._mapGameTeam.get(tournamentGameInfo);
                if (tournamentTeamPairing != null && (tournamentTeamMatchView = (TournamentTeamMatchView)((Object)AbstractTeamPairingsFragment.this._mapViewPairing.getKeyForValue(tournamentTeamPairing))) != null) {
                    tournamentTeamMatchView.setMatchIsOngoing(tournamentTeamPairing.isOngoing());
                    tournamentTeamMatchView.setPointsText(tournamentTeamPairing.getPointsTeam1(), tournamentTeamPairing.getPointsTeam2());
                }
            }
        });
    }

    @Override
    protected ListAdapter getListAdapter() {
        if (this._teamMatchListAdapter == null) {
            this._teamMatchListAdapter = new TeamMatchListAdapter();
        }
        return this._teamMatchListAdapter;
    }

    protected abstract List<TournamentTeamPairing> getTeamPairingList();

    @Override
    protected void listItemClicked(int n) {
        TournamentGameInfo tournamentGameInfo;
        if (this._teamMatchListAdapter != null && (tournamentGameInfo = (TournamentGameInfo)this._teamMatchListAdapter.getValueForPosition(n)) != null) {
            this.openGame(tournamentGameInfo.getGameID());
        }
    }

    protected void registerMatchView(TournamentTeamPairing tournamentTeamPairing, TournamentTeamMatchView tournamentTeamMatchView) {
        this._mapViewPairing.put(tournamentTeamMatchView, tournamentTeamPairing);
    }

    class TeamMatchListAdapter
    extends SectionedListAdapter<TournamentTeamPairing, TournamentGameInfo> {
        public TeamMatchListAdapter() {
            super(AbstractTeamPairingsFragment.this._gamesByPairing, AbstractTeamPairingsFragment.this._matchList, false);
        }

        @Override
        protected View getViewForSectionHeader(final TournamentTeamPairing tournamentTeamPairing, View object, ViewGroup object2) {
            object = object != null && object instanceof TournamentTeamMatchView ? (TournamentTeamMatchView)((Object)object) : new TournamentTeamMatchView((Context)AbstractTeamPairingsFragment.this.getActivity());
            object.setMatchIsOngoing(tournamentTeamPairing.isOngoing());
            object.setPointsText(tournamentTeamPairing.getPointsTeam1(), tournamentTeamPairing.getPointsTeam2());
            object2 = tournamentTeamPairing.getTeam1() != null ? tournamentTeamPairing.getTeam1().getName() : "";
            String string = tournamentTeamPairing.getTeam2() != null ? tournamentTeamPairing.getTeam2().getName() : "";
            object.setTeamName((String)object2, string);
            object.setOnClickListener(new View.OnClickListener(){

                public void onClick(View view) {
                    TeamMatchListAdapter.this.toggleOpenCloseSection(tournamentTeamPairing);
                }
            });
            AbstractTeamPairingsFragment.this.registerMatchView(tournamentTeamPairing, (TournamentTeamMatchView)((Object)object));
            return object;
        }

        @Override
        protected View getViewForValue(TournamentGameInfo tournamentGameInfo, View object, ViewGroup object2) {
            block3 : {
                block2 : {
                    if (object == null) break block2;
                    object2 = object;
                    if (object instanceof TournamentGameInfoView) break block3;
                }
                object2 = new TournamentGameInfoView((Context)AbstractTeamPairingsFragment.this.getActivity(), tournamentGameInfo);
            }
            object = (TournamentGameInfoView)((Object)object2);
            object.setGameInfo(tournamentGameInfo);
            AbstractTeamPairingsFragment.this.registerGameView(tournamentGameInfo.getGameID(), (TournamentGameInfoView)((Object)object));
            return object;
        }

    }

}

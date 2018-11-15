/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 */
package de.cisha.android.board.broadcast;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import de.cisha.android.board.broadcast.AbstractTeamPairingsFragment;
import de.cisha.android.board.broadcast.model.TournamentGameID;
import de.cisha.android.board.broadcast.model.TournamentGameInfo;
import de.cisha.android.board.broadcast.model.TournamentTeam;
import de.cisha.android.board.broadcast.model.TournamentTeamPairing;
import de.cisha.android.board.broadcast.view.TournamentGameInfoView;
import de.cisha.android.board.broadcast.view.TournamentTeamMatchView;
import de.cisha.android.ui.list.SectionedListAdapter;
import java.util.List;
import java.util.Map;

class AbstractTeamPairingsFragment.TeamMatchListAdapter
extends SectionedListAdapter<TournamentTeamPairing, TournamentGameInfo> {
    public AbstractTeamPairingsFragment.TeamMatchListAdapter() {
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

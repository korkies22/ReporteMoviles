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
package de.cisha.android.board.broadcast.standings;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import de.cisha.android.board.broadcast.TournamentDetailsHolder;
import de.cisha.android.board.broadcast.model.TeamStanding;
import de.cisha.android.board.broadcast.model.Tournament;
import de.cisha.android.board.broadcast.model.TournamentHolder;
import de.cisha.android.board.broadcast.model.TournamentRoundInfo;
import de.cisha.android.board.broadcast.model.TournamentTeam;
import de.cisha.android.board.broadcast.standings.AbstractStandingsFragment;
import de.cisha.android.board.broadcast.standings.view.StandingsRankingItem;
import de.cisha.android.board.broadcast.statistics.TeamStatisticsFragment;
import de.cisha.android.ui.patterns.text.CustomTextViewTextStyle;
import de.cisha.chess.model.Country;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.List;

public class TournamentTeamStandingsFragment
extends AbstractStandingsFragment {
    public static final String TAGNAME = "TournamentTeamStandingsRoundRobin";
    private List<TournamentTeam> _standings = new LinkedList<TournamentTeam>();
    private StandingsListAdapter _standingsListAdapter = new StandingsListAdapter();

    @Override
    protected boolean canSelectItems() {
        return true;
    }

    @Override
    protected View createHeaderView() {
        StandingsRankingItem standingsRankingItem = new StandingsRankingItem((Context)this.getActivity());
        standingsRankingItem.setIsTableHeader();
        standingsRankingItem.setItemText(this.getString(2131689589), this.getString(2131689592), this.getString(2131689593), this.getString(2131689588));
        return standingsRankingItem;
    }

    @Override
    protected ListAdapter createStandingsListAdapter() {
        return this._standingsListAdapter;
    }

    @Override
    protected void itemSelected(int n) {
        if (n < this._standings.size()) {
            Object object = this._standings.get(n);
            if (this.getTournamentHolder() != null && this.getContentFragmentSetter() != null) {
                this.getTournamentHolder().setSelectedTeam((TournamentTeam)object);
                object = new TeamStatisticsFragment();
                this.getContentFragmentSetter().setContentFragment((Fragment)object, true, "TeamDetailFragment", "TournamentTeam");
            }
        }
    }

    @Override
    public void registeredForChangesOn(Tournament tournament, TournamentRoundInfo tournamentRoundInfo, boolean bl) {
        this._standings = tournament.getTeamStandings();
        if (this._standings == null) {
            this._standings = new LinkedList<TournamentTeam>();
        }
        this.runOnUiThreadBetweenStartAndDestroy(new Runnable(){

            @Override
            public void run() {
                TournamentTeamStandingsFragment.this._standingsListAdapter.notifyDataSetChanged();
            }
        });
    }

    class StandingsListAdapter
    extends BaseAdapter {
        StandingsListAdapter() {
        }

        public int getCount() {
            return TournamentTeamStandingsFragment.this._standings.size();
        }

        public Object getItem(int n) {
            return TournamentTeamStandingsFragment.this._standings.get(n);
        }

        public long getItemId(int n) {
            return 0L;
        }

        public View getView(int n, View object, ViewGroup object2) {
            Serializable serializable;
            if (object == null) {
                object = new StandingsRankingItem((Context)TournamentTeamStandingsFragment.this.getActivity());
                object.setNameTextStyle(CustomTextViewTextStyle.TABLE);
            } else {
                object = (StandingsRankingItem)((Object)object);
            }
            TournamentTeam tournamentTeam = (TournamentTeam)TournamentTeamStandingsFragment.this._standings.get(n);
            object2 = tournamentTeam.getCurrentStandings();
            String string = "";
            if (object2 != null) {
                serializable = DecimalFormat.getInstance();
                serializable.setMinimumFractionDigits(0);
                serializable.setMaximumFractionDigits(1);
                string = serializable.format(object2.getTeamPoints());
                object2 = serializable.format(object2.getBoardPoints());
            } else {
                object2 = "";
            }
            serializable = new StringBuilder();
            serializable.append(n + 1);
            serializable.append(".");
            object.setItemText(serializable.toString(), tournamentTeam.getName(), string, (String)object2);
            object2 = tournamentTeam.getCountry();
            if (object2 != null) {
                object.setFlagImage(object2.getImageId());
                object.setShowsFlag(true);
                return object;
            }
            object.setShowsFlag(false);
            return object;
        }

        public boolean isEnabled(int n) {
            return true;
        }
    }

}

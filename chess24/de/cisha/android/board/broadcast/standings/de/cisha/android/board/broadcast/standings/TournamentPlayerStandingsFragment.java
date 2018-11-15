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
import com.neovisionaries.i18n.CountryCode;
import de.cisha.android.board.broadcast.TournamentDetailsHolder;
import de.cisha.android.board.broadcast.model.PlayerStanding;
import de.cisha.android.board.broadcast.model.Tournament;
import de.cisha.android.board.broadcast.model.TournamentHolder;
import de.cisha.android.board.broadcast.model.TournamentPlayer;
import de.cisha.android.board.broadcast.model.TournamentRoundInfo;
import de.cisha.android.board.broadcast.standings.AbstractStandingsFragment;
import de.cisha.android.board.broadcast.standings.view.StandingsRankingItem;
import de.cisha.android.board.broadcast.statistics.SinglePlayerStatisticFragment;
import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.List;

public class TournamentPlayerStandingsFragment
extends AbstractStandingsFragment {
    public static final String TAGNAME = "TournamentPlayerStandings";
    private List<TournamentPlayer> _standings = new LinkedList<TournamentPlayer>();
    private StandingsListAdapter _standingsListAdapter = new StandingsListAdapter();

    @Override
    protected boolean canSelectItems() {
        return true;
    }

    @Override
    protected View createHeaderView() {
        StandingsRankingItem standingsRankingItem = new StandingsRankingItem((Context)this.getActivity());
        standingsRankingItem.setIsTableHeader();
        standingsRankingItem.hideLastRow();
        standingsRankingItem.setItemText(this.getString(2131689589), this.getString(2131689590), this.getString(2131689591));
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
            this.getTournamentHolder().setSelectedPlayer((TournamentPlayer)object);
            object = new SinglePlayerStatisticFragment();
            this.getContentFragmentSetter().setContentFragment((Fragment)object, true, "SinglePlayerStatisticFragment", "TournamentPlayer");
        }
    }

    @Override
    public void registeredForChangesOn(Tournament tournament, TournamentRoundInfo tournamentRoundInfo, boolean bl) {
        this._standings = tournament.getPlayerStandings();
        if (this._standings == null) {
            this._standings = new LinkedList<TournamentPlayer>();
        }
        this.runOnUiThreadBetweenStartAndDestroy(new Runnable(){

            @Override
            public void run() {
                TournamentPlayerStandingsFragment.this._standingsListAdapter.notifyDataSetChanged();
            }
        });
    }

    class StandingsListAdapter
    extends BaseAdapter {
        StandingsListAdapter() {
        }

        public int getCount() {
            return TournamentPlayerStandingsFragment.this._standings.size();
        }

        public Object getItem(int n) {
            return TournamentPlayerStandingsFragment.this._standings.get(n);
        }

        public long getItemId(int n) {
            return 0L;
        }

        public View getView(int n, View object, ViewGroup object2) {
            if (object == null) {
                object = new StandingsRankingItem((Context)TournamentPlayerStandingsFragment.this.getActivity());
                object.hideLastRow();
            } else {
                object = (StandingsRankingItem)((Object)object);
            }
            TournamentPlayer tournamentPlayer = (TournamentPlayer)TournamentPlayerStandingsFragment.this._standings.get(n);
            Object object3 = tournamentPlayer.getStanding();
            object2 = "";
            if (object3 != null) {
                object2 = DecimalFormat.getInstance();
                object2.setMinimumFractionDigits(0);
                object2.setMaximumFractionDigits(1);
                object2 = object2.format(object3.getPoints());
            }
            object3 = new StringBuilder();
            object3.append(n + 1);
            object3.append(".");
            object.setItemText(object3.toString(), tournamentPlayer.getNameWithTitleAndRating(), (String)object2);
            object2 = tournamentPlayer.getCountry();
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

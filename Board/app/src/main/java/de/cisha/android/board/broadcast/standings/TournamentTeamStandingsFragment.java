// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.broadcast.standings;

import de.cisha.chess.model.Country;
import de.cisha.android.board.broadcast.model.TeamStanding;
import java.text.NumberFormat;
import de.cisha.android.ui.patterns.text.CustomTextViewTextStyle;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import de.cisha.android.board.broadcast.model.TournamentRoundInfo;
import de.cisha.android.board.broadcast.model.Tournament;
import android.support.v4.app.Fragment;
import de.cisha.android.board.broadcast.statistics.TeamStatisticsFragment;
import android.widget.ListAdapter;
import android.content.Context;
import de.cisha.android.board.broadcast.standings.view.StandingsRankingItem;
import android.view.View;
import java.util.LinkedList;
import de.cisha.android.board.broadcast.model.TournamentTeam;
import java.util.List;

public class TournamentTeamStandingsFragment extends AbstractStandingsFragment
{
    public static final String TAGNAME = "TournamentTeamStandingsRoundRobin";
    private List<TournamentTeam> _standings;
    private StandingsListAdapter _standingsListAdapter;
    
    public TournamentTeamStandingsFragment() {
        this._standings = new LinkedList<TournamentTeam>();
        this._standingsListAdapter = new StandingsListAdapter();
    }
    
    @Override
    protected boolean canSelectItems() {
        return true;
    }
    
    @Override
    protected View createHeaderView() {
        final StandingsRankingItem standingsRankingItem = new StandingsRankingItem((Context)this.getActivity());
        standingsRankingItem.setIsTableHeader();
        standingsRankingItem.setItemText(this.getString(2131689589), this.getString(2131689592), this.getString(2131689593), this.getString(2131689588));
        return (View)standingsRankingItem;
    }
    
    @Override
    protected ListAdapter createStandingsListAdapter() {
        return (ListAdapter)this._standingsListAdapter;
    }
    
    @Override
    protected void itemSelected(final int n) {
        if (n < this._standings.size()) {
            final TournamentTeam selectedTeam = this._standings.get(n);
            if (this.getTournamentHolder() != null && this.getContentFragmentSetter() != null) {
                this.getTournamentHolder().setSelectedTeam(selectedTeam);
                this.getContentFragmentSetter().setContentFragment(new TeamStatisticsFragment(), true, "TeamDetailFragment", "TournamentTeam");
            }
        }
    }
    
    @Override
    public void registeredForChangesOn(final Tournament tournament, final TournamentRoundInfo tournamentRoundInfo, final boolean b) {
        this._standings = tournament.getTeamStandings();
        if (this._standings == null) {
            this._standings = new LinkedList<TournamentTeam>();
        }
        this.runOnUiThreadBetweenStartAndDestroy(new Runnable() {
            @Override
            public void run() {
                TournamentTeamStandingsFragment.this._standingsListAdapter.notifyDataSetChanged();
            }
        });
    }
    
    class StandingsListAdapter extends BaseAdapter
    {
        public int getCount() {
            return TournamentTeamStandingsFragment.this._standings.size();
        }
        
        public Object getItem(final int n) {
            return TournamentTeamStandingsFragment.this._standings.get(n);
        }
        
        public long getItemId(final int n) {
            return 0L;
        }
        
        public View getView(final int n, final View view, final ViewGroup viewGroup) {
            StandingsRankingItem standingsRankingItem;
            if (view == null) {
                standingsRankingItem = new StandingsRankingItem((Context)TournamentTeamStandingsFragment.this.getActivity());
                standingsRankingItem.setNameTextStyle(CustomTextViewTextStyle.TABLE);
            }
            else {
                standingsRankingItem = (StandingsRankingItem)view;
            }
            final TournamentTeam tournamentTeam = TournamentTeamStandingsFragment.this._standings.get(n);
            final TeamStanding currentStandings = tournamentTeam.getCurrentStandings();
            String format = "";
            String format2;
            if (currentStandings != null) {
                final NumberFormat instance = NumberFormat.getInstance();
                instance.setMinimumFractionDigits(0);
                instance.setMaximumFractionDigits(1);
                format = instance.format(currentStandings.getTeamPoints());
                format2 = instance.format(currentStandings.getBoardPoints());
            }
            else {
                format2 = "";
            }
            final StringBuilder sb = new StringBuilder();
            sb.append(n + 1);
            sb.append(".");
            standingsRankingItem.setItemText(sb.toString(), tournamentTeam.getName(), format, format2);
            final Country country = tournamentTeam.getCountry();
            if (country != null) {
                standingsRankingItem.setFlagImage(country.getImageId());
                standingsRankingItem.setShowsFlag(true);
                return (View)standingsRankingItem;
            }
            standingsRankingItem.setShowsFlag(false);
            return (View)standingsRankingItem;
        }
        
        public boolean isEnabled(final int n) {
            return true;
        }
    }
}

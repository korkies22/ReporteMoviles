// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.broadcast.standings;

import com.neovisionaries.i18n.CountryCode;
import de.cisha.android.board.broadcast.model.PlayerStanding;
import java.text.NumberFormat;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import de.cisha.android.board.broadcast.model.TournamentRoundInfo;
import de.cisha.android.board.broadcast.model.Tournament;
import android.support.v4.app.Fragment;
import de.cisha.android.board.broadcast.statistics.SinglePlayerStatisticFragment;
import android.widget.ListAdapter;
import android.content.Context;
import de.cisha.android.board.broadcast.standings.view.StandingsRankingItem;
import android.view.View;
import java.util.LinkedList;
import de.cisha.android.board.broadcast.model.TournamentPlayer;
import java.util.List;

public class TournamentPlayerStandingsFragment extends AbstractStandingsFragment
{
    public static final String TAGNAME = "TournamentPlayerStandings";
    private List<TournamentPlayer> _standings;
    private StandingsListAdapter _standingsListAdapter;
    
    public TournamentPlayerStandingsFragment() {
        this._standings = new LinkedList<TournamentPlayer>();
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
        standingsRankingItem.hideLastRow();
        standingsRankingItem.setItemText(this.getString(2131689589), this.getString(2131689590), this.getString(2131689591));
        return (View)standingsRankingItem;
    }
    
    @Override
    protected ListAdapter createStandingsListAdapter() {
        return (ListAdapter)this._standingsListAdapter;
    }
    
    @Override
    protected void itemSelected(final int n) {
        if (n < this._standings.size()) {
            this.getTournamentHolder().setSelectedPlayer(this._standings.get(n));
            this.getContentFragmentSetter().setContentFragment(new SinglePlayerStatisticFragment(), true, "SinglePlayerStatisticFragment", "TournamentPlayer");
        }
    }
    
    @Override
    public void registeredForChangesOn(final Tournament tournament, final TournamentRoundInfo tournamentRoundInfo, final boolean b) {
        this._standings = tournament.getPlayerStandings();
        if (this._standings == null) {
            this._standings = new LinkedList<TournamentPlayer>();
        }
        this.runOnUiThreadBetweenStartAndDestroy(new Runnable() {
            @Override
            public void run() {
                TournamentPlayerStandingsFragment.this._standingsListAdapter.notifyDataSetChanged();
            }
        });
    }
    
    class StandingsListAdapter extends BaseAdapter
    {
        public int getCount() {
            return TournamentPlayerStandingsFragment.this._standings.size();
        }
        
        public Object getItem(final int n) {
            return TournamentPlayerStandingsFragment.this._standings.get(n);
        }
        
        public long getItemId(final int n) {
            return 0L;
        }
        
        public View getView(final int n, final View view, final ViewGroup viewGroup) {
            StandingsRankingItem standingsRankingItem;
            if (view == null) {
                standingsRankingItem = new StandingsRankingItem((Context)TournamentPlayerStandingsFragment.this.getActivity());
                standingsRankingItem.hideLastRow();
            }
            else {
                standingsRankingItem = (StandingsRankingItem)view;
            }
            final TournamentPlayer tournamentPlayer = TournamentPlayerStandingsFragment.this._standings.get(n);
            final PlayerStanding standing = tournamentPlayer.getStanding();
            String format = "";
            if (standing != null) {
                final NumberFormat instance = NumberFormat.getInstance();
                instance.setMinimumFractionDigits(0);
                instance.setMaximumFractionDigits(1);
                format = instance.format(standing.getPoints());
            }
            final StringBuilder sb = new StringBuilder();
            sb.append(n + 1);
            sb.append(".");
            standingsRankingItem.setItemText(sb.toString(), tournamentPlayer.getNameWithTitleAndRating(), format);
            final CountryCode country = tournamentPlayer.getCountry();
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

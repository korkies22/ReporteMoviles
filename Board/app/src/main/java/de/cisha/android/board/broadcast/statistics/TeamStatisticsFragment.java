// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.broadcast.statistics;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import de.cisha.android.board.broadcast.model.TournamentTeam;
import android.support.v4.view.ViewPager;
import de.cisha.android.board.broadcast.statistics.view.BroadcastTeamView;
import de.cisha.android.board.broadcast.model.TournamentHolder;
import de.cisha.android.board.broadcast.TournamentDetailsHolder;
import de.cisha.android.board.broadcast.TournamentSubFragmentWithOpenedGames;

public class TeamStatisticsFragment extends TournamentSubFragmentWithOpenedGames implements TournamentDetailsHolder, TournamentTeamSelectionListener
{
    public static String TAG = "TeamStatisticsFragment";
    BroadcastTeamView _teamView;
    private ViewPager _viewPager;
    
    private void selectTeam(final TournamentTeam tournamentTeam) {
        if (this.getTournamentHolder() != null) {
            if (tournamentTeam != null) {
                if (this.getTournamentHolder().hasTournament()) {
                    this._teamView.showTeam(tournamentTeam, this.getTournamentHolder().getTournament().getGamesPerMatch());
                }
            }
            else {
                this._teamView.reset();
            }
        }
    }
    
    @Override
    public ContentFragmentSetter getContentFragmentSetter() {
        return super.getContentFragmentSetter();
    }
    
    @Override
    public TournamentHolder getTournamentHolder() {
        return super.getTournamentHolder();
    }
    
    @Override
    public View onCreateView(final LayoutInflater layoutInflater, final ViewGroup viewGroup, final Bundle bundle) {
        final View inflate = layoutInflater.inflate(2131427568, viewGroup, false);
        this._teamView = (BroadcastTeamView)inflate.findViewById(2131297140);
        (this._viewPager = (ViewPager)inflate.findViewById(2131297141)).setAdapter(new MyPagerAdapter(this.getChildFragmentManager()));
        return inflate;
    }
    
    @Override
    public void onResume() {
        super.onResume();
        this.selectTeam(this.getTournamentHolder().getSelectedTeam());
    }
    
    @Override
    public void onStart() {
        super.onStart();
        this.getTournamentHolder().addTeamSelectionListener((TournamentHolder.TournamentTeamSelectionListener)this);
    }
    
    @Override
    public void onStop() {
        super.onStop();
        this.getTournamentHolder().removeTeamSelectionListener((TournamentHolder.TournamentTeamSelectionListener)this);
    }
    
    @Override
    public void selectedTeamChanged(final TournamentTeam tournamentTeam) {
        this.runOnUiThreadBetweenStartAndDestroy(new Runnable() {
            @Override
            public void run() {
                TeamStatisticsFragment.this.selectTeam(tournamentTeam);
            }
        });
    }
    
    private class MyPagerAdapter extends FragmentPagerAdapter
    {
        public MyPagerAdapter(final FragmentManager fragmentManager) {
            super(fragmentManager);
        }
        
        @Override
        public int getCount() {
            return 2;
        }
        
        @Override
        public Fragment getItem(final int n) {
            if (n == 0) {
                return new TeamPlayersListFragment();
            }
            return new SingleTeamPairingsFragment();
        }
        
        @Override
        public CharSequence getPageTitle(final int n) {
            if (n == 0) {
                return "Players";
            }
            return "Matches";
        }
    }
}

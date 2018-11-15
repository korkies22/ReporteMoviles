/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.ViewGroup
 */
package de.cisha.android.board.broadcast.statistics;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import de.cisha.android.board.broadcast.TournamentDetailsHolder;
import de.cisha.android.board.broadcast.TournamentSubFragmentWithOpenedGames;
import de.cisha.android.board.broadcast.model.Tournament;
import de.cisha.android.board.broadcast.model.TournamentHolder;
import de.cisha.android.board.broadcast.model.TournamentTeam;
import de.cisha.android.board.broadcast.statistics.SingleTeamPairingsFragment;
import de.cisha.android.board.broadcast.statistics.TeamPlayersListFragment;
import de.cisha.android.board.broadcast.statistics.view.BroadcastTeamView;

public class TeamStatisticsFragment
extends TournamentSubFragmentWithOpenedGames
implements TournamentDetailsHolder,
TournamentHolder.TournamentTeamSelectionListener {
    public static String TAG = "TeamStatisticsFragment";
    BroadcastTeamView _teamView;
    private ViewPager _viewPager;

    private void selectTeam(TournamentTeam tournamentTeam) {
        if (this.getTournamentHolder() != null) {
            if (tournamentTeam != null) {
                if (this.getTournamentHolder().hasTournament()) {
                    Tournament tournament = this.getTournamentHolder().getTournament();
                    this._teamView.showTeam(tournamentTeam, tournament.getGamesPerMatch());
                    return;
                }
            } else {
                this._teamView.reset();
            }
        }
    }

    @Override
    public TournamentDetailsHolder.ContentFragmentSetter getContentFragmentSetter() {
        return super.getContentFragmentSetter();
    }

    @Override
    public TournamentHolder getTournamentHolder() {
        return super.getTournamentHolder();
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        layoutInflater = layoutInflater.inflate(2131427568, viewGroup, false);
        this._teamView = (BroadcastTeamView)layoutInflater.findViewById(2131297140);
        this._viewPager = (ViewPager)layoutInflater.findViewById(2131297141);
        this._viewPager.setAdapter(new MyPagerAdapter(this.getChildFragmentManager()));
        return layoutInflater;
    }

    @Override
    public void onResume() {
        super.onResume();
        this.selectTeam(this.getTournamentHolder().getSelectedTeam());
    }

    @Override
    public void onStart() {
        super.onStart();
        this.getTournamentHolder().addTeamSelectionListener(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        this.getTournamentHolder().removeTeamSelectionListener(this);
    }

    @Override
    public void selectedTeamChanged(final TournamentTeam tournamentTeam) {
        this.runOnUiThreadBetweenStartAndDestroy(new Runnable(){

            @Override
            public void run() {
                TeamStatisticsFragment.this.selectTeam(tournamentTeam);
            }
        });
    }

    private class MyPagerAdapter
    extends FragmentPagerAdapter {
        public MyPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public Fragment getItem(int n) {
            if (n == 0) {
                return new TeamPlayersListFragment();
            }
            return new SingleTeamPairingsFragment();
        }

        @Override
        public CharSequence getPageTitle(int n) {
            if (n == 0) {
                return "Players";
            }
            return "Matches";
        }
    }

}

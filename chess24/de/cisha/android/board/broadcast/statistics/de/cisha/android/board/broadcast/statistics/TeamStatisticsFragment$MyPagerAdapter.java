/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.broadcast.statistics;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import de.cisha.android.board.broadcast.statistics.SingleTeamPairingsFragment;
import de.cisha.android.board.broadcast.statistics.TeamPlayersListFragment;
import de.cisha.android.board.broadcast.statistics.TeamStatisticsFragment;

private class TeamStatisticsFragment.MyPagerAdapter
extends FragmentPagerAdapter {
    public TeamStatisticsFragment.MyPagerAdapter(FragmentManager fragmentManager) {
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

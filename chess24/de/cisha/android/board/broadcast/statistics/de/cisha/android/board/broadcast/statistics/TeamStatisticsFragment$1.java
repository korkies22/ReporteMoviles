/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.broadcast.statistics;

import de.cisha.android.board.broadcast.model.TournamentTeam;

class TeamStatisticsFragment
implements Runnable {
    final /* synthetic */ TournamentTeam val$team;

    TeamStatisticsFragment(TournamentTeam tournamentTeam) {
        this.val$team = tournamentTeam;
    }

    @Override
    public void run() {
        TeamStatisticsFragment.this.selectTeam(this.val$team);
    }
}

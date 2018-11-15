/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.AsyncTask
 */
package de.cisha.android.board.broadcast.standings;

import android.os.AsyncTask;
import de.cisha.android.board.broadcast.model.Tournament;
import de.cisha.android.board.broadcast.model.TournamentRoundInfo;
import de.cisha.android.board.broadcast.standings.TournamentStandingsMultiKnockoutFragment;
import de.cisha.android.board.broadcast.standings.model.IStandingsCalculator;
import de.cisha.android.board.broadcast.standings.model.KnockoutMatch;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

private class TournamentStandingsMultiKnockoutFragment.StandingsCalculationTask
extends AsyncTask<Void, Void, Map<TournamentRoundInfo, List<KnockoutMatch>>> {
    private IStandingsCalculator _standingsCalculator;
    private Tournament _taskTournament;

    public TournamentStandingsMultiKnockoutFragment.StandingsCalculationTask(Tournament tournament, IStandingsCalculator iStandingsCalculator) {
        this._taskTournament = tournament;
        this._standingsCalculator = iStandingsCalculator;
    }

    protected /* varargs */ Map<TournamentRoundInfo, List<KnockoutMatch>> doInBackground(Void ... object) {
        object = new HashMap();
        for (TournamentRoundInfo tournamentRoundInfo : this._taskTournament.getMainRounds()) {
            object.put(tournamentRoundInfo, this._standingsCalculator.getMatchesForMainRound(tournamentRoundInfo, this._taskTournament));
        }
        return object;
    }
}

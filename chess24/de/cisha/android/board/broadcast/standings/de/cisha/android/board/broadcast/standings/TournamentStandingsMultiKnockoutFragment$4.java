/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.broadcast.standings;

import de.cisha.android.board.broadcast.model.Tournament;
import de.cisha.android.board.broadcast.model.TournamentRoundInfo;
import de.cisha.android.board.broadcast.standings.TournamentStandingsMultiKnockoutFragment;
import de.cisha.android.board.broadcast.standings.model.IStandingsCalculator;
import de.cisha.android.board.broadcast.standings.model.KnockoutMatch;
import java.util.List;
import java.util.Map;

class TournamentStandingsMultiKnockoutFragment
extends TournamentStandingsMultiKnockoutFragment.StandingsCalculationTask {
    TournamentStandingsMultiKnockoutFragment(Tournament tournament, IStandingsCalculator iStandingsCalculator) {
        super(TournamentStandingsMultiKnockoutFragment.this, tournament, iStandingsCalculator);
    }

    protected void onPostExecute(Map<TournamentRoundInfo, List<KnockoutMatch>> map) {
        TournamentStandingsMultiKnockoutFragment.this._matches = map;
        TournamentStandingsMultiKnockoutFragment.this.updateViewWithStandingsInfo();
    }
}

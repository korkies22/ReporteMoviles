/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.broadcast.standings.model;

import de.cisha.android.board.broadcast.model.Tournament;
import de.cisha.android.board.broadcast.model.TournamentRoundInfo;
import de.cisha.android.board.broadcast.standings.model.KnockoutMatch;
import java.util.List;

public interface IStandingsCalculator {
    public List<KnockoutMatch> getMatchesForMainRound(TournamentRoundInfo var1, Tournament var2);
}

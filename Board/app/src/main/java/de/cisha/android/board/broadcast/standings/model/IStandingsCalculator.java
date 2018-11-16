// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.broadcast.standings.model;

import java.util.List;
import de.cisha.android.board.broadcast.model.Tournament;
import de.cisha.android.board.broadcast.model.TournamentRoundInfo;

public interface IStandingsCalculator
{
    List<KnockoutMatch> getMatchesForMainRound(final TournamentRoundInfo p0, final Tournament p1);
}

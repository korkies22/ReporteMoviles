/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.broadcast.standings.model;

import de.cisha.android.board.broadcast.standings.model.KnockoutMatch;
import java.util.Comparator;

class MultiGameKnockOutStandingsCalculator
implements Comparator<KnockoutMatch> {
    MultiGameKnockOutStandingsCalculator() {
    }

    @Override
    public int compare(KnockoutMatch knockoutMatch, KnockoutMatch knockoutMatch2) {
        return knockoutMatch.getMatchNumber() - knockoutMatch2.getMatchNumber();
    }
}

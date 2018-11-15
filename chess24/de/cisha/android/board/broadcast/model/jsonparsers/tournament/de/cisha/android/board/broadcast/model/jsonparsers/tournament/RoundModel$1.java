/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.broadcast.model.jsonparsers.tournament;

import de.cisha.android.board.broadcast.model.jsonparsers.tournament.MatchModel;
import java.util.Comparator;

class RoundModel
implements Comparator<MatchModel> {
    RoundModel() {
    }

    @Override
    public int compare(MatchModel matchModel, MatchModel matchModel2) {
        if (matchModel != null && matchModel2 != null) {
            return matchModel.getMatchNumber() - matchModel2.getMatchNumber();
        }
        return 0;
    }
}

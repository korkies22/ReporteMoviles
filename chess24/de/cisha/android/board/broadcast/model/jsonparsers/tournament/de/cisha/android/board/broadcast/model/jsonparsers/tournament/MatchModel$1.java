/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.broadcast.model.jsonparsers.tournament;

import de.cisha.android.board.broadcast.model.jsonparsers.tournament.GameModel;
import java.util.Comparator;

class MatchModel
implements Comparator<GameModel> {
    MatchModel() {
    }

    @Override
    public int compare(GameModel gameModel, GameModel gameModel2) {
        if (gameModel != null && gameModel2 != null) {
            return gameModel.getGameNumber() - gameModel2.getGameNumber();
        }
        return 0;
    }
}

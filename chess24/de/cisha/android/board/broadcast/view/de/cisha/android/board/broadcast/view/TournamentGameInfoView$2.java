/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.broadcast.view;

import de.cisha.chess.model.GameResult;

static class TournamentGameInfoView {
    static final /* synthetic */ int[] $SwitchMap$de$cisha$chess$model$GameResult;

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    static {
        $SwitchMap$de$cisha$chess$model$GameResult = new int[GameResult.values().length];
        try {
            TournamentGameInfoView.$SwitchMap$de$cisha$chess$model$GameResult[GameResult.WHITE_WINS.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            TournamentGameInfoView.$SwitchMap$de$cisha$chess$model$GameResult[GameResult.BLACK_WINS.ordinal()] = 2;
            return;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            return;
        }
    }
}

/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.database;

import de.cisha.chess.model.GameType;

static class AnalyzesListFragment {
    static final /* synthetic */ int[] $SwitchMap$de$cisha$chess$model$GameType;

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    static {
        $SwitchMap$de$cisha$chess$model$GameType = new int[GameType.values().length];
        try {
            AnalyzesListFragment.$SwitchMap$de$cisha$chess$model$GameType[GameType.GAME_VS_ENGINE.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            AnalyzesListFragment.$SwitchMap$de$cisha$chess$model$GameType[GameType.ONLINEPLAYZONE.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            AnalyzesListFragment.$SwitchMap$de$cisha$chess$model$GameType[GameType.PGN_GAME.ordinal()] = 3;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            AnalyzesListFragment.$SwitchMap$de$cisha$chess$model$GameType[GameType.TACTICS.ordinal()] = 4;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            AnalyzesListFragment.$SwitchMap$de$cisha$chess$model$GameType[GameType.BROADCAST.ordinal()] = 5;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            AnalyzesListFragment.$SwitchMap$de$cisha$chess$model$GameType[GameType.ANALYZED_GAME.ordinal()] = 6;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            AnalyzesListFragment.$SwitchMap$de$cisha$chess$model$GameType[GameType.UNDEFINED.ordinal()] = 7;
            return;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            return;
        }
    }
}

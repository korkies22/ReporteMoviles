/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.playzone.model;

import de.cisha.chess.model.GameEndReason;

static class GameEndMessageHelper {
    static final /* synthetic */ int[] $SwitchMap$de$cisha$chess$model$GameEndReason;

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    static {
        $SwitchMap$de$cisha$chess$model$GameEndReason = new int[GameEndReason.values().length];
        try {
            GameEndMessageHelper.$SwitchMap$de$cisha$chess$model$GameEndReason[GameEndReason.DRAW_BY_FIFTY_MOVE_RULE.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            GameEndMessageHelper.$SwitchMap$de$cisha$chess$model$GameEndReason[GameEndReason.DRAW_BY_MUTUAL_AGREEMENT.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            GameEndMessageHelper.$SwitchMap$de$cisha$chess$model$GameEndReason[GameEndReason.DRAW_BY_INSUFFICENT_MATERIAL.ordinal()] = 3;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            GameEndMessageHelper.$SwitchMap$de$cisha$chess$model$GameEndReason[GameEndReason.DRAW_BY_STALEMATE.ordinal()] = 4;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            GameEndMessageHelper.$SwitchMap$de$cisha$chess$model$GameEndReason[GameEndReason.DRAW_BY_THREE_REPETITIONS.ordinal()] = 5;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            GameEndMessageHelper.$SwitchMap$de$cisha$chess$model$GameEndReason[GameEndReason.DRAW_BY_CLOCKFLAG_VS_INSUFFICIENT_MATERIAL.ordinal()] = 6;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            GameEndMessageHelper.$SwitchMap$de$cisha$chess$model$GameEndReason[GameEndReason.DRAW_BY_DISCONNECT_VS_INSUFFICENT_MATERIAL.ordinal()] = 7;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            GameEndMessageHelper.$SwitchMap$de$cisha$chess$model$GameEndReason[GameEndReason.CLOCK_FLAG.ordinal()] = 8;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            GameEndMessageHelper.$SwitchMap$de$cisha$chess$model$GameEndReason[GameEndReason.DISCONNECT_TIMEOUT.ordinal()] = 9;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            GameEndMessageHelper.$SwitchMap$de$cisha$chess$model$GameEndReason[GameEndReason.MATE.ordinal()] = 10;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            GameEndMessageHelper.$SwitchMap$de$cisha$chess$model$GameEndReason[GameEndReason.RESIGN.ordinal()] = 11;
            return;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            return;
        }
    }
}

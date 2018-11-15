/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.playzone.engine.model;

import de.cisha.chess.model.ClockSetting;

static class EngineConnection {
    static final /* synthetic */ int[] $SwitchMap$de$cisha$chess$model$ClockSetting$GameClockType;

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    static {
        $SwitchMap$de$cisha$chess$model$ClockSetting$GameClockType = new int[ClockSetting.GameClockType.values().length];
        try {
            EngineConnection.$SwitchMap$de$cisha$chess$model$ClockSetting$GameClockType[ClockSetting.GameClockType.BULLET.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            EngineConnection.$SwitchMap$de$cisha$chess$model$ClockSetting$GameClockType[ClockSetting.GameClockType.BLITZ.ordinal()] = 2;
            return;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            return;
        }
    }
}

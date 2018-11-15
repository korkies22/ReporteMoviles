/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.tactics;

import de.cisha.android.board.tactics.ITacticsExerciseService;

public static class ITacticsExerciseService.TacticsTrainerInfo {
    private boolean _hasLimit;
    private int _numberOfPuzzlesLeft;
    private long _timeLeft;

    public ITacticsExerciseService.TacticsTrainerInfo(boolean bl, int n, long l) {
        this._hasLimit = bl;
        this._numberOfPuzzlesLeft = n;
        this._timeLeft = l;
    }

    public int getNumberOfPuzzlesLeft() {
        return this._numberOfPuzzlesLeft;
    }

    public long getTimeLeft() {
        return this._timeLeft;
    }

    public boolean isHasLimit() {
        return this._hasLimit;
    }
}

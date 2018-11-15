/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.tactics.model;

import de.cisha.android.board.tactics.model.TacticsExercisesBundle;

public static enum TacticsExercisesBundle.NoMoreExercisesReason {
    DAILY_LIMIT_REACHED("daily"),
    GUEST_LIMIT_REACHED("guest"),
    NO_MORE_EXERCISES_LEFT("empty");
    
    private String _reason;

    private TacticsExercisesBundle.NoMoreExercisesReason(String string2) {
        this._reason = string2;
    }

    public static TacticsExercisesBundle.NoMoreExercisesReason reasonForString(String string) {
        for (TacticsExercisesBundle.NoMoreExercisesReason noMoreExercisesReason : TacticsExercisesBundle.NoMoreExercisesReason.values()) {
            if (!noMoreExercisesReason.getReason().equals(string)) continue;
            return noMoreExercisesReason;
        }
        return NO_MORE_EXERCISES_LEFT;
    }

    public String getReason() {
        return this._reason;
    }
}

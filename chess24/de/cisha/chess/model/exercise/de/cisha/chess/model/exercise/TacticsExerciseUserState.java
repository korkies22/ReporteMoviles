/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.chess.model.exercise;

public enum TacticsExerciseUserState {
    SOLVED_INCORRECT,
    PARTIALLY_SOLVED_CORRECT_GET_USERMOVE,
    PARTIALLY_SOLVED_CORRECT_DO_COMPUTERMOVE,
    SOLVED_CORRECT;
    

    private TacticsExerciseUserState() {
    }

    public boolean isCompleted() {
        if (this != SOLVED_INCORRECT && this != SOLVED_CORRECT) {
            return false;
        }
        return true;
    }

    public boolean isCorrect() {
        if (this == SOLVED_CORRECT) {
            return true;
        }
        return false;
    }
}

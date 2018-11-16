// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.chess.model.exercise;

public enum TacticsExerciseUserState
{
    PARTIALLY_SOLVED_CORRECT_DO_COMPUTERMOVE, 
    PARTIALLY_SOLVED_CORRECT_GET_USERMOVE, 
    SOLVED_CORRECT, 
    SOLVED_INCORRECT;
    
    public boolean isCompleted() {
        return this == TacticsExerciseUserState.SOLVED_INCORRECT || this == TacticsExerciseUserState.SOLVED_CORRECT;
    }
    
    public boolean isCorrect() {
        return this == TacticsExerciseUserState.SOLVED_CORRECT;
    }
}

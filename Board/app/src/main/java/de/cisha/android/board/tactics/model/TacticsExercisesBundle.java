// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.tactics.model;

import java.util.LinkedList;
import de.cisha.chess.model.CishaUUID;
import de.cisha.chess.model.exercise.TacticsExercise;
import java.util.List;

public class TacticsExercisesBundle
{
    private List<TacticsExercise> _exercises;
    private NoMoreExercisesReason _reason;
    private CishaUUID _sessionId;
    
    public TacticsExercisesBundle(final NoMoreExercisesReason reason, final CishaUUID sessionId) {
        this._exercises = new LinkedList<TacticsExercise>();
        this._sessionId = sessionId;
        this._reason = reason;
    }
    
    public TacticsExercisesBundle(final List<TacticsExercise> exercises, final CishaUUID sessionId) {
        this._exercises = exercises;
        this._sessionId = sessionId;
        if (this._exercises.size() == 0) {
            this._reason = NoMoreExercisesReason.NO_MORE_EXERCISES_LEFT;
        }
    }
    
    public List<TacticsExercise> getExercises() {
        return this._exercises;
    }
    
    public NoMoreExercisesReason getNoExercisesReason() {
        return this._reason;
    }
    
    public CishaUUID getSessionId() {
        return this._sessionId;
    }
    
    public boolean hasExercises() {
        return this._exercises != null && this._exercises.size() > 0;
    }
    
    public enum NoMoreExercisesReason
    {
        DAILY_LIMIT_REACHED("daily"), 
        GUEST_LIMIT_REACHED("guest"), 
        NO_MORE_EXERCISES_LEFT("empty");
        
        private String _reason;
        
        private NoMoreExercisesReason(final String reason) {
            this._reason = reason;
        }
        
        public static NoMoreExercisesReason reasonForString(final String s) {
            final NoMoreExercisesReason[] values = values();
            for (int i = 0; i < values.length; ++i) {
                final NoMoreExercisesReason noMoreExercisesReason = values[i];
                if (noMoreExercisesReason.getReason().equals(s)) {
                    return noMoreExercisesReason;
                }
            }
            return NoMoreExercisesReason.NO_MORE_EXERCISES_LEFT;
        }
        
        public String getReason() {
            return this._reason;
        }
    }
}

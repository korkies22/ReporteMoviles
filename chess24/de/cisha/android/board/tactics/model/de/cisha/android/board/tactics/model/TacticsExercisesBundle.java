/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.tactics.model;

import de.cisha.chess.model.CishaUUID;
import de.cisha.chess.model.exercise.TacticsExercise;
import java.util.LinkedList;
import java.util.List;

public class TacticsExercisesBundle {
    private List<TacticsExercise> _exercises;
    private NoMoreExercisesReason _reason;
    private CishaUUID _sessionId;

    public TacticsExercisesBundle(NoMoreExercisesReason noMoreExercisesReason, CishaUUID cishaUUID) {
        this._exercises = new LinkedList<TacticsExercise>();
        this._sessionId = cishaUUID;
        this._reason = noMoreExercisesReason;
    }

    public TacticsExercisesBundle(List<TacticsExercise> list, CishaUUID cishaUUID) {
        this._exercises = list;
        this._sessionId = cishaUUID;
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
        if (this._exercises != null && this._exercises.size() > 0) {
            return true;
        }
        return false;
    }

    public static enum NoMoreExercisesReason {
        DAILY_LIMIT_REACHED("daily"),
        GUEST_LIMIT_REACHED("guest"),
        NO_MORE_EXERCISES_LEFT("empty");
        
        private String _reason;

        private NoMoreExercisesReason(String string2) {
            this._reason = string2;
        }

        public static NoMoreExercisesReason reasonForString(String string) {
            for (NoMoreExercisesReason noMoreExercisesReason : NoMoreExercisesReason.values()) {
                if (!noMoreExercisesReason.getReason().equals(string)) continue;
                return noMoreExercisesReason;
            }
            return NO_MORE_EXERCISES_LEFT;
        }

        public String getReason() {
            return this._reason;
        }
    }

}

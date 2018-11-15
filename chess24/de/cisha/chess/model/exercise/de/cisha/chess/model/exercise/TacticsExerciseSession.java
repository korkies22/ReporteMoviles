/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.chess.model.exercise;

import de.cisha.chess.model.CishaUUID;
import de.cisha.chess.model.Rating;
import de.cisha.chess.model.exercise.TacticsExercise;
import de.cisha.chess.model.exercise.TacticsExerciseUserSolution;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class TacticsExerciseSession {
    List<TacticsExerciseUserSolution> _exercises = new ArrayList<TacticsExerciseUserSolution>(20);
    private Rating _performance;
    private CishaUUID _sessionID = new CishaUUID("", false);
    private Rating _userEnd;
    private Rating _userStart;

    public void addExercise(TacticsExerciseUserSolution tacticsExerciseUserSolution) {
        this._exercises.add(tacticsExerciseUserSolution);
    }

    public int getAverageTimeMillis() {
        Iterator<TacticsExerciseUserSolution> iterator = this._exercises.iterator();
        int n = 0;
        while (iterator.hasNext()) {
            n += iterator.next().getTimeUsed();
        }
        if (this._exercises.size() == 0) {
            return 0;
        }
        return n / this._exercises.size();
    }

    public List<TacticsExerciseUserSolution> getExercises() {
        return new ArrayList<TacticsExerciseUserSolution>(this._exercises);
    }

    public int getNumberOfCorrectExercises() {
        Iterator<TacticsExerciseUserSolution> iterator = this._exercises.iterator();
        int n = 0;
        while (iterator.hasNext()) {
            if (!iterator.next().isCorrect()) continue;
            ++n;
        }
        return n;
    }

    public int getNumberOfExercises() {
        return this._exercises.size();
    }

    public int getNumberOfWrongExercises() {
        return this.getNumberOfExercises() - this.getNumberOfCorrectExercises();
    }

    public Rating getPerformance() {
        if (this._performance == null) {
            int n;
            int n2;
            Iterator<TacticsExerciseUserSolution> iterator = this._exercises.iterator();
            int n3 = n = (n2 = 0);
            int n4 = n;
            while (iterator.hasNext()) {
                Object object = iterator.next();
                ++n2;
                n = n4;
                if (object.isCorrect()) {
                    n = n4 + 1;
                }
                n4 = (object = object.getExercise().getExerciseRating()) == null ? 0 : object.getRating();
                n3 += n4;
                n4 = n;
            }
            float f = n4;
            float f2 = n2;
            return new Rating((int)((float)n3 / f2 - 500.0f + 2.0f * (f /= f2) * 1000.0f));
        }
        return this._performance;
    }

    public CishaUUID getSessionId() {
        return this._sessionID;
    }

    public Rating getUserEndRating() {
        return this._userEnd;
    }

    public Rating getUserStartRating() {
        return this._userStart;
    }

    public void setRatingPerformance(Rating rating) {
        this._performance = rating;
    }

    public void setSessionId(CishaUUID cishaUUID) {
        if (this._sessionID == null || !this._sessionID.isVerified()) {
            this._sessionID = cishaUUID;
        }
    }

    public void setUserEndRating(Rating rating) {
        this._userEnd = rating;
    }

    public void setUserStartRating(Rating rating) {
        this._userStart = rating;
    }
}

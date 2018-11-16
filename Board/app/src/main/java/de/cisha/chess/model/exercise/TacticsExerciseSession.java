// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.chess.model.exercise;

import java.util.Collection;
import java.util.Iterator;
import java.util.ArrayList;
import de.cisha.chess.model.CishaUUID;
import de.cisha.chess.model.Rating;
import java.util.List;

public class TacticsExerciseSession
{
    List<TacticsExerciseUserSolution> _exercises;
    private Rating _performance;
    private CishaUUID _sessionID;
    private Rating _userEnd;
    private Rating _userStart;
    
    public TacticsExerciseSession() {
        this._exercises = new ArrayList<TacticsExerciseUserSolution>(20);
        this._sessionID = new CishaUUID("", false);
    }
    
    public void addExercise(final TacticsExerciseUserSolution tacticsExerciseUserSolution) {
        this._exercises.add(tacticsExerciseUserSolution);
    }
    
    public int getAverageTimeMillis() {
        final Iterator<TacticsExerciseUserSolution> iterator = this._exercises.iterator();
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
        final Iterator<TacticsExerciseUserSolution> iterator = this._exercises.iterator();
        int n = 0;
        while (iterator.hasNext()) {
            if (iterator.next().isCorrect()) {
                ++n;
            }
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
            final Iterator<TacticsExerciseUserSolution> iterator = this._exercises.iterator();
            int n = 0;
            int n3;
            int n2 = n3 = n;
            while (iterator.hasNext()) {
                final TacticsExerciseUserSolution tacticsExerciseUserSolution = iterator.next();
                ++n;
                int n4 = n3;
                if (tacticsExerciseUserSolution.isCorrect()) {
                    n4 = n3 + 1;
                }
                final Rating exerciseRating = tacticsExerciseUserSolution.getExercise().getExerciseRating();
                int rating;
                if (exerciseRating == null) {
                    rating = 0;
                }
                else {
                    rating = exerciseRating.getRating();
                }
                n2 += rating;
                n3 = n4;
            }
            final float n5 = n3;
            final float n6 = n;
            return new Rating((int)(n2 / n6 - 500.0f + 2.0f * (n5 / n6) * 1000.0f));
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
    
    public void setRatingPerformance(final Rating performance) {
        this._performance = performance;
    }
    
    public void setSessionId(final CishaUUID sessionID) {
        if (this._sessionID == null || !this._sessionID.isVerified()) {
            this._sessionID = sessionID;
        }
    }
    
    public void setUserEndRating(final Rating userEnd) {
        this._userEnd = userEnd;
    }
    
    public void setUserStartRating(final Rating userStart) {
        this._userStart = userStart;
    }
}

// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.chess.model.exercise;

import de.cisha.chess.model.Rating;

public class TacticsExerciseSolutionInfo
{
    private int _exerciseId;
    private Rating _userRatingMax;
    private Rating _userRatingMin;
    private Rating _userRatingNow;
    
    public TacticsExerciseSolutionInfo(final int exerciseId, final Rating userRatingMin, final Rating userRatingMax, final Rating userRatingNow) {
        this._exerciseId = exerciseId;
        this._userRatingMin = userRatingMin;
        this._userRatingMax = userRatingMax;
        this._userRatingNow = userRatingNow;
    }
    
    public int getExerciseId() {
        return this._exerciseId;
    }
    
    public Rating getUserRatingMax() {
        return this._userRatingMax;
    }
    
    public Rating getUserRatingMin() {
        return this._userRatingMin;
    }
    
    public Rating getUserRatingNow() {
        return this._userRatingNow;
    }
}

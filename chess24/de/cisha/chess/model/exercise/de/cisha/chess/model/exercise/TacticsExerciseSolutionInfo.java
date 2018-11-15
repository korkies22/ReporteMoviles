/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.chess.model.exercise;

import de.cisha.chess.model.Rating;

public class TacticsExerciseSolutionInfo {
    private int _exerciseId;
    private Rating _userRatingMax;
    private Rating _userRatingMin;
    private Rating _userRatingNow;

    public TacticsExerciseSolutionInfo(int n, Rating rating, Rating rating2, Rating rating3) {
        this._exerciseId = n;
        this._userRatingMin = rating;
        this._userRatingMax = rating2;
        this._userRatingNow = rating3;
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

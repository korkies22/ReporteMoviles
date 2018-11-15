/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.tactics.model;

import de.cisha.android.board.tactics.model.TacticsExerciseInfo;
import de.cisha.chess.model.Rating;
import de.cisha.chess.model.exercise.TacticsExercise;
import java.util.ArrayList;
import java.util.List;

public class TacticsExerciseSessionInfo {
    private int _averageTimePerPuzzle;
    private int _correctPuzzleCount;
    private int _puzzleCount;
    private List<TacticsExerciseInfo> _puzzleInfos = new ArrayList<TacticsExerciseInfo>();
    private Rating _userCurrentRating;
    private Rating _userPerformance;
    private Rating _userStartRating;

    public void addPuzzleInfo(TacticsExerciseInfo tacticsExerciseInfo) {
        this._puzzleInfos.add(tacticsExerciseInfo);
    }

    public int getAverageTimePerPuzzle() {
        return this._averageTimePerPuzzle;
    }

    public int getCorrectPuzzleCount() {
        return this._correctPuzzleCount;
    }

    public TacticsExerciseInfo getExerciseInfoForExercise(TacticsExercise tacticsExercise) {
        if (tacticsExercise != null) {
            for (TacticsExerciseInfo tacticsExerciseInfo : this._puzzleInfos) {
                if (tacticsExercise.getExerciseId() != tacticsExerciseInfo.getPuzzleId()) continue;
                return tacticsExerciseInfo;
            }
        }
        return null;
    }

    public int getPuzzleCount() {
        return this._puzzleCount;
    }

    public Rating getUserCurrentRating() {
        return this._userCurrentRating;
    }

    public Rating getUserPerformance() {
        return this._userPerformance;
    }

    public Rating getUserStartRating() {
        return this._userStartRating;
    }

    public void setAverageTimePerPuzzle(int n) {
        this._averageTimePerPuzzle = n;
    }

    public void setCorrectPuzzleCount(int n) {
        this._correctPuzzleCount = n;
    }

    public void setPuzzleCount(int n) {
        this._puzzleCount = n;
    }

    public void setUserCurrentRating(Rating rating) {
        this._userCurrentRating = rating;
    }

    public void setUserPerformance(Rating rating) {
        this._userPerformance = rating;
    }

    public void setUserStartRating(Rating rating) {
        this._userStartRating = rating;
    }
}

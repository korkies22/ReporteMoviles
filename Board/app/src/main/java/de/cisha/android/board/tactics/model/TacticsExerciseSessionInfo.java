// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.tactics.model;

import java.util.Iterator;
import de.cisha.chess.model.exercise.TacticsExercise;
import java.util.ArrayList;
import de.cisha.chess.model.Rating;
import java.util.List;

public class TacticsExerciseSessionInfo
{
    private int _averageTimePerPuzzle;
    private int _correctPuzzleCount;
    private int _puzzleCount;
    private List<TacticsExerciseInfo> _puzzleInfos;
    private Rating _userCurrentRating;
    private Rating _userPerformance;
    private Rating _userStartRating;
    
    public TacticsExerciseSessionInfo() {
        this._puzzleInfos = new ArrayList<TacticsExerciseInfo>();
    }
    
    public void addPuzzleInfo(final TacticsExerciseInfo tacticsExerciseInfo) {
        this._puzzleInfos.add(tacticsExerciseInfo);
    }
    
    public int getAverageTimePerPuzzle() {
        return this._averageTimePerPuzzle;
    }
    
    public int getCorrectPuzzleCount() {
        return this._correctPuzzleCount;
    }
    
    public TacticsExerciseInfo getExerciseInfoForExercise(final TacticsExercise tacticsExercise) {
        if (tacticsExercise != null) {
            for (final TacticsExerciseInfo tacticsExerciseInfo : this._puzzleInfos) {
                if (tacticsExercise.getExerciseId() == tacticsExerciseInfo.getPuzzleId()) {
                    return tacticsExerciseInfo;
                }
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
    
    public void setAverageTimePerPuzzle(final int averageTimePerPuzzle) {
        this._averageTimePerPuzzle = averageTimePerPuzzle;
    }
    
    public void setCorrectPuzzleCount(final int correctPuzzleCount) {
        this._correctPuzzleCount = correctPuzzleCount;
    }
    
    public void setPuzzleCount(final int puzzleCount) {
        this._puzzleCount = puzzleCount;
    }
    
    public void setUserCurrentRating(final Rating userCurrentRating) {
        this._userCurrentRating = userCurrentRating;
    }
    
    public void setUserPerformance(final Rating userPerformance) {
        this._userPerformance = userPerformance;
    }
    
    public void setUserStartRating(final Rating userStartRating) {
        this._userStartRating = userStartRating;
    }
}

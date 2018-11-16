// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.profile.model;

import de.cisha.chess.model.Rating;
import de.cisha.chess.model.FEN;

public class TacticStatisticData
{
    private int _attempts;
    private int _attemptsCorrect;
    private FEN _fen;
    private Rating _rating;
    private float _secondsPerExercise;
    
    public TacticStatisticData(final Rating rating, final int attempts, final int attemptsCorrect, final float secondsPerExercise, final FEN fen) {
        this._rating = rating;
        this._attempts = attempts;
        this._attemptsCorrect = attemptsCorrect;
        this._secondsPerExercise = secondsPerExercise;
        this._fen = fen;
    }
    
    public int getAttemptsClassic() {
        return this._attempts;
    }
    
    public int getAttemptsCorrectClassic() {
        return this._attemptsCorrect;
    }
    
    public FEN getFenClassic() {
        return this._fen;
    }
    
    public Rating getRatingClassic() {
        return this._rating;
    }
    
    public float getRatioClassic() {
        if (this._attempts == 0) {
            return 0.0f;
        }
        return this._attemptsCorrect / this._attempts;
    }
    
    public float getSecondsPerExerciseClassic() {
        return this._secondsPerExercise;
    }
}

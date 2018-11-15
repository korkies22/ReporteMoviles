/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.profile.model;

import de.cisha.chess.model.FEN;
import de.cisha.chess.model.Rating;

public class TacticStatisticData {
    private int _attempts;
    private int _attemptsCorrect;
    private FEN _fen;
    private Rating _rating;
    private float _secondsPerExercise;

    public TacticStatisticData(Rating rating, int n, int n2, float f, FEN fEN) {
        this._rating = rating;
        this._attempts = n;
        this._attemptsCorrect = n2;
        this._secondsPerExercise = f;
        this._fen = fEN;
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
        return (float)this._attemptsCorrect / (float)this._attempts;
    }

    public float getSecondsPerExerciseClassic() {
        return this._secondsPerExercise;
    }
}

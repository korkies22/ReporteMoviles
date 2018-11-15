/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.chess.model;

public enum GameResult {
    NO_RESULT("no result", "*", 0.0f, 0.0f, "*", "*", false),
    DRAW("draw", "\u00bd-\u00bd", 0.5f, 0.5f, "\u00bd", "\u00bd", false),
    WHITE_WINS("white wins", "1-0", 1.0f, 0.0f, "1", "0", false),
    BLACK_WINS("black wins", "0-1", 0.0f, 1.0f, "0", "1", false),
    WHITE_WINS_BY_FORFEIT("black forfeits", "+ -", 1.0f, 0.0f, "+", "-", true),
    BLACK_WINS_BY_FORFEIT("white forfeits", "- +", 0.0f, 1.0f, "-", "+", true),
    BOTH_LOSE("both forfeit", "- -", 0.0f, 0.0f, "-", "-", true);
    
    private boolean _byForfeit;
    private String _description;
    private String _resultBlack;
    private String _resultWhite;
    private float _scoreBlack;
    private float _scoreWhite;
    private String _shortDescription;

    private GameResult(String string2, String string3, float f, float f2, String string4, String string5, boolean bl) {
        this._description = string2;
        this._shortDescription = string3;
        this._scoreWhite = f;
        this._scoreBlack = f2;
        this._resultBlack = string5;
        this._resultWhite = string4;
        this._byForfeit = bl;
    }

    public static GameResult fromString(String string) {
        if (!("\u00bd-\u00bd".equals(string) || "1/2-1/2".equals(string) || "1/2".equals(string) || "0.5-0.5".equals(string))) {
            if ("1-0".equals(string)) {
                return WHITE_WINS;
            }
            if ("0-1".equals(string)) {
                return BLACK_WINS;
            }
            if (!("+--".equals(string) || "+ -".equals(string) || "+-".equals(string))) {
                if (!("--+".equals(string) || "- +".equals(string) || "-+".equals(string))) {
                    if (!("---".equals(string) || "- -".equals(string) || "--".equals(string) || "0-0".equals(string))) {
                        return NO_RESULT;
                    }
                    return BOTH_LOSE;
                }
                return BLACK_WINS_BY_FORFEIT;
            }
            return WHITE_WINS_BY_FORFEIT;
        }
        return DRAW;
    }

    public static GameResult fromString(String object, boolean bl) {
        GameResult gameResult = GameResult.fromString(object);
        if (gameResult == WHITE_WINS) {
            return WHITE_WINS_BY_FORFEIT;
        }
        if (gameResult == BLACK_WINS) {
            return BLACK_WINS_BY_FORFEIT;
        }
        object = gameResult;
        if (gameResult == DRAW) {
            object = BOTH_LOSE;
        }
        return object;
    }

    public static GameResult getResultForScore(double d, double d2, boolean bl) {
        GameResult gameResult = NO_RESULT;
        if (d == 1.0) {
            if (bl) {
                return WHITE_WINS_BY_FORFEIT;
            }
            return WHITE_WINS;
        }
        if (d2 == 1.0) {
            if (bl) {
                return BLACK_WINS_BY_FORFEIT;
            }
            return BLACK_WINS;
        }
        if (d == 0.5 && d2 == 0.5) {
            return DRAW;
        }
        GameResult gameResult2 = gameResult;
        if (d == 0.0) {
            gameResult2 = gameResult;
            if (d2 == 0.0) {
                gameResult2 = BOTH_LOSE;
            }
        }
        return gameResult2;
    }

    public String getDescription() {
        return this._description;
    }

    public String getResultBlack() {
        return this._resultBlack;
    }

    public String getResultWhite() {
        return this._resultWhite;
    }

    public float getScoreBlack() {
        return this._scoreBlack;
    }

    public float getScoreWhite() {
        return this._scoreWhite;
    }

    public String getShortDescription() {
        return this._shortDescription;
    }

    public String getShortDescription(boolean bl) {
        String string;
        String string2 = string = this.getShortDescription();
        if (!bl) {
            string2 = new StringBuilder(string).reverse().toString();
        }
        return string2;
    }

    public boolean hasBlackLost() {
        if (this._scoreBlack < 0.25f) {
            return true;
        }
        return false;
    }

    public boolean hasBlackWon() {
        if (this._scoreBlack > 0.75f) {
            return true;
        }
        return false;
    }

    public boolean hasWhiteLost() {
        if (this._scoreWhite < 0.25f) {
            return true;
        }
        return false;
    }

    public boolean hasWhiteWon() {
        if (this._scoreWhite > 0.75f) {
            return true;
        }
        return false;
    }

    public boolean isForfeiture() {
        return this._byForfeit;
    }
}

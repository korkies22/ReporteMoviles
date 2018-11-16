// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.chess.model;

public enum GameResult
{
    BLACK_WINS("black wins", "0-1", 0.0f, 1.0f, "0", "1", false), 
    BLACK_WINS_BY_FORFEIT("white forfeits", "- +", 0.0f, 1.0f, "-", "+", true), 
    BOTH_LOSE("both forfeit", "- -", 0.0f, 0.0f, "-", "-", true), 
    DRAW("draw", "½-½", 0.5f, 0.5f, "½", "½", false), 
    NO_RESULT("no result", "*", 0.0f, 0.0f, "*", "*", false), 
    WHITE_WINS("white wins", "1-0", 1.0f, 0.0f, "1", "0", false), 
    WHITE_WINS_BY_FORFEIT("black forfeits", "+ -", 1.0f, 0.0f, "+", "-", true);
    
    private boolean _byForfeit;
    private String _description;
    private String _resultBlack;
    private String _resultWhite;
    private float _scoreBlack;
    private float _scoreWhite;
    private String _shortDescription;
    
    private GameResult(final String description, final String shortDescription, final float scoreWhite, final float scoreBlack, final String resultWhite, final String resultBlack, final boolean byForfeit) {
        this._description = description;
        this._shortDescription = shortDescription;
        this._scoreWhite = scoreWhite;
        this._scoreBlack = scoreBlack;
        this._resultBlack = resultBlack;
        this._resultWhite = resultWhite;
        this._byForfeit = byForfeit;
    }
    
    public static GameResult fromString(final String s) {
        if ("½-½".equals(s) || "1/2-1/2".equals(s) || "1/2".equals(s) || "0.5-0.5".equals(s)) {
            return GameResult.DRAW;
        }
        if ("1-0".equals(s)) {
            return GameResult.WHITE_WINS;
        }
        if ("0-1".equals(s)) {
            return GameResult.BLACK_WINS;
        }
        if ("+--".equals(s) || "+ -".equals(s) || "+-".equals(s)) {
            return GameResult.WHITE_WINS_BY_FORFEIT;
        }
        if ("--+".equals(s) || "- +".equals(s) || "-+".equals(s)) {
            return GameResult.BLACK_WINS_BY_FORFEIT;
        }
        if (!"---".equals(s) && !"- -".equals(s) && !"--".equals(s) && !"0-0".equals(s)) {
            return GameResult.NO_RESULT;
        }
        return GameResult.BOTH_LOSE;
    }
    
    public static GameResult fromString(final String s, final boolean b) {
        final GameResult fromString = fromString(s);
        if (fromString == GameResult.WHITE_WINS) {
            return GameResult.WHITE_WINS_BY_FORFEIT;
        }
        if (fromString == GameResult.BLACK_WINS) {
            return GameResult.BLACK_WINS_BY_FORFEIT;
        }
        GameResult both_LOSE;
        if ((both_LOSE = fromString) == GameResult.DRAW) {
            both_LOSE = GameResult.BOTH_LOSE;
        }
        return both_LOSE;
    }
    
    public static GameResult getResultForScore(final double n, final double n2, final boolean b) {
        final GameResult no_RESULT = GameResult.NO_RESULT;
        if (n == 1.0) {
            if (b) {
                return GameResult.WHITE_WINS_BY_FORFEIT;
            }
            return GameResult.WHITE_WINS;
        }
        else if (n2 == 1.0) {
            if (b) {
                return GameResult.BLACK_WINS_BY_FORFEIT;
            }
            return GameResult.BLACK_WINS;
        }
        else {
            if (n == 0.5 && n2 == 0.5) {
                return GameResult.DRAW;
            }
            GameResult both_LOSE = no_RESULT;
            if (n == 0.0) {
                both_LOSE = no_RESULT;
                if (n2 == 0.0) {
                    both_LOSE = GameResult.BOTH_LOSE;
                }
            }
            return both_LOSE;
        }
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
    
    public String getShortDescription(final boolean b) {
        String s = this.getShortDescription();
        if (!b) {
            s = new StringBuilder(s).reverse().toString();
        }
        return s;
    }
    
    public boolean hasBlackLost() {
        return this._scoreBlack < 0.25f;
    }
    
    public boolean hasBlackWon() {
        return this._scoreBlack > 0.75f;
    }
    
    public boolean hasWhiteLost() {
        return this._scoreWhite < 0.25f;
    }
    
    public boolean hasWhiteWon() {
        return this._scoreWhite > 0.75f;
    }
    
    public boolean isForfeiture() {
        return this._byForfeit;
    }
}

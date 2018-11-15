/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.stockfish;

import de.cisha.chess.model.Rating;

public class StockfishEngineStrength {
    private int elo;

    public StockfishEngineStrength(int n) {
        this.elo = n;
    }

    public StockfishEngineStrength(Rating rating) {
        this.elo = rating.getRating();
    }

    private float getAdjustedElo() {
        float f;
        float f2 = f = (float)this.elo;
        if (f < 1600.0f) {
            f2 = f - (1600.0f - f);
        }
        return f2;
    }

    public float getBigBlunderEvalDiff() {
        return (2800.0f - (float)this.elo) / 180.0f;
    }

    public float getBigBlunderProbability() {
        return Math.max((2000.0f - this.getAdjustedElo()) / 20000.0f, 0.0f);
    }

    public int getDepth() {
        if (this.elo <= 1400) {
            return 1;
        }
        if (this.elo <= 1700) {
            return 2;
        }
        if (this.elo <= 1900) {
            return 3;
        }
        if (this.elo <= 2000) {
            return 4;
        }
        if (this.elo <= 2100) {
            return 5;
        }
        if (this.elo <= 2200) {
            return 6;
        }
        if (this.elo <= 2300) {
            return 7;
        }
        if (this.elo <= 2400) {
            return 8;
        }
        if (this.elo <= 2450) {
            return 9;
        }
        if (this.elo <= 2500) {
            return 10;
        }
        if (this.elo <= 2550) {
            return 11;
        }
        if (this.elo <= 2600) {
            return 12;
        }
        if (this.elo <= 2650) {
            return 14;
        }
        return 16;
    }

    public int getSkillLevel() {
        if (this.elo < 1100) {
            return 0;
        }
        if (this.elo < 1300) {
            return 10;
        }
        return 20;
    }

    public float getSmallBlunderEvalDiff() {
        return (3000.0f - (float)this.elo) / 1000.0f;
    }

    public float getSmallBlunderProbability() {
        return Math.max((5000.0f - 2.0f * this.getAdjustedElo()) / 5000.0f, 0.0f);
    }

    public int getVariants() {
        if (this.elo < 1000) {
            return 6;
        }
        if (this.elo < 1400) {
            return 5;
        }
        if (this.elo < 1700) {
            return 4;
        }
        if (this.elo < 2000) {
            return 3;
        }
        if (this.elo < 2500) {
            return 2;
        }
        return 1;
    }
}

/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.chess.engine;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class EngineEvaluation {
    private static final int INTERNAL_DEFINITE_MATE = 1500;
    private static final int INTERNAL_MATE_REPRESENTATION = 2000;
    private static NumberFormat nf = new DecimalFormat("##0.00");
    private float _evaluation;
    private String _stringRepresentation;

    public EngineEvaluation(float f) {
        this._evaluation = f;
        this._stringRepresentation = nf.format(this._evaluation);
    }

    public EngineEvaluation(int n, boolean bl) {
        if (bl) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("# ");
            stringBuilder.append(n);
            this._stringRepresentation = stringBuilder.toString();
            this._evaluation = 2000 - n;
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("# -");
        stringBuilder.append(n);
        this._stringRepresentation = stringBuilder.toString();
        this._evaluation = -2000 + n;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public EngineEvaluation(String string) {
        if (string == null) return;
        this._stringRepresentation = string;
        if (!string.matches("\\d+") && !string.matches("-\\d+")) {
            int n;
            block3 : {
                if (!string.startsWith("#")) return;
                try {
                    n = Integer.parseInt(string.substring(1));
                    break block3;
                }
                catch (NumberFormatException numberFormatException) {}
                return;
            }
            if (n == 0) return;
            n = n >= 0 ? 2000 - n : -2000 - n;
            float f = n;
            this._evaluation = f;
            return;
        }
        this._evaluation = (float)Integer.parseInt(string) / 100.0f;
    }

    public float getEvaluation() {
        return this._evaluation;
    }

    public String getEvaluationAsString() {
        return this._stringRepresentation;
    }

    public int getMovesToMate() {
        if (this._evaluation < -1500.0f) {
            return (int)(- this._evaluation + 2000.0f);
        }
        if (this._evaluation > 1500.0f) {
            return (int)(- this._evaluation + 2000.0f);
        }
        return 0;
    }

    public boolean isMate() {
        if (this._evaluation >= -1500.0f && this._evaluation <= 1500.0f) {
            return false;
        }
        return true;
    }
}

// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.chess.engine;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class EngineEvaluation
{
    private static final int INTERNAL_DEFINITE_MATE = 1500;
    private static final int INTERNAL_MATE_REPRESENTATION = 2000;
    private static NumberFormat nf;
    private float _evaluation;
    private String _stringRepresentation;
    
    static {
        EngineEvaluation.nf = new DecimalFormat("##0.00");
    }
    
    public EngineEvaluation(final float evaluation) {
        this._evaluation = evaluation;
        this._stringRepresentation = EngineEvaluation.nf.format(this._evaluation);
    }
    
    public EngineEvaluation(final int n, final boolean b) {
        if (b) {
            final StringBuilder sb = new StringBuilder();
            sb.append("# ");
            sb.append(n);
            this._stringRepresentation = sb.toString();
            this._evaluation = 2000 - n;
            return;
        }
        final StringBuilder sb2 = new StringBuilder();
        sb2.append("# -");
        sb2.append(n);
        this._stringRepresentation = sb2.toString();
        this._evaluation = -2000 + n;
    }
    
    public EngineEvaluation(final String stringRepresentation) {
        if (stringRepresentation == null) {
            return;
        }
        this._stringRepresentation = stringRepresentation;
        Label_0092: {
            if (stringRepresentation.matches("\\d+") || stringRepresentation.matches("-\\d+")) {
                break Label_0092;
            }
            if (!stringRepresentation.startsWith("#")) {
                return;
            }
        Label_0055_Outer:
            while (true) {
                while (true) {
                    try {
                        int int1 = Integer.parseInt(stringRepresentation.substring(1));
                        while (true) {
                            if (int1 != 0) {
                                int n;
                                if (int1 >= 0) {
                                    n = 2000 - int1;
                                }
                                else {
                                    n = -2000 - int1;
                                }
                                this._evaluation = n;
                                return;
                            }
                            return;
                            this._evaluation = Integer.parseInt(stringRepresentation) / 100.0f;
                            return;
                            int1 = 0;
                            continue Label_0055_Outer;
                        }
                    }
                    catch (NumberFormatException ex) {}
                    continue;
                }
            }
        }
    }
    
    public float getEvaluation() {
        return this._evaluation;
    }
    
    public String getEvaluationAsString() {
        return this._stringRepresentation;
    }
    
    public int getMovesToMate() {
        if (this._evaluation < -1500.0f) {
            return (int)(-(this._evaluation + 2000.0f));
        }
        if (this._evaluation > 1500.0f) {
            return (int)(-this._evaluation + 2000.0f);
        }
        return 0;
    }
    
    public boolean isMate() {
        return this._evaluation < -1500.0f || this._evaluation > 1500.0f;
    }
}

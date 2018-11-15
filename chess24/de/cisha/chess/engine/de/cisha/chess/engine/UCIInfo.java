/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.chess.engine;

import de.cisha.chess.engine.EngineEvaluation;
import de.cisha.chess.model.position.Position;

public class UCIInfo {
    private int _depth;
    private EngineEvaluation _eval;
    private String _line;
    private int _lineNumber;
    private int _mills;
    private int _nodes;
    private Position _position;

    public UCIInfo() {
        this(null, 0, "", null, 0, 0, 0);
    }

    public UCIInfo(Position position, int n, String string, EngineEvaluation engineEvaluation, int n2, int n3, int n4) {
        this._position = position;
        this._lineNumber = n;
        this._line = string;
        this._eval = engineEvaluation;
        this._nodes = n3;
        this._mills = n4;
        this._depth = n2;
    }

    public int getDepth() {
        return this._depth;
    }

    public float getEval() {
        if (this._eval != null) {
            return this._eval.getEvaluation();
        }
        return 0.0f;
    }

    public String getEvalString() {
        if (this._eval == null) {
            return "...";
        }
        return this._eval.getEvaluationAsString();
    }

    public String getLine() {
        return this._line;
    }

    public int getLineNumber() {
        return this._lineNumber;
    }

    public int getMovesToMate() {
        if (this._eval != null) {
            return this._eval.getMovesToMate();
        }
        return 0;
    }

    public int getNodes() {
        return this._nodes;
    }

    public Position getPosition() {
        return this._position;
    }

    public int getTimeInMills() {
        return this._mills;
    }

    public boolean isMate() {
        if (this._eval == null) {
            return false;
        }
        return this._eval.isMate();
    }
}

// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.chess.engine;

import de.cisha.chess.model.position.Position;

public class UCIInfo
{
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
    
    public UCIInfo(final Position position, final int lineNumber, final String line, final EngineEvaluation eval, final int depth, final int nodes, final int mills) {
        this._position = position;
        this._lineNumber = lineNumber;
        this._line = line;
        this._eval = eval;
        this._nodes = nodes;
        this._mills = mills;
        this._depth = depth;
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
        return this._eval != null && this._eval.isMate();
    }
}

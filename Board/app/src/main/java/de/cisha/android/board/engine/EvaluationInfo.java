// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.engine;

import de.cisha.chess.model.position.Position;
import de.cisha.chess.model.position.FastMoveStringGeneratingPosition;
import de.cisha.chess.model.Move;
import de.cisha.chess.engine.UCIInfo;

public class EvaluationInfo
{
    private UCIInfo _info;
    private String _lineToShow;
    private Move _move;
    private String _toStringResult;
    
    public EvaluationInfo(final UCIInfo info) {
        this._toStringResult = null;
        this._info = info;
        this.createMoveAndLine();
    }
    
    private void createMoveAndLine() {
        this._lineToShow = "";
        if (this._info != null) {
            final String line = this._info.getLine();
            final Position position = this._info.getPosition();
            if (position != null && line != null) {
                final String[] split = line.trim().split(" ");
                if (split.length > 0) {
                    final FastMoveStringGeneratingPosition fastMoveStringGeneratingPosition = new FastMoveStringGeneratingPosition(position);
                    this._move = fastMoveStringGeneratingPosition.createMoveFromCAN(split[0]);
                    this._lineToShow = fastMoveStringGeneratingPosition.convertCANToFAN(split);
                }
            }
        }
    }
    
    public int getDepth() {
        if (this._info.isMate()) {
            final int n = this._info.getMovesToMate() * 2;
            int n2;
            if (this._info.getEval() > 0.0f) {
                n2 = n;
                if (this._info.getPosition().getActiveColor()) {
                    return n - 1;
                }
            }
            else {
                n2 = n;
                if (!this._info.getPosition().getActiveColor()) {
                    n2 = n - 1;
                }
            }
            return n2;
        }
        return this._info.getDepth();
    }
    
    public float getEval() {
        return this._info.getEval();
    }
    
    public String getEvalString() {
        return this._info.getEvalString();
    }
    
    public int getLineNumber() {
        return this._info.getLineNumber();
    }
    
    public String getLineToString() {
        return this._lineToShow;
    }
    
    public Move getMove() {
        return this._move;
    }
    
    @Override
    public String toString() {
        if (this._toStringResult == null) {
            final StringBuilder sb = new StringBuilder();
            sb.append("");
            sb.append(this.getLineNumber());
            sb.append(" (");
            sb.append(this.getEvalString());
            sb.append(") ");
            this._toStringResult = sb.toString();
            final StringBuilder sb2 = new StringBuilder();
            sb2.append(this._toStringResult);
            sb2.append(this.getLineToString());
            this._toStringResult = sb2.toString();
        }
        return this._toStringResult;
    }
}

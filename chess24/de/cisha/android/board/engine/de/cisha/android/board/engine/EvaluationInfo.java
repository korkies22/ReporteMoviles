/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.engine;

import de.cisha.chess.engine.UCIInfo;
import de.cisha.chess.model.Move;
import de.cisha.chess.model.position.AbstractPosition;
import de.cisha.chess.model.position.FastMoveStringGeneratingPosition;
import de.cisha.chess.model.position.Position;

public class EvaluationInfo {
    private UCIInfo _info;
    private String _lineToShow;
    private Move _move;
    private String _toStringResult = null;

    public EvaluationInfo(UCIInfo uCIInfo) {
        this._info = uCIInfo;
        this.createMoveAndLine();
    }

    private void createMoveAndLine() {
        this._lineToShow = "";
        if (this._info != null) {
            String[] arrstring = this._info.getLine();
            AbstractPosition abstractPosition = this._info.getPosition();
            if (abstractPosition != null && arrstring != null && (arrstring = arrstring.trim().split(" ")).length > 0) {
                abstractPosition = new FastMoveStringGeneratingPosition((Position)abstractPosition);
                this._move = abstractPosition.createMoveFromCAN(arrstring[0]);
                this._lineToShow = abstractPosition.convertCANToFAN(arrstring);
            }
        }
    }

    public int getDepth() {
        if (this._info.isMate()) {
            int n;
            int n2 = this._info.getMovesToMate() * 2;
            if (this._info.getEval() > 0.0f) {
                n = n2;
                if (this._info.getPosition().getActiveColor()) {
                    return n2 - 1;
                }
            } else {
                n = n2;
                if (!this._info.getPosition().getActiveColor()) {
                    n = n2 - 1;
                }
            }
            return n;
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

    public String toString() {
        if (this._toStringResult == null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("");
            stringBuilder.append(this.getLineNumber());
            stringBuilder.append(" (");
            stringBuilder.append(this.getEvalString());
            stringBuilder.append(") ");
            this._toStringResult = stringBuilder.toString();
            stringBuilder = new StringBuilder();
            stringBuilder.append(this._toStringResult);
            stringBuilder.append(this.getLineToString());
            this._toStringResult = stringBuilder.toString();
        }
        return this._toStringResult;
    }
}

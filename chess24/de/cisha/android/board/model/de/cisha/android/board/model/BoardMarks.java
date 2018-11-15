/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.model;

import de.cisha.android.board.model.ArrowContainer;
import de.cisha.android.board.model.SquareMarkings;

public class BoardMarks {
    private ArrowContainer _arrowContainer;
    private SquareMarkings _markedSquares;

    public BoardMarks() {
        this._arrowContainer = new ArrowContainer();
        this._markedSquares = new SquareMarkings();
    }

    public BoardMarks(BoardMarks boardMarks) {
        this._arrowContainer = new ArrowContainer(boardMarks.getArrowContainer());
        this._markedSquares = new SquareMarkings(boardMarks.getSquareMarkings());
    }

    public void clear() {
        this._arrowContainer.removeAllArrows();
        this._markedSquares.clear();
    }

    public ArrowContainer getArrowContainer() {
        return this._arrowContainer;
    }

    public SquareMarkings getSquareMarkings() {
        return this._markedSquares;
    }
}

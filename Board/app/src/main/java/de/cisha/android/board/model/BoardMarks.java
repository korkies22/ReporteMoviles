// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.model;

public class BoardMarks
{
    private ArrowContainer _arrowContainer;
    private SquareMarkings _markedSquares;
    
    public BoardMarks() {
        this._arrowContainer = new ArrowContainer();
        this._markedSquares = new SquareMarkings();
    }
    
    public BoardMarks(final BoardMarks boardMarks) {
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

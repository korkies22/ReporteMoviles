// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.setup.model;

import java.util.Iterator;
import de.cisha.chess.model.Move;
import de.cisha.chess.model.Piece;
import de.cisha.chess.model.Square;
import de.cisha.chess.model.pieces.King;
import de.cisha.chess.model.ModifyablePosition;
import java.util.LinkedList;
import de.cisha.chess.model.FEN;
import de.cisha.chess.model.position.Position;
import de.cisha.chess.model.position.PositionObserver;
import java.util.List;
import de.cisha.chess.model.position.PositionObservable;

public class PositionHolder implements PositionObservable
{
    private List<PositionObserver> _observer;
    private Position _position;
    
    public PositionHolder() {
        this._position = new Position(FEN.STARTING_POSITION);
        this._observer = new LinkedList<PositionObserver>();
    }
    
    @Override
    public void addPositionObserver(final PositionObserver positionObserver) {
        this._observer.add(positionObserver);
    }
    
    public void clearPosition() {
        final ModifyablePosition modifyablePosition = new ModifyablePosition(FEN.EMPTY_POSITION);
        modifyablePosition.setPiece(King.instanceForColor(true), Square.SQUARE_E1);
        modifyablePosition.setPiece(King.instanceForColor(false), Square.SQUARE_E8);
        this.setPosition(modifyablePosition.getSafePosition());
    }
    
    @Override
    public FEN getFEN() {
        return this._position.getFEN();
    }
    
    @Override
    public Position getPosition() {
        return this._position;
    }
    
    @Override
    public void removePositionObserver(final PositionObserver positionObserver) {
        this._observer.remove(positionObserver);
    }
    
    public void setPosition(final Position position) {
        this._position = position;
        final Iterator<PositionObserver> iterator = this._observer.iterator();
        while (iterator.hasNext()) {
            iterator.next().positionChanged(position, null);
        }
    }
}

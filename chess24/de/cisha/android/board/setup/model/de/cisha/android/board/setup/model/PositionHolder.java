/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.setup.model;

import de.cisha.chess.model.FEN;
import de.cisha.chess.model.ModifyablePosition;
import de.cisha.chess.model.Move;
import de.cisha.chess.model.Piece;
import de.cisha.chess.model.Square;
import de.cisha.chess.model.pieces.King;
import de.cisha.chess.model.position.Position;
import de.cisha.chess.model.position.PositionObservable;
import de.cisha.chess.model.position.PositionObserver;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class PositionHolder
implements PositionObservable {
    private List<PositionObserver> _observer = new LinkedList<PositionObserver>();
    private Position _position = new Position(FEN.STARTING_POSITION);

    @Override
    public void addPositionObserver(PositionObserver positionObserver) {
        this._observer.add(positionObserver);
    }

    public void clearPosition() {
        ModifyablePosition modifyablePosition = new ModifyablePosition(FEN.EMPTY_POSITION);
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
    public void removePositionObserver(PositionObserver positionObserver) {
        this._observer.remove(positionObserver);
    }

    public void setPosition(Position position) {
        this._position = position;
        Iterator<PositionObserver> iterator = this._observer.iterator();
        while (iterator.hasNext()) {
            iterator.next().positionChanged(position, null);
        }
    }
}

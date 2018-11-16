// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.chess.model.position;

import de.cisha.chess.model.SEP;
import de.cisha.chess.model.Move;
import de.cisha.chess.model.pieces.Pawn;
import de.cisha.chess.model.Piece;
import de.cisha.chess.model.Square;
import de.cisha.chess.model.PieceInformation;
import java.util.List;
import de.cisha.chess.model.FEN;

public class MutablePosition extends AbstractPosition
{
    public MutablePosition() {
    }
    
    public MutablePosition(final FEN fen) {
        super(fen);
    }
    
    public MutablePosition(final AbstractPosition abstractPosition) {
        super(abstractPosition);
    }
    
    public MutablePosition(final List<PieceInformation> list) {
        super(list);
    }
    
    private void doMove(final Square square, final Square square2, final Piece piece) {
        while (true) {
            while (true) {
                Label_0206: {
                    synchronized (this) {
                        final Piece piece2 = this.getPieceFor(square);
                        if (piece2 != null && piece2.getColor() == this._activeColor) {
                            ++this._actionCounter;
                            if (!this.isEmpty(square2) || piece2 instanceof Pawn) {
                                this._actionCounter = 0;
                            }
                            if (this.getPieceFor(square2) != null) {
                                this.removePiece(square2);
                            }
                            else if (this.isEnPassant(piece2, square, square2)) {
                                final char file = square2.getFile();
                                if (!piece2.getColor()) {
                                    break Label_0206;
                                }
                                final int n = 5;
                                this.removePiece(Square.instanceForFileAndRank(file, n));
                            }
                            if (this.isCastling(piece2, square, square2)) {
                                this.moveCastlingRook(square, square2);
                            }
                            this.removePiece(square);
                            if (this.isPromotion(piece2, square, square2)) {
                                this.setPiece(piece, square2);
                            }
                            else {
                                this.setPiece(piece2, square2);
                            }
                            this.updateCastlingAttributes(square, square2);
                            this._activeColor ^= true;
                            ++this._moveCounter;
                            this.updateEnPassant(piece2, square, square2);
                        }
                        return;
                    }
                }
                final int n = 4;
                continue;
            }
        }
    }
    
    private void moveCastlingRook(Square square, final Square square2) {
        if (square.getColumn() + 1 < square2.getColumn()) {
            square = Square.instanceForRowAndColumn(square2.getRow(), 7);
            final Piece piece = this.getPieceFor(square);
            this.removePiece(square);
            this.setPiece(piece, Square.instanceForRowAndColumn(square2.getRow(), square2.getColumn() - 1));
            return;
        }
        if (square.getColumn() - 1 > square2.getColumn()) {
            square = Square.instanceForRowAndColumn(square2.getRow(), 0);
            final Piece piece2 = this.getPieceFor(square);
            this.removePiece(square);
            this.setPiece(piece2, Square.instanceForRowAndColumn(square2.getRow(), square2.getColumn() + 1));
        }
    }
    
    private void updateCastlingAttributes(final Square square, final Square square2) {
        if (this._activeColor) {
            if (square.equals(Square.instanceForRowAndColumn(0, 4))) {
                this._whiteCanCastleLong = false;
                this._whiteCanCastleShort = false;
                return;
            }
            if (square.equals(Square.instanceForRowAndColumn(0, 0))) {
                this._whiteCanCastleLong = false;
                return;
            }
            if (square.equals(Square.instanceForRowAndColumn(0, 7))) {
                this._whiteCanCastleShort = false;
                return;
            }
            if (square2.equals(Square.instanceForRowAndColumn(7, 0))) {
                this._blackCanCastleLong = false;
                return;
            }
            if (square2.equals(Square.instanceForRowAndColumn(7, 7))) {
                this._blackCanCastleShort = false;
            }
        }
        else {
            if (square.equals(Square.instanceForRowAndColumn(7, 4))) {
                this._blackCanCastleLong = false;
                this._blackCanCastleShort = false;
                return;
            }
            if (square.equals(Square.instanceForRowAndColumn(7, 0))) {
                this._blackCanCastleLong = false;
                return;
            }
            if (square.equals(Square.instanceForRowAndColumn(7, 7))) {
                this._blackCanCastleShort = false;
                return;
            }
            if (square2.equals(Square.instanceForRowAndColumn(0, 0))) {
                this._whiteCanCastleLong = false;
                return;
            }
            if (square2.equals(Square.instanceForRowAndColumn(0, 7))) {
                this._whiteCanCastleShort = false;
            }
        }
    }
    
    private void updateEnPassant(final Piece piece, final Square square, final Square square2) {
        this._epColumn = -1;
        if (piece instanceof Pawn && Math.abs(square.getRank() - square2.getRank()) > 1) {
            this._epColumn = square2.getColumn();
        }
    }
    
    public void doMove(final Move move) {
        if (!move.isNullMove()) {
            this.doMove(move.getSquareFrom(), move.getSquareTo(), move.getPromotionPiece());
            return;
        }
        this.doNullMove();
    }
    
    public void doMove(final SEP sep) {
        if (sep.isNullMove()) {
            this.doNullMove();
            return;
        }
        final Piece piece = this.getPieceFor(sep.getStartSquare());
        if (piece != null) {
            this.doMove(sep.getStartSquare(), sep.getEndSquare(), this.getPromotionPiece(sep.getPromotionPiece(), piece.getColor()));
        }
    }
    
    public void doNullMove() {
        this._epColumn = -1;
        this._activeColor ^= true;
        ++this._moveCounter;
    }
    
    @Override
    public MutablePosition getMutablePosition() {
        return new MutablePosition(this);
    }
    
    @Override
    public Position getSafePosition() {
        return new Position(this);
    }
}

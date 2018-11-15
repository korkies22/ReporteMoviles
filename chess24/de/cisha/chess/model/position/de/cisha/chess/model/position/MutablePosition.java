/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.chess.model.position;

import de.cisha.chess.model.FEN;
import de.cisha.chess.model.Move;
import de.cisha.chess.model.Piece;
import de.cisha.chess.model.PieceInformation;
import de.cisha.chess.model.SEP;
import de.cisha.chess.model.Square;
import de.cisha.chess.model.pieces.Pawn;
import de.cisha.chess.model.position.AbstractPosition;
import de.cisha.chess.model.position.Position;
import java.util.List;

public class MutablePosition
extends AbstractPosition {
    public MutablePosition() {
    }

    public MutablePosition(FEN fEN) {
        super(fEN);
    }

    public MutablePosition(AbstractPosition abstractPosition) {
        super(abstractPosition);
    }

    public MutablePosition(List<PieceInformation> list) {
        super(list);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void doMove(Square square, Square square2, Piece piece) {
        synchronized (this) {
            Piece piece2 = this.getPieceFor(square);
            if (piece2 != null && piece2.getColor() == this._activeColor) {
                ++this._actionCounter;
                if (!this.isEmpty(square2) || piece2 instanceof Pawn) {
                    this._actionCounter = 0;
                }
                if (this.getPieceFor(square2) != null) {
                    this.removePiece(square2);
                } else if (this.isEnPassant(piece2, square, square2)) {
                    char c = square2.getFile();
                    int n = piece2.getColor() ? 5 : 4;
                    this.removePiece(Square.instanceForFileAndRank(c, n));
                }
                if (this.isCastling(piece2, square, square2)) {
                    this.moveCastlingRook(square, square2);
                }
                this.removePiece(square);
                if (this.isPromotion(piece2, square, square2)) {
                    this.setPiece(piece, square2);
                } else {
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

    private void moveCastlingRook(Square square, Square square2) {
        if (square.getColumn() + 1 < square2.getColumn()) {
            square = Square.instanceForRowAndColumn(square2.getRow(), 7);
            Piece piece = this.getPieceFor(square);
            this.removePiece(square);
            this.setPiece(piece, Square.instanceForRowAndColumn(square2.getRow(), square2.getColumn() - 1));
            return;
        }
        if (square.getColumn() - 1 > square2.getColumn()) {
            square = Square.instanceForRowAndColumn(square2.getRow(), 0);
            Piece piece = this.getPieceFor(square);
            this.removePiece(square);
            this.setPiece(piece, Square.instanceForRowAndColumn(square2.getRow(), square2.getColumn() + 1));
        }
    }

    private void updateCastlingAttributes(Square square, Square square2) {
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
                return;
            }
        } else {
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

    private void updateEnPassant(Piece piece, Square square, Square square2) {
        this._epColumn = -1;
        if (piece instanceof Pawn && Math.abs(square.getRank() - square2.getRank()) > 1) {
            this._epColumn = square2.getColumn();
        }
    }

    public void doMove(Move move) {
        if (!move.isNullMove()) {
            this.doMove(move.getSquareFrom(), move.getSquareTo(), move.getPromotionPiece());
            return;
        }
        this.doNullMove();
    }

    public void doMove(SEP sEP) {
        if (sEP.isNullMove()) {
            this.doNullMove();
            return;
        }
        Piece piece = this.getPieceFor(sEP.getStartSquare());
        if (piece != null) {
            this.doMove(sEP.getStartSquare(), sEP.getEndSquare(), this.getPromotionPiece(sEP.getPromotionPiece(), piece.getColor()));
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

/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.chess.model;

import de.cisha.chess.model.AbstractMoveContainer;
import de.cisha.chess.model.Game;
import de.cisha.chess.model.MoveContainer;
import de.cisha.chess.model.Piece;
import de.cisha.chess.model.PieceSetup;
import de.cisha.chess.model.SEP;
import de.cisha.chess.model.Square;
import de.cisha.chess.model.position.Position;
import de.cisha.chess.util.FENHelper;
import java.io.Serializable;

public class Move
extends AbstractMoveContainer
implements Serializable {
    private static final long serialVersionUID = -1092013934009633099L;
    private boolean _checkmates = false;
    private boolean _checks = false;
    private String _concurrentString = "";
    private boolean _enPassant;
    private boolean _isUsermove = false;
    private Piece _movingPiece;
    private boolean _promotesPawn = false;
    private Piece _promotionPiece;
    private Position _resultingPosition;
    private Square _squareFrom;
    private Square _squareTo;
    private boolean _takes = false;
    private Piece _takesPiece;

    protected Move(Move move) {
        super(move);
        this._squareFrom = move._squareFrom;
        this._squareTo = move._squareTo;
        this._checks = move._checks;
        this._checkmates = move._checkmates;
        this._takes = move._takes;
        this._enPassant = move._enPassant;
        this._promotesPawn = move._promotesPawn;
        this._takesPiece = move._takesPiece;
        this._movingPiece = move._movingPiece;
        this._promotionPiece = move._promotionPiece;
        this._resultingPosition = move.getResultingPosition().getSafePosition();
        this._concurrentString = move._concurrentString;
        this._isUsermove = move._isUsermove;
    }

    public Move(Square object, Square square, boolean bl, boolean bl2, boolean bl3, boolean bl4, boolean bl5, Piece piece, Piece piece2, Piece piece3, String string, Position position, boolean bl6) {
        this._squareFrom = object;
        this._squareTo = square;
        this._checks = bl;
        this._checkmates = bl2;
        this._takes = bl3;
        this._enPassant = bl4;
        this._promotesPawn = bl5;
        this._takesPiece = piece;
        this._movingPiece = piece2;
        this._promotionPiece = piece3;
        this._resultingPosition = position;
        object = string;
        if (string == null) {
            object = "";
        }
        this._concurrentString = object;
        this._isUsermove = bl6;
    }

    private String getCastlingString() {
        if ((this._movingPiece.getFENChar() == 'k' || this._movingPiece.getFENChar() == 'K') && Math.abs(this._squareFrom.getColumn() - this._squareTo.getColumn()) > 1) {
            if (this._squareFrom.getColumn() < this._squareTo.getColumn()) {
                return "0-0";
            }
            return "0-0-0";
        }
        return null;
    }

    private String getChecksString() {
        if (this._checks) {
            if (this._checkmates) {
                return "#";
            }
            return "+";
        }
        return "";
    }

    private String getSANWithFigurine(boolean bl) {
        CharSequence charSequence = "";
        CharSequence charSequence2 = charSequence;
        if (this._movingPiece != null) {
            charSequence2 = charSequence;
            if (this._squareTo != null) {
                charSequence2 = charSequence;
                if (this._concurrentString != null) {
                    CharSequence charSequence3;
                    charSequence = new StringBuilder();
                    charSequence2 = bl ? this._movingPiece.getPieceFigurineNotation() : this._movingPiece.getPieceNotation();
                    charSequence.append((String)charSequence2);
                    charSequence.append(this._concurrentString);
                    charSequence2 = this._takes ? "x" : "";
                    charSequence.append((String)charSequence2);
                    charSequence.append(this._squareTo.getName());
                    if (this._promotionPiece != null) {
                        charSequence3 = new StringBuilder();
                        charSequence3.append("");
                        charSequence2 = bl ? this._promotionPiece.getPieceFigurineNotation() : this._promotionPiece.getPieceNotation();
                        charSequence3.append((String)charSequence2);
                        charSequence2 = ((StringBuilder)charSequence3).toString();
                    } else {
                        charSequence2 = "";
                    }
                    charSequence.append((String)charSequence2);
                    charSequence = charSequence.toString();
                    charSequence3 = this.getCastlingString();
                    charSequence2 = charSequence;
                    if (charSequence3 != null) {
                        charSequence2 = charSequence;
                        if (!charSequence3.isEmpty()) {
                            charSequence2 = charSequence3;
                        }
                    }
                }
            }
        }
        charSequence = new StringBuilder();
        charSequence.append((String)charSequence2);
        charSequence.append(this.getChecksString());
        charSequence2 = charSequence.toString();
        charSequence = new StringBuilder();
        charSequence.append((String)charSequence2);
        charSequence.append(this.getSymbols());
        return charSequence.toString();
    }

    @Override
    public Move copy() {
        return new Move(this);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public boolean equals(Object object) {
        boolean bl = true;
        if (object == this) {
            return true;
        }
        if (object == null) return false;
        if (!(object instanceof Move)) return false;
        object = (Move)object;
        if (this.getSEP().getSEPNumber() != object.getSEP().getSEPNumber()) return false;
        if (!this.getResultingPosition().equals(object.getResultingPosition())) return false;
        if (this.getParent() == null) {
            if (object.getParent() == null) return bl;
        }
        if (this.getParent() == null) return false;
        if (!this.getParent().equals(object.getParent())) return false;
        return true;
    }

    protected String getConcurrentString() {
        return this._concurrentString;
    }

    public String getEAN() {
        CharSequence charSequence = "";
        String string = charSequence;
        if (this._squareTo != null) {
            string = charSequence;
            if (this._squareFrom != null) {
                charSequence = new StringBuilder();
                charSequence.append(this._squareFrom.getName());
                charSequence.append(this._squareTo.getName());
                string = this._promotionPiece != null ? this._promotionPiece.getPieceNotation() : "";
                charSequence.append(string);
                string = charSequence.toString();
            }
        }
        return string;
    }

    public String getFAN() {
        return this.getSANWithFigurine(true);
    }

    @Override
    public Move getFirstMove() {
        return this;
    }

    @Override
    public Game getGame() {
        MoveContainer moveContainer;
        MoveContainer moveContainer2 = moveContainer = this.getParent();
        while (moveContainer != null) {
            MoveContainer moveContainer3 = moveContainer.getParent();
            moveContainer2 = moveContainer;
            moveContainer = moveContainer3;
        }
        if (moveContainer2 instanceof Game) {
            return (Game)moveContainer2;
        }
        return null;
    }

    public int getHalfMoveNumber() {
        if (this._resultingPosition != null) {
            return this._resultingPosition.getHalfMoveNumber();
        }
        return 1;
    }

    public String getLAN() {
        CharSequence charSequence;
        CharSequence charSequence2 = charSequence = "";
        if (this._movingPiece != null) {
            charSequence2 = charSequence;
            if (this._squareTo != null) {
                charSequence2 = charSequence;
                if (this._squareFrom != null) {
                    charSequence = new StringBuilder();
                    charSequence.append(this._movingPiece.getPieceNotation());
                    charSequence.append(this._squareFrom.getName());
                    charSequence2 = this._takes ? "x" : "-";
                    charSequence.append((String)charSequence2);
                    charSequence.append(this._squareTo.getName());
                    if (this._promotionPiece != null) {
                        charSequence2 = new StringBuilder();
                        charSequence2.append("");
                        charSequence2.append(this._promotionPiece.getPieceNotation());
                        charSequence2 = charSequence2.toString();
                    } else {
                        charSequence2 = "";
                    }
                    charSequence.append((String)charSequence2);
                    charSequence = charSequence.toString();
                    String string = this.getCastlingString();
                    charSequence2 = charSequence;
                    if (string != null) {
                        charSequence2 = charSequence;
                        if (!string.isEmpty()) {
                            charSequence2 = string;
                        }
                    }
                }
            }
        }
        charSequence = new StringBuilder();
        charSequence.append((String)charSequence2);
        charSequence.append(this.getChecksString());
        return charSequence.toString();
    }

    public int getMoveNumber() {
        if (this._resultingPosition != null) {
            return this._resultingPosition.getMoveNumber();
        }
        return 1;
    }

    public Piece getPiece() {
        return this._movingPiece;
    }

    public Piece getPieceTaken() {
        return this._takesPiece;
    }

    public Piece getPromotionPiece() {
        return this._promotionPiece;
    }

    public PieceSetup getResultingPieceSetup() {
        return this._resultingPosition;
    }

    @Override
    public Position getResultingPosition() {
        return this._resultingPosition;
    }

    public String getSAN() {
        return this.getSANWithFigurine(false);
    }

    public SEP getSEP() {
        int n = this.getPromotionPiece() != null ? FENHelper.getPromotionTypeForFENChar(this.getPromotionPiece().getFENChar()) : 0;
        return new SEP(this.getSquareFrom(), this.getSquareTo(), n);
    }

    public Square getSquareFrom() {
        return this._squareFrom;
    }

    public Square getSquareTo() {
        return this._squareTo;
    }

    public Piece getTakenPiece() {
        return this._takesPiece;
    }

    public Square getTakesSquare() {
        Square square = this._takes ? this.getSquareTo() : null;
        Square square2 = square;
        if (this.isEnPassant()) {
            square2 = square;
            if (square != null) {
                if (this.getSquareTo().getRank() == 6) {
                    return Square.instanceForRowAndColumn(square.getRow() - 1, square.getColumn());
                }
                square2 = Square.instanceForRowAndColumn(square.getRow() + 1, square.getColumn());
            }
        }
        return square2;
    }

    public int hashCode() {
        return this.getSEP().getSEPNumber() + this.getResultingPosition().hashCode();
    }

    public boolean isCheck() {
        return this._checks;
    }

    public boolean isCheckMate() {
        return this._checkmates;
    }

    public boolean isEnPassant() {
        return this._enPassant;
    }

    public boolean isNullMove() {
        return false;
    }

    public boolean isPromoting() {
        return this._promotesPawn;
    }

    public boolean isTaking() {
        return this._takes;
    }

    public boolean isUserMove() {
        return this._isUsermove;
    }

    public void removeFromParent() {
        MoveContainer moveContainer = this.getParent();
        if (moveContainer != null) {
            moveContainer.removeMove(this);
            this.setParent(null);
        }
    }

    protected void setResultingPosition(Position position) {
        this._resultingPosition = position;
    }

    public void setUserGenerated(boolean bl) {
        this._isUsermove = bl;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this._movingPiece);
        stringBuilder.append(" ");
        stringBuilder.append(this._squareFrom);
        stringBuilder.append("-");
        stringBuilder.append(this._squareTo);
        stringBuilder.append(" takes(");
        stringBuilder.append(this._takesPiece);
        stringBuilder.append(") ");
        stringBuilder.append(this._promotionPiece);
        return stringBuilder.toString();
    }
}

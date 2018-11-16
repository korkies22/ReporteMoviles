// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.chess.model;

import de.cisha.chess.util.FENHelper;
import de.cisha.chess.model.position.Position;
import java.io.Serializable;

public class Move extends AbstractMoveContainer implements Serializable
{
    private static final long serialVersionUID = -1092013934009633099L;
    private boolean _checkmates;
    private boolean _checks;
    private String _concurrentString;
    private boolean _enPassant;
    private boolean _isUsermove;
    private Piece _movingPiece;
    private boolean _promotesPawn;
    private Piece _promotionPiece;
    private Position _resultingPosition;
    private Square _squareFrom;
    private Square _squareTo;
    private boolean _takes;
    private Piece _takesPiece;
    
    protected Move(final Move move) {
        super(move);
        this._takes = false;
        this._promotesPawn = false;
        this._checks = false;
        this._checkmates = false;
        this._concurrentString = "";
        this._isUsermove = false;
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
    
    public Move(final Square squareFrom, final Square squareTo, final boolean checks, final boolean checkmates, final boolean takes, final boolean enPassant, final boolean promotesPawn, final Piece takesPiece, final Piece movingPiece, final Piece promotionPiece, final String s, final Position resultingPosition, final boolean isUsermove) {
        this._takes = false;
        this._promotesPawn = false;
        this._checks = false;
        this._checkmates = false;
        this._concurrentString = "";
        this._isUsermove = false;
        this._squareFrom = squareFrom;
        this._squareTo = squareTo;
        this._checks = checks;
        this._checkmates = checkmates;
        this._takes = takes;
        this._enPassant = enPassant;
        this._promotesPawn = promotesPawn;
        this._takesPiece = takesPiece;
        this._movingPiece = movingPiece;
        this._promotionPiece = promotionPiece;
        this._resultingPosition = resultingPosition;
        String concurrentString = s;
        if (s == null) {
            concurrentString = "";
        }
        this._concurrentString = concurrentString;
        this._isUsermove = isUsermove;
    }
    
    private String getCastlingString() {
        if ((this._movingPiece.getFENChar() != 'k' && this._movingPiece.getFENChar() != 'K') || Math.abs(this._squareFrom.getColumn() - this._squareTo.getColumn()) <= 1) {
            return null;
        }
        if (this._squareFrom.getColumn() < this._squareTo.getColumn()) {
            return "0-0";
        }
        return "0-0-0";
    }
    
    private String getChecksString() {
        if (!this._checks) {
            return "";
        }
        if (this._checkmates) {
            return "#";
        }
        return "+";
    }
    
    private String getSANWithFigurine(final boolean b) {
        String s2;
        final String s = s2 = "";
        if (this._movingPiece != null) {
            s2 = s;
            if (this._squareTo != null) {
                s2 = s;
                if (this._concurrentString != null) {
                    final StringBuilder sb = new StringBuilder();
                    String s3;
                    if (b) {
                        s3 = this._movingPiece.getPieceFigurineNotation();
                    }
                    else {
                        s3 = this._movingPiece.getPieceNotation();
                    }
                    sb.append(s3);
                    sb.append(this._concurrentString);
                    String s4;
                    if (this._takes) {
                        s4 = "x";
                    }
                    else {
                        s4 = "";
                    }
                    sb.append(s4);
                    sb.append(this._squareTo.getName());
                    String string;
                    if (this._promotionPiece != null) {
                        final StringBuilder sb2 = new StringBuilder();
                        sb2.append("");
                        String s5;
                        if (b) {
                            s5 = this._promotionPiece.getPieceFigurineNotation();
                        }
                        else {
                            s5 = this._promotionPiece.getPieceNotation();
                        }
                        sb2.append(s5);
                        string = sb2.toString();
                    }
                    else {
                        string = "";
                    }
                    sb.append(string);
                    final String string2 = sb.toString();
                    final String castlingString = this.getCastlingString();
                    s2 = string2;
                    if (castlingString != null) {
                        s2 = string2;
                        if (!castlingString.isEmpty()) {
                            s2 = castlingString;
                        }
                    }
                }
            }
        }
        final StringBuilder sb3 = new StringBuilder();
        sb3.append(s2);
        sb3.append(this.getChecksString());
        final String string3 = sb3.toString();
        final StringBuilder sb4 = new StringBuilder();
        sb4.append(string3);
        sb4.append(this.getSymbols());
        return sb4.toString();
    }
    
    public Move copy() {
        return new Move(this);
    }
    
    @Override
    public boolean equals(final Object o) {
        boolean b = true;
        if (o == this) {
            return true;
        }
        if (o != null && o instanceof Move) {
            final Move move = (Move)o;
            if (this.getSEP().getSEPNumber() == move.getSEP().getSEPNumber() && this.getResultingPosition().equals(move.getResultingPosition())) {
                if (this.getParent() == null && move.getParent() == null) {
                    return b;
                }
                if (this.getParent() != null && this.getParent().equals(move.getParent())) {
                    return true;
                }
            }
            b = false;
            return b;
        }
        return false;
    }
    
    protected String getConcurrentString() {
        return this._concurrentString;
    }
    
    public String getEAN() {
        String string = "";
        if (this._squareTo != null) {
            string = string;
            if (this._squareFrom != null) {
                final StringBuilder sb = new StringBuilder();
                sb.append(this._squareFrom.getName());
                sb.append(this._squareTo.getName());
                String pieceNotation;
                if (this._promotionPiece != null) {
                    pieceNotation = this._promotionPiece.getPieceNotation();
                }
                else {
                    pieceNotation = "";
                }
                sb.append(pieceNotation);
                string = sb.toString();
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
        MoveContainer parent;
        MoveContainer parent2;
        for (MoveContainer moveContainer = parent = this.getParent(); moveContainer != null; moveContainer = parent2) {
            parent2 = moveContainer.getParent();
            parent = moveContainer;
        }
        if (parent instanceof Game) {
            return (Game)parent;
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
        String s2;
        final String s = s2 = "";
        if (this._movingPiece != null) {
            s2 = s;
            if (this._squareTo != null) {
                s2 = s;
                if (this._squareFrom != null) {
                    final StringBuilder sb = new StringBuilder();
                    sb.append(this._movingPiece.getPieceNotation());
                    sb.append(this._squareFrom.getName());
                    String s3;
                    if (this._takes) {
                        s3 = "x";
                    }
                    else {
                        s3 = "-";
                    }
                    sb.append(s3);
                    sb.append(this._squareTo.getName());
                    String string;
                    if (this._promotionPiece != null) {
                        final StringBuilder sb2 = new StringBuilder();
                        sb2.append("");
                        sb2.append(this._promotionPiece.getPieceNotation());
                        string = sb2.toString();
                    }
                    else {
                        string = "";
                    }
                    sb.append(string);
                    final String string2 = sb.toString();
                    final String castlingString = this.getCastlingString();
                    s2 = string2;
                    if (castlingString != null) {
                        s2 = string2;
                        if (!castlingString.isEmpty()) {
                            s2 = castlingString;
                        }
                    }
                }
            }
        }
        final StringBuilder sb3 = new StringBuilder();
        sb3.append(s2);
        sb3.append(this.getChecksString());
        return sb3.toString();
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
        int promotionTypeForFENChar;
        if (this.getPromotionPiece() != null) {
            promotionTypeForFENChar = FENHelper.getPromotionTypeForFENChar(this.getPromotionPiece().getFENChar());
        }
        else {
            promotionTypeForFENChar = 0;
        }
        return new SEP(this.getSquareFrom(), this.getSquareTo(), promotionTypeForFENChar);
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
        Square squareTo;
        if (this._takes) {
            squareTo = this.getSquareTo();
        }
        else {
            squareTo = null;
        }
        Square instanceForRowAndColumn = squareTo;
        if (this.isEnPassant() && (instanceForRowAndColumn = squareTo) != null) {
            if (this.getSquareTo().getRank() == 6) {
                return Square.instanceForRowAndColumn(squareTo.getRow() - 1, squareTo.getColumn());
            }
            instanceForRowAndColumn = Square.instanceForRowAndColumn(squareTo.getRow() + 1, squareTo.getColumn());
        }
        return instanceForRowAndColumn;
    }
    
    @Override
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
        final MoveContainer parent = this.getParent();
        if (parent != null) {
            parent.removeMove(this);
            this.setParent(null);
        }
    }
    
    protected void setResultingPosition(final Position resultingPosition) {
        this._resultingPosition = resultingPosition;
    }
    
    public void setUserGenerated(final boolean isUsermove) {
        this._isUsermove = isUsermove;
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(this._movingPiece);
        sb.append(" ");
        sb.append(this._squareFrom);
        sb.append("-");
        sb.append(this._squareTo);
        sb.append(" takes(");
        sb.append(this._takesPiece);
        sb.append(") ");
        sb.append(this._promotionPiece);
        return sb.toString();
    }
}

// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.chess.model.position;

import de.cisha.chess.model.pieces.King;
import de.cisha.chess.model.Piece;
import de.cisha.chess.model.Square;
import de.cisha.chess.model.pieces.Pawn;
import de.cisha.chess.model.SEP;

public class FastMoveStringGeneratingPosition extends MutablePosition
{
    private static final long serialVersionUID = -7182471444896727287L;
    
    public FastMoveStringGeneratingPosition(final Position position) {
        super(position);
    }
    
    public String convertCANToFAN(final String[] array) {
        final StringBuilder sb = new StringBuilder();
        final boolean activeColor = this.getActiveColor();
        boolean b = true;
        final int length = array.length;
        int n = (activeColor ^ true) ? 1 : 0;
        for (int i = 0; i < length; ++i) {
            final SEP sep = new SEP(array[i]);
            final Square startSquare = sep.getStartSquare();
            final Square endSquare = sep.getEndSquare();
            final Piece piece = this.getPieceFor(startSquare);
            if (piece != null) {
                if (this.getPieceFor(endSquare) == null) {
                    b = false;
                }
                String string = "";
                Piece piece2 = null;
                boolean b3 = false;
                boolean b4 = false;
                String s = null;
                Label_0297: {
                    boolean b2 = false;
                    String concurrentStringForMove = null;
                    Label_0280: {
                        if (piece instanceof Pawn) {
                            if (startSquare.getColumn() != endSquare.getColumn()) {
                                final StringBuilder sb2 = new StringBuilder();
                                sb2.append("");
                                sb2.append(startSquare.getFile());
                                string = sb2.toString();
                                if (this.isEmpty(endSquare)) {
                                    this.setPiece(null, Square.instanceForRowAndColumn(startSquare.getRow(), endSquare.getColumn()));
                                }
                                b = true;
                            }
                            if (piece == Pawn.WHITE && endSquare.getRow() == 7) {
                                piece2 = this.getPromotionPiece(sep.getPromotionPiece(), true);
                            }
                            else {
                                b2 = b;
                                concurrentStringForMove = string;
                                if (piece != Pawn.BLACK) {
                                    break Label_0280;
                                }
                                b2 = b;
                                concurrentStringForMove = string;
                                if (endSquare.getRow() != 0) {
                                    break Label_0280;
                                }
                                piece2 = this.getPromotionPiece(sep.getPromotionPiece(), false);
                            }
                            b3 = true;
                            b4 = b;
                            s = string;
                            break Label_0297;
                        }
                        concurrentStringForMove = this.getConcurrentStringForMove(sep, piece);
                        b2 = b;
                    }
                    final boolean b5 = false;
                    s = concurrentStringForMove;
                    b4 = b2;
                    piece2 = piece;
                    b3 = b5;
                }
                String s2;
                if (piece2 instanceof King && Math.abs(startSquare.getColumn() - endSquare.getColumn()) > 1) {
                    if (endSquare.getColumn() == 2) {
                        final Square instanceForRowAndColumn = Square.instanceForRowAndColumn(endSquare.getRow(), 0);
                        final Piece piece3 = this.getPieceFor(instanceForRowAndColumn);
                        this.setPiece(null, instanceForRowAndColumn);
                        this.setPiece(piece3, Square.instanceForRowAndColumn(endSquare.getRow(), 3));
                        s2 = "0-0-0";
                    }
                    else {
                        final Square instanceForRowAndColumn2 = Square.instanceForRowAndColumn(endSquare.getRow(), 7);
                        final Piece piece4 = this.getPieceFor(instanceForRowAndColumn2);
                        this.setPiece(null, instanceForRowAndColumn2);
                        this.setPiece(piece4, Square.instanceForRowAndColumn(endSquare.getRow(), 5));
                        s2 = "0-0";
                    }
                }
                else {
                    s2 = null;
                }
                this.setPiece(piece2, endSquare);
                this.setPiece(null, startSquare);
                final boolean activeColor2 = this._activeColor;
                final boolean b6 = true;
                this._activeColor = (activeColor2 ^ true);
                ++this._moveCounter;
                if (piece2.isWhite()) {
                    sb.append(this.getMoveNumber());
                    sb.append('.');
                }
                int n2;
                if ((n2 = n) != 0) {
                    sb.append(this.getMoveNumber());
                    sb.append("... ");
                    n2 = 0;
                }
                if (s2 == null) {
                    if (!b3) {
                        sb.append(piece2.getPieceFigurineNotation());
                    }
                    sb.append(s);
                    if (b4) {
                        sb.append('x');
                    }
                    sb.append(endSquare);
                }
                else {
                    sb.append(s2);
                }
                if (b3) {
                    sb.append(piece2.getPieceFigurineNotation());
                }
                if (this.isCheck()) {
                    sb.append('+');
                }
                sb.append(" ");
                n = n2;
                b = b6;
            }
        }
        return sb.toString();
    }
}

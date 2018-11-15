/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.chess.model.position;

import de.cisha.chess.model.Piece;
import de.cisha.chess.model.SEP;
import de.cisha.chess.model.Square;
import de.cisha.chess.model.pieces.King;
import de.cisha.chess.model.pieces.Pawn;
import de.cisha.chess.model.position.AbstractPosition;
import de.cisha.chess.model.position.MutablePosition;
import de.cisha.chess.model.position.Position;

public class FastMoveStringGeneratingPosition
extends MutablePosition {
    private static final long serialVersionUID = -7182471444896727287L;

    public FastMoveStringGeneratingPosition(Position position) {
        super(position);
    }

    /*
     * Enabled aggressive block sorting
     */
    public String convertCANToFAN(String[] arrstring) {
        StringBuilder stringBuilder = new StringBuilder();
        boolean bl = this.getActiveColor();
        boolean bl2 = true;
        int n = arrstring.length;
        boolean bl3 = bl ^ true;
        int n2 = 0;
        do {
            block17 : {
                Object object;
                Piece piece;
                boolean bl4;
                Object object2;
                Square square;
                Object object3;
                boolean bl5;
                Square square2;
                block22 : {
                    block21 : {
                        block18 : {
                            block20 : {
                                block19 : {
                                    if (n2 >= n) {
                                        return stringBuilder.toString();
                                    }
                                    object = new SEP(arrstring[n2]);
                                    square = object.getStartSquare();
                                    square2 = object.getEndSquare();
                                    piece = this.getPieceFor(square);
                                    if (piece == null) break block17;
                                    if (this.getPieceFor(square2) == null) {
                                        bl2 = false;
                                    }
                                    object3 = "";
                                    if (!(piece instanceof Pawn)) break block18;
                                    if (square.getColumn() != square2.getColumn()) {
                                        object3 = new StringBuilder();
                                        object3.append("");
                                        object3.append(square.getFile());
                                        object3 = object3.toString();
                                        if (this.isEmpty(square2)) {
                                            this.setPiece(null, Square.instanceForRowAndColumn(square.getRow(), square2.getColumn()));
                                        }
                                        bl2 = true;
                                    }
                                    if (piece != Pawn.WHITE || square2.getRow() != 7) break block19;
                                    object2 = this.getPromotionPiece(object.getPromotionPiece(), true);
                                    break block20;
                                }
                                bl5 = bl2;
                                object2 = object3;
                                if (piece != Pawn.BLACK) break block21;
                                bl5 = bl2;
                                object2 = object3;
                                if (square2.getRow() != 0) break block21;
                                object2 = this.getPromotionPiece(object.getPromotionPiece(), false);
                            }
                            bl5 = true;
                            bl4 = bl2;
                            object = object3;
                            break block22;
                        }
                        object2 = this.getConcurrentStringForMove((SEP)object, piece);
                        bl5 = bl2;
                    }
                    bl2 = false;
                    object = object2;
                    bl4 = bl5;
                    object2 = piece;
                    bl5 = bl2;
                }
                if (object2 instanceof King && Math.abs(square.getColumn() - square2.getColumn()) > 1) {
                    if (square2.getColumn() == 2) {
                        object3 = Square.instanceForRowAndColumn(square2.getRow(), 0);
                        piece = this.getPieceFor((Square)object3);
                        this.setPiece(null, (Square)object3);
                        this.setPiece(piece, Square.instanceForRowAndColumn(square2.getRow(), 3));
                        object3 = "0-0-0";
                    } else {
                        object3 = Square.instanceForRowAndColumn(square2.getRow(), 7);
                        piece = this.getPieceFor((Square)object3);
                        this.setPiece(null, (Square)object3);
                        this.setPiece(piece, Square.instanceForRowAndColumn(square2.getRow(), 5));
                        object3 = "0-0";
                    }
                } else {
                    object3 = null;
                }
                this.setPiece((Piece)object2, square2);
                this.setPiece(null, square);
                bl = this._activeColor;
                boolean bl6 = true;
                this._activeColor = bl ^ true;
                ++this._moveCounter;
                if (object2.isWhite()) {
                    stringBuilder.append(this.getMoveNumber());
                    stringBuilder.append('.');
                }
                bl2 = bl3;
                if (bl3) {
                    stringBuilder.append(this.getMoveNumber());
                    stringBuilder.append("... ");
                    bl2 = false;
                }
                if (object3 == null) {
                    if (!bl5) {
                        stringBuilder.append(object2.getPieceFigurineNotation());
                    }
                    stringBuilder.append((String)object);
                    if (bl4) {
                        stringBuilder.append('x');
                    }
                    stringBuilder.append(square2);
                } else {
                    stringBuilder.append((String)object3);
                }
                if (bl5) {
                    stringBuilder.append(object2.getPieceFigurineNotation());
                }
                if (this.isCheck()) {
                    stringBuilder.append('+');
                }
                stringBuilder.append(" ");
                bl3 = bl2;
                bl2 = bl6;
            }
            ++n2;
        } while (true);
    }
}

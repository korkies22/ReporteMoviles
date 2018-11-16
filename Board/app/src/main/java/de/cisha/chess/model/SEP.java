// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.chess.model;

import de.cisha.chess.util.FENHelper;
import java.io.Serializable;

public class SEP implements Serializable
{
    public static final int PROMOTION_NO_PROMOTION = 0;
    public static final int PROMOTION_TO_BISHOP = 2;
    public static final int PROMOTION_TO_KNIGHT = 1;
    public static final int PROMOTION_TO_QUEEN = 3;
    public static final int PROMOTION_TO_ROOK = 0;
    private static final long serialVersionUID = 976051306292172683L;
    short _information;
    
    public SEP(final Square square, final Square square2) {
        this(square, square2, 0);
    }
    
    public SEP(final Square square, final Square square2, final int n) {
        this.computeSEPFromSquare(square, square2, n);
    }
    
    public SEP(final String s) {
        if (s != null && (s.length() == 4 || s.length() == 5)) {
            int promotionTypeForFENChar = 0;
            final Square instanceForFileAndRank = Square.instanceForFileAndRank(s.charAt(0), this.createIntFromChar(s.charAt(1)));
            final Square instanceForFileAndRank2 = Square.instanceForFileAndRank(s.charAt(2), this.createIntFromChar(s.charAt(3)));
            if (s.length() == 5) {
                promotionTypeForFENChar = FENHelper.getPromotionTypeForFENChar(s.charAt(4));
            }
            this.computeSEPFromSquare(instanceForFileAndRank, instanceForFileAndRank2, promotionTypeForFENChar);
        }
    }
    
    public SEP(final short information) {
        this._information = information;
    }
    
    private void computeSEPFromSquare(final Square square, final Square square2, final int n) {
        if (square != null && square2 != null) {
            this._information = (short)(4096 * n + 512 * square.getRow() + 64 * square.getColumn() + 8 * square2.getRow() + square2.getColumn());
            return;
        }
        this._information = 0;
    }
    
    private int createIntFromChar(final char c) {
        return '\u0001' + c - '1';
    }
    
    @Override
    public boolean equals(final Object o) {
        boolean b = false;
        if (o != null && o instanceof SEP) {
            if (this.getSEPNumber() == ((SEP)o).getSEPNumber()) {
                b = true;
            }
            return b;
        }
        return false;
    }
    
    public Square getEndSquare() {
        return Square.instanceForRowAndColumn(this._information / 8 % 8, this._information % 8);
    }
    
    public int getPromotionPiece() {
        return this._information / 4096;
    }
    
    public short getSEPNumber() {
        return this._information;
    }
    
    public Square getStartSquare() {
        return Square.instanceForRowAndColumn(this._information / 512 % 8, this._information / 64 % 8);
    }
    
    @Override
    public int hashCode() {
        return this.getSEPNumber();
    }
    
    public boolean isNullMove() {
        return this._information == 0;
    }
    
    public boolean isValid() {
        return this._information >= 0 && this._information < 16384;
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("");
        sb.append(this._information);
        sb.append("(");
        sb.append(this.getStartSquare().toString());
        sb.append("-");
        sb.append(this.getEndSquare().toString());
        sb.append(" Promotion: ");
        sb.append(this.getPromotionPiece());
        sb.append(")");
        return sb.toString();
    }
}

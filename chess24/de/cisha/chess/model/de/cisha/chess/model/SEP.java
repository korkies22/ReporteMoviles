/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.chess.model;

import de.cisha.chess.model.Square;
import de.cisha.chess.util.FENHelper;
import java.io.Serializable;

public class SEP
implements Serializable {
    public static final int PROMOTION_NO_PROMOTION = 0;
    public static final int PROMOTION_TO_BISHOP = 2;
    public static final int PROMOTION_TO_KNIGHT = 1;
    public static final int PROMOTION_TO_QUEEN = 3;
    public static final int PROMOTION_TO_ROOK = 0;
    private static final long serialVersionUID = 976051306292172683L;
    short _information;

    public SEP(Square square, Square square2) {
        this(square, square2, 0);
    }

    public SEP(Square square, Square square2, int n) {
        this.computeSEPFromSquare(square, square2, n);
    }

    public SEP(String string) {
        if (string != null && (string.length() == 4 || string.length() == 5)) {
            int n = 0;
            Square square = Square.instanceForFileAndRank(string.charAt(0), this.createIntFromChar(string.charAt(1)));
            Square square2 = Square.instanceForFileAndRank(string.charAt(2), this.createIntFromChar(string.charAt(3)));
            if (string.length() == 5) {
                n = FENHelper.getPromotionTypeForFENChar(string.charAt(4));
            }
            this.computeSEPFromSquare(square, square2, n);
        }
    }

    public SEP(short s) {
        this._information = s;
    }

    private void computeSEPFromSquare(Square square, Square square2, int n) {
        if (square != null && square2 != null) {
            this._information = (short)(4096 * n + 512 * square.getRow() + 64 * square.getColumn() + 8 * square2.getRow() + square2.getColumn());
            return;
        }
        this._information = 0;
    }

    private int createIntFromChar(char c) {
        return '\u0001' + c - 49;
    }

    public boolean equals(Object object) {
        boolean bl = false;
        if (object != null && object instanceof SEP) {
            if (this.getSEPNumber() == ((SEP)object).getSEPNumber()) {
                bl = true;
            }
            return bl;
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

    public int hashCode() {
        return this.getSEPNumber();
    }

    public boolean isNullMove() {
        if (this._information == 0) {
            return true;
        }
        return false;
    }

    public boolean isValid() {
        if (this._information >= 0 && this._information < 16384) {
            return true;
        }
        return false;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("");
        stringBuilder.append(this._information);
        stringBuilder.append("(");
        stringBuilder.append(this.getStartSquare().toString());
        stringBuilder.append("-");
        stringBuilder.append(this.getEndSquare().toString());
        stringBuilder.append(" Promotion: ");
        stringBuilder.append(this.getPromotionPiece());
        stringBuilder.append(")");
        return stringBuilder.toString();
    }
}

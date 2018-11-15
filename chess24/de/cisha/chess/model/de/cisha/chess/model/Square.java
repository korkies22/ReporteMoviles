/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.chess.model;

import java.io.Serializable;

public class Square
implements Serializable {
    private static final Square[][] ALL_SQUARES_12x12;
    private static final Square[] ALL_SQUARES_64x1;
    private static final Square[][] ALL_SQUARES_8x8;
    public static final int CHESS_FILE_A = 0;
    public static final int CHESS_FILE_B = 1;
    public static final int CHESS_FILE_C = 2;
    public static final int CHESS_FILE_COUNT = 8;
    public static final int CHESS_FILE_D = 3;
    public static final int CHESS_FILE_E = 4;
    public static final int CHESS_FILE_F = 5;
    public static final int CHESS_FILE_G = 6;
    public static final int CHESS_FILE_H = 7;
    public static final int CHESS_RANK_1 = 0;
    public static final int CHESS_RANK_2 = 1;
    public static final int CHESS_RANK_3 = 2;
    public static final int CHESS_RANK_4 = 3;
    public static final int CHESS_RANK_5 = 4;
    public static final int CHESS_RANK_6 = 5;
    public static final int CHESS_RANK_7 = 6;
    public static final int CHESS_RANK_8 = 7;
    public static final int CHESS_RANK_COUNT = 8;
    public static final Square SQUARE_A1;
    public static final Square SQUARE_A2;
    public static final Square SQUARE_A3;
    public static final Square SQUARE_A4;
    public static final Square SQUARE_A5;
    public static final Square SQUARE_A6;
    public static final Square SQUARE_A7;
    public static final Square SQUARE_A8;
    public static final Square SQUARE_B1;
    public static final Square SQUARE_B2;
    public static final Square SQUARE_B3;
    public static final Square SQUARE_B4;
    public static final Square SQUARE_B5;
    public static final Square SQUARE_B6;
    public static final Square SQUARE_B7;
    public static final Square SQUARE_B8;
    public static final Square SQUARE_C1;
    public static final Square SQUARE_C2;
    public static final Square SQUARE_C3;
    public static final Square SQUARE_C4;
    public static final Square SQUARE_C5;
    public static final Square SQUARE_C6;
    public static final Square SQUARE_C7;
    public static final Square SQUARE_C8;
    public static final Square SQUARE_D1;
    public static final Square SQUARE_D2;
    public static final Square SQUARE_D3;
    public static final Square SQUARE_D4;
    public static final Square SQUARE_D5;
    public static final Square SQUARE_D6;
    public static final Square SQUARE_D7;
    public static final Square SQUARE_D8;
    public static final Square SQUARE_E1;
    public static final Square SQUARE_E2;
    public static final Square SQUARE_E3;
    public static final Square SQUARE_E4;
    public static final Square SQUARE_E5;
    public static final Square SQUARE_E6;
    public static final Square SQUARE_E7;
    public static final Square SQUARE_E8;
    public static final Square SQUARE_F1;
    public static final Square SQUARE_F2;
    public static final Square SQUARE_F3;
    public static final Square SQUARE_F4;
    public static final Square SQUARE_F5;
    public static final Square SQUARE_F6;
    public static final Square SQUARE_F7;
    public static final Square SQUARE_F8;
    public static final Square SQUARE_G1;
    public static final Square SQUARE_G2;
    public static final Square SQUARE_G3;
    public static final Square SQUARE_G4;
    public static final Square SQUARE_G5;
    public static final Square SQUARE_G6;
    public static final Square SQUARE_G7;
    public static final Square SQUARE_G8;
    public static final Square SQUARE_H1;
    public static final Square SQUARE_H2;
    public static final Square SQUARE_H3;
    public static final Square SQUARE_H4;
    public static final Square SQUARE_H5;
    public static final Square SQUARE_H6;
    public static final Square SQUARE_H7;
    public static final Square SQUARE_H8;
    private static char[] _columnNames;
    private static final long serialVersionUID = -3850436768536597186L;
    private int _column;
    private boolean _isValid;
    private int _row;

    static {
        SQUARE_A1 = new Square('a', 1);
        SQUARE_A2 = new Square('a', 2);
        SQUARE_A3 = new Square('a', 3);
        SQUARE_A4 = new Square('a', 4);
        SQUARE_A5 = new Square('a', 5);
        SQUARE_A6 = new Square('a', 6);
        SQUARE_A7 = new Square('a', 7);
        SQUARE_A8 = new Square('a', 8);
        SQUARE_B1 = new Square('b', 1);
        SQUARE_B2 = new Square('b', 2);
        SQUARE_B3 = new Square('b', 3);
        SQUARE_B4 = new Square('b', 4);
        SQUARE_B5 = new Square('b', 5);
        SQUARE_B6 = new Square('b', 6);
        SQUARE_B7 = new Square('b', 7);
        SQUARE_B8 = new Square('b', 8);
        SQUARE_C1 = new Square('c', 1);
        SQUARE_C2 = new Square('c', 2);
        SQUARE_C3 = new Square('c', 3);
        SQUARE_C4 = new Square('c', 4);
        SQUARE_C5 = new Square('c', 5);
        SQUARE_C6 = new Square('c', 6);
        SQUARE_C7 = new Square('c', 7);
        SQUARE_C8 = new Square('c', 8);
        SQUARE_D1 = new Square('d', 1);
        SQUARE_D2 = new Square('d', 2);
        SQUARE_D3 = new Square('d', 3);
        SQUARE_D4 = new Square('d', 4);
        SQUARE_D5 = new Square('d', 5);
        SQUARE_D6 = new Square('d', 6);
        SQUARE_D7 = new Square('d', 7);
        SQUARE_D8 = new Square('d', 8);
        SQUARE_E1 = new Square('e', 1);
        SQUARE_E2 = new Square('e', 2);
        SQUARE_E3 = new Square('e', 3);
        SQUARE_E4 = new Square('e', 4);
        SQUARE_E5 = new Square('e', 5);
        SQUARE_E6 = new Square('e', 6);
        SQUARE_E7 = new Square('e', 7);
        SQUARE_E8 = new Square('e', 8);
        SQUARE_F1 = new Square('f', 1);
        SQUARE_F2 = new Square('f', 2);
        SQUARE_F3 = new Square('f', 3);
        SQUARE_F4 = new Square('f', 4);
        SQUARE_F5 = new Square('f', 5);
        SQUARE_F6 = new Square('f', 6);
        SQUARE_F7 = new Square('f', 7);
        SQUARE_F8 = new Square('f', 8);
        SQUARE_G1 = new Square('g', 1);
        SQUARE_G2 = new Square('g', 2);
        SQUARE_G3 = new Square('g', 3);
        SQUARE_G4 = new Square('g', 4);
        SQUARE_G5 = new Square('g', 5);
        SQUARE_G6 = new Square('g', 6);
        SQUARE_G7 = new Square('g', 7);
        SQUARE_G8 = new Square('g', 8);
        SQUARE_H1 = new Square('h', 1);
        SQUARE_H2 = new Square('h', 2);
        SQUARE_H3 = new Square('h', 3);
        SQUARE_H4 = new Square('h', 4);
        SQUARE_H5 = new Square('h', 5);
        SQUARE_H6 = new Square('h', 6);
        SQUARE_H7 = new Square('h', 7);
        SQUARE_H8 = new Square('h', 8);
        ALL_SQUARES_64x1 = new Square[]{SQUARE_A1, SQUARE_A2, SQUARE_A3, SQUARE_A4, SQUARE_A5, SQUARE_A6, SQUARE_A7, SQUARE_A8, SQUARE_B1, SQUARE_B2, SQUARE_B3, SQUARE_B4, SQUARE_B5, SQUARE_B6, SQUARE_B7, SQUARE_B8, SQUARE_C1, SQUARE_C2, SQUARE_C3, SQUARE_C4, SQUARE_C5, SQUARE_C6, SQUARE_C7, SQUARE_C8, SQUARE_D1, SQUARE_D2, SQUARE_D3, SQUARE_D4, SQUARE_D5, SQUARE_D6, SQUARE_D7, SQUARE_D8, SQUARE_E1, SQUARE_E2, SQUARE_E3, SQUARE_E4, SQUARE_E5, SQUARE_E6, SQUARE_E7, SQUARE_E8, SQUARE_F1, SQUARE_F2, SQUARE_F3, SQUARE_F4, SQUARE_F5, SQUARE_F6, SQUARE_F7, SQUARE_F8, SQUARE_G1, SQUARE_G2, SQUARE_G3, SQUARE_G4, SQUARE_G5, SQUARE_G6, SQUARE_G7, SQUARE_G8, SQUARE_H1, SQUARE_H2, SQUARE_H3, SQUARE_H4, SQUARE_H5, SQUARE_H6, SQUARE_H7, SQUARE_H8};
        Object object = new Square[]{SQUARE_A1, SQUARE_A2, SQUARE_A3, SQUARE_A4, SQUARE_A5, SQUARE_A6, SQUARE_A7, SQUARE_A8};
        Square square = SQUARE_B1;
        Square square2 = SQUARE_B2;
        Square square3 = SQUARE_B3;
        Square square4 = SQUARE_B4;
        Square square5 = SQUARE_B5;
        Square square6 = SQUARE_B6;
        Square square7 = SQUARE_B7;
        Square square8 = SQUARE_B8;
        Object object2 = new Square[]{SQUARE_C1, SQUARE_C2, SQUARE_C3, SQUARE_C4, SQUARE_C5, SQUARE_C6, SQUARE_C7, SQUARE_C8};
        Square square9 = SQUARE_D1;
        Square square10 = SQUARE_D2;
        Square square11 = SQUARE_D3;
        Square square12 = SQUARE_D4;
        Square square13 = SQUARE_D5;
        Square square14 = SQUARE_D6;
        Square square15 = SQUARE_D7;
        Square square16 = SQUARE_D8;
        Object object3 = new Square[]{SQUARE_E1, SQUARE_E2, SQUARE_E3, SQUARE_E4, SQUARE_E5, SQUARE_E6, SQUARE_E7, SQUARE_E8};
        Object object4 = new Square[]{SQUARE_F1, SQUARE_F2, SQUARE_F3, SQUARE_F4, SQUARE_F5, SQUARE_F6, SQUARE_F7, SQUARE_F8};
        Square square17 = SQUARE_G1;
        Square square18 = SQUARE_G2;
        Square square19 = SQUARE_G3;
        Square square20 = SQUARE_G4;
        Square square21 = SQUARE_G5;
        Square square22 = SQUARE_G6;
        Square square23 = SQUARE_G7;
        Square square24 = SQUARE_G8;
        Object object5 = new Square[]{SQUARE_H1, SQUARE_H2, SQUARE_H3, SQUARE_H4, SQUARE_H5, SQUARE_H6, SQUARE_H7, SQUARE_H8};
        ALL_SQUARES_8x8 = new Square[][]{object, {square, square2, square3, square4, square5, square6, square7, square8}, object2, {square9, square10, square11, square12, square13, square14, square15, square16}, object3, object4, {square17, square18, square19, square20, square21, square22, square23, square24}, object5};
        Square[] arrsquare = new Square[]{new Square(-2, -2), new Square(-2, -1), new Square(-2, 0), new Square(-2, 1), new Square(-2, 2), new Square(-2, 3), new Square(-2, 4), new Square(-2, 5), new Square(-2, 6), new Square(-2, 7), new Square(-2, 8), new Square(-2, 9)};
        Square[] arrsquare2 = new Square[]{new Square(-1, -2), new Square(-1, -1), new Square(-1, 0), new Square(-1, 1), new Square(-1, 2), new Square(-1, 3), new Square(-1, 4), new Square(-1, 5), new Square(-1, 6), new Square(-1, 7), new Square(-1, 8), new Square(-1, 9)};
        square = new Square(0, -2);
        square2 = new Square(0, -1);
        square3 = SQUARE_A1;
        square4 = SQUARE_A2;
        square5 = SQUARE_A3;
        square6 = SQUARE_A4;
        square7 = SQUARE_A5;
        square8 = SQUARE_A6;
        square9 = SQUARE_A7;
        square10 = SQUARE_A8;
        square11 = new Square(0, 8);
        square12 = new Square(0, 9);
        Square[] arrsquare3 = new Square[]{new Square(1, -2), new Square(1, -1), SQUARE_B1, SQUARE_B2, SQUARE_B3, SQUARE_B4, SQUARE_B5, SQUARE_B6, SQUARE_B7, SQUARE_B8, new Square(1, 8), new Square(1, 9)};
        Square[] arrsquare4 = new Square[]{new Square(2, -2), new Square(2, -1), SQUARE_C1, SQUARE_C2, SQUARE_C3, SQUARE_C4, SQUARE_C5, SQUARE_C6, SQUARE_C7, SQUARE_C8, new Square(2, 8), new Square(2, 9)};
        Square[] arrsquare5 = new Square[]{new Square(3, -2), new Square(3, -1), SQUARE_D1, SQUARE_D2, SQUARE_D3, SQUARE_D4, SQUARE_D5, SQUARE_D6, SQUARE_D7, SQUARE_D8, new Square(3, 8), new Square(3, 9)};
        square13 = new Square(4, -2);
        square14 = new Square(4, -1);
        square15 = SQUARE_E1;
        square16 = SQUARE_E2;
        square17 = SQUARE_E3;
        square18 = SQUARE_E4;
        square19 = SQUARE_E5;
        square20 = SQUARE_E6;
        square21 = SQUARE_E7;
        square22 = SQUARE_E8;
        square23 = new Square(4, 8);
        square24 = new Square(4, 9);
        object = new Square(5, -2);
        object2 = new Square(5, -1);
        object3 = SQUARE_F1;
        object4 = SQUARE_F2;
        object5 = SQUARE_F3;
        Square square25 = SQUARE_F4;
        Square square26 = SQUARE_F5;
        Square square27 = SQUARE_F6;
        Square square28 = SQUARE_F7;
        Square square29 = SQUARE_F8;
        Square square30 = new Square(5, 8);
        Square square31 = new Square(5, 9);
        Square square32 = new Square(6, -2);
        Square square33 = new Square(6, -1);
        Square square34 = SQUARE_G1;
        Square square35 = SQUARE_G2;
        Square square36 = SQUARE_G3;
        Square square37 = SQUARE_G4;
        Square square38 = SQUARE_G5;
        Square square39 = SQUARE_G6;
        Square square40 = SQUARE_G7;
        Square square41 = SQUARE_G8;
        Square square42 = new Square(6, 8);
        Square square43 = new Square(6, 9);
        Square[] arrsquare6 = new Square[]{new Square(7, -2), new Square(7, -1), SQUARE_H1, SQUARE_H2, SQUARE_H3, SQUARE_H4, SQUARE_H5, SQUARE_H6, SQUARE_H7, SQUARE_H8, new Square(7, 8), new Square(7, 9)};
        Square square44 = new Square(8, -2);
        Square square45 = new Square(8, -1);
        Square square46 = new Square(8, 0);
        Square square47 = new Square(8, 1);
        Square square48 = new Square(8, 2);
        Square square49 = new Square(8, 3);
        Square square50 = new Square(8, 4);
        Square square51 = new Square(8, 5);
        Square square52 = new Square(8, 6);
        Square square53 = new Square(8, 7);
        Square square54 = new Square(8, 8);
        Square square55 = new Square(8, 9);
        Square square56 = new Square(9, -2);
        Square square57 = new Square(9, -1);
        Square square58 = new Square(9, 0);
        Square square59 = new Square(9, 1);
        Square square60 = new Square(9, 2);
        Square square61 = new Square(9, 3);
        Square square62 = new Square(9, 4);
        Square square63 = new Square(9, 5);
        Square square64 = new Square(9, 6);
        Square square65 = new Square(9, 7);
        Square square66 = new Square(9, 8);
        Square square67 = new Square(9, 9);
        ALL_SQUARES_12x12 = new Square[][]{arrsquare, arrsquare2, {square, square2, square3, square4, square5, square6, square7, square8, square9, square10, square11, square12}, arrsquare3, arrsquare4, arrsquare5, {square13, square14, square15, square16, square17, square18, square19, square20, square21, square22, square23, square24}, {object, object2, object3, object4, object5, square25, square26, square27, square28, square29, square30, square31}, {square32, square33, square34, square35, square36, square37, square38, square39, square40, square41, square42, square43}, arrsquare6, {square44, square45, square46, square47, square48, square49, square50, square51, square52, square53, square54, square55}, {square56, square57, square58, square59, square60, square61, square62, square63, square64, square65, square66, square67}};
        _columnNames = new char[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h'};
    }

    private Square(char c, int n) {
        this._row = n - 1;
        this._column = c - 97;
        this.checkValidity();
    }

    private Square(int n, int n2) {
        this._row = n;
        this._column = n2;
        this.checkValidity();
    }

    private void checkValidity() {
        boolean bl = this._row >= 0 && this._column >= 0 && this._row < 8 && this._column < 8;
        this._isValid = bl;
    }

    public static Square[] getAllSquares64() {
        return ALL_SQUARES_64x1;
    }

    public static Square[][] getAllSquares8x8() {
        return ALL_SQUARES_8x8;
    }

    public static Square instanceForFileAndRank(char c, int n) {
        return Square.instanceForRowAndColumn(n - 1, c - 97);
    }

    public static Square instanceForNumber(int n) {
        return Square.instanceForRowAndColumn(n / 8, n % 8);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static Square instanceForRowAndColumn(int n, int n2) {
        try {
            return ALL_SQUARES_12x12[n2 + 2][n + 2];
        }
        catch (Exception exception) {
            return new Square(n, n2);
        }
    }

    public boolean equals(Object object) {
        boolean bl = object instanceof Square;
        boolean bl2 = false;
        if (bl) {
            object = (Square)object;
            bl = bl2;
            if (object._column == this._column) {
                bl = bl2;
                if (object._row == this._row) {
                    bl = true;
                }
            }
            return bl;
        }
        return false;
    }

    public int getColumn() {
        return this._column;
    }

    public char getFile() {
        return (char)(this._column + 97);
    }

    public String getName() {
        if (this._column < 8) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("");
            stringBuilder.append(_columnNames[this._column]);
            stringBuilder.append(this._row + 1);
            return stringBuilder.toString();
        }
        return "";
    }

    public int getRank() {
        return this._row + 1;
    }

    public int getRow() {
        return this._row;
    }

    public int hashCode() {
        return 34523 * this._row + this._column;
    }

    public boolean isBlack() {
        return this.isWhite() ^ true;
    }

    public boolean isValid() {
        return this._isValid;
    }

    public boolean isWhite() {
        if ((this._column + this._row) % 2 == 1) {
            return true;
        }
        return false;
    }

    public String toString() {
        if (this.isValid()) {
            return this.getName();
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("");
        stringBuilder.append(this._row);
        stringBuilder.append("/");
        stringBuilder.append(this._column);
        return stringBuilder.toString();
    }
}

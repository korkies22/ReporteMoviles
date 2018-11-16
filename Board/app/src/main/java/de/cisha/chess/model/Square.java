// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.chess.model;

import java.io.Serializable;

public class Square implements Serializable
{
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
        ALL_SQUARES_64x1 = new Square[] { Square.SQUARE_A1, Square.SQUARE_A2, Square.SQUARE_A3, Square.SQUARE_A4, Square.SQUARE_A5, Square.SQUARE_A6, Square.SQUARE_A7, Square.SQUARE_A8, Square.SQUARE_B1, Square.SQUARE_B2, Square.SQUARE_B3, Square.SQUARE_B4, Square.SQUARE_B5, Square.SQUARE_B6, Square.SQUARE_B7, Square.SQUARE_B8, Square.SQUARE_C1, Square.SQUARE_C2, Square.SQUARE_C3, Square.SQUARE_C4, Square.SQUARE_C5, Square.SQUARE_C6, Square.SQUARE_C7, Square.SQUARE_C8, Square.SQUARE_D1, Square.SQUARE_D2, Square.SQUARE_D3, Square.SQUARE_D4, Square.SQUARE_D5, Square.SQUARE_D6, Square.SQUARE_D7, Square.SQUARE_D8, Square.SQUARE_E1, Square.SQUARE_E2, Square.SQUARE_E3, Square.SQUARE_E4, Square.SQUARE_E5, Square.SQUARE_E6, Square.SQUARE_E7, Square.SQUARE_E8, Square.SQUARE_F1, Square.SQUARE_F2, Square.SQUARE_F3, Square.SQUARE_F4, Square.SQUARE_F5, Square.SQUARE_F6, Square.SQUARE_F7, Square.SQUARE_F8, Square.SQUARE_G1, Square.SQUARE_G2, Square.SQUARE_G3, Square.SQUARE_G4, Square.SQUARE_G5, Square.SQUARE_G6, Square.SQUARE_G7, Square.SQUARE_G8, Square.SQUARE_H1, Square.SQUARE_H2, Square.SQUARE_H3, Square.SQUARE_H4, Square.SQUARE_H5, Square.SQUARE_H6, Square.SQUARE_H7, Square.SQUARE_H8 };
        ALL_SQUARES_8x8 = new Square[][] { { Square.SQUARE_A1, Square.SQUARE_A2, Square.SQUARE_A3, Square.SQUARE_A4, Square.SQUARE_A5, Square.SQUARE_A6, Square.SQUARE_A7, Square.SQUARE_A8 }, { Square.SQUARE_B1, Square.SQUARE_B2, Square.SQUARE_B3, Square.SQUARE_B4, Square.SQUARE_B5, Square.SQUARE_B6, Square.SQUARE_B7, Square.SQUARE_B8 }, { Square.SQUARE_C1, Square.SQUARE_C2, Square.SQUARE_C3, Square.SQUARE_C4, Square.SQUARE_C5, Square.SQUARE_C6, Square.SQUARE_C7, Square.SQUARE_C8 }, { Square.SQUARE_D1, Square.SQUARE_D2, Square.SQUARE_D3, Square.SQUARE_D4, Square.SQUARE_D5, Square.SQUARE_D6, Square.SQUARE_D7, Square.SQUARE_D8 }, { Square.SQUARE_E1, Square.SQUARE_E2, Square.SQUARE_E3, Square.SQUARE_E4, Square.SQUARE_E5, Square.SQUARE_E6, Square.SQUARE_E7, Square.SQUARE_E8 }, { Square.SQUARE_F1, Square.SQUARE_F2, Square.SQUARE_F3, Square.SQUARE_F4, Square.SQUARE_F5, Square.SQUARE_F6, Square.SQUARE_F7, Square.SQUARE_F8 }, { Square.SQUARE_G1, Square.SQUARE_G2, Square.SQUARE_G3, Square.SQUARE_G4, Square.SQUARE_G5, Square.SQUARE_G6, Square.SQUARE_G7, Square.SQUARE_G8 }, { Square.SQUARE_H1, Square.SQUARE_H2, Square.SQUARE_H3, Square.SQUARE_H4, Square.SQUARE_H5, Square.SQUARE_H6, Square.SQUARE_H7, Square.SQUARE_H8 } };
        ALL_SQUARES_12x12 = new Square[][] { { new Square(-2, -2), new Square(-2, -1), new Square(-2, 0), new Square(-2, 1), new Square(-2, 2), new Square(-2, 3), new Square(-2, 4), new Square(-2, 5), new Square(-2, 6), new Square(-2, 7), new Square(-2, 8), new Square(-2, 9) }, { new Square(-1, -2), new Square(-1, -1), new Square(-1, 0), new Square(-1, 1), new Square(-1, 2), new Square(-1, 3), new Square(-1, 4), new Square(-1, 5), new Square(-1, 6), new Square(-1, 7), new Square(-1, 8), new Square(-1, 9) }, { new Square(0, -2), new Square(0, -1), Square.SQUARE_A1, Square.SQUARE_A2, Square.SQUARE_A3, Square.SQUARE_A4, Square.SQUARE_A5, Square.SQUARE_A6, Square.SQUARE_A7, Square.SQUARE_A8, new Square(0, 8), new Square(0, 9) }, { new Square(1, -2), new Square(1, -1), Square.SQUARE_B1, Square.SQUARE_B2, Square.SQUARE_B3, Square.SQUARE_B4, Square.SQUARE_B5, Square.SQUARE_B6, Square.SQUARE_B7, Square.SQUARE_B8, new Square(1, 8), new Square(1, 9) }, { new Square(2, -2), new Square(2, -1), Square.SQUARE_C1, Square.SQUARE_C2, Square.SQUARE_C3, Square.SQUARE_C4, Square.SQUARE_C5, Square.SQUARE_C6, Square.SQUARE_C7, Square.SQUARE_C8, new Square(2, 8), new Square(2, 9) }, { new Square(3, -2), new Square(3, -1), Square.SQUARE_D1, Square.SQUARE_D2, Square.SQUARE_D3, Square.SQUARE_D4, Square.SQUARE_D5, Square.SQUARE_D6, Square.SQUARE_D7, Square.SQUARE_D8, new Square(3, 8), new Square(3, 9) }, { new Square(4, -2), new Square(4, -1), Square.SQUARE_E1, Square.SQUARE_E2, Square.SQUARE_E3, Square.SQUARE_E4, Square.SQUARE_E5, Square.SQUARE_E6, Square.SQUARE_E7, Square.SQUARE_E8, new Square(4, 8), new Square(4, 9) }, { new Square(5, -2), new Square(5, -1), Square.SQUARE_F1, Square.SQUARE_F2, Square.SQUARE_F3, Square.SQUARE_F4, Square.SQUARE_F5, Square.SQUARE_F6, Square.SQUARE_F7, Square.SQUARE_F8, new Square(5, 8), new Square(5, 9) }, { new Square(6, -2), new Square(6, -1), Square.SQUARE_G1, Square.SQUARE_G2, Square.SQUARE_G3, Square.SQUARE_G4, Square.SQUARE_G5, Square.SQUARE_G6, Square.SQUARE_G7, Square.SQUARE_G8, new Square(6, 8), new Square(6, 9) }, { new Square(7, -2), new Square(7, -1), Square.SQUARE_H1, Square.SQUARE_H2, Square.SQUARE_H3, Square.SQUARE_H4, Square.SQUARE_H5, Square.SQUARE_H6, Square.SQUARE_H7, Square.SQUARE_H8, new Square(7, 8), new Square(7, 9) }, { new Square(8, -2), new Square(8, -1), new Square(8, 0), new Square(8, 1), new Square(8, 2), new Square(8, 3), new Square(8, 4), new Square(8, 5), new Square(8, 6), new Square(8, 7), new Square(8, 8), new Square(8, 9) }, { new Square(9, -2), new Square(9, -1), new Square(9, 0), new Square(9, 1), new Square(9, 2), new Square(9, 3), new Square(9, 4), new Square(9, 5), new Square(9, 6), new Square(9, 7), new Square(9, 8), new Square(9, 9) } };
        Square._columnNames = new char[] { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h' };
    }
    
    private Square(final char c, final int n) {
        this._row = n - 1;
        this._column = c - 'a';
        this.checkValidity();
    }
    
    private Square(final int row, final int column) {
        this._row = row;
        this._column = column;
        this.checkValidity();
    }
    
    private void checkValidity() {
        this._isValid = (this._row >= 0 && this._column >= 0 && this._row < 8 && this._column < 8);
    }
    
    public static Square[] getAllSquares64() {
        return Square.ALL_SQUARES_64x1;
    }
    
    public static Square[][] getAllSquares8x8() {
        return Square.ALL_SQUARES_8x8;
    }
    
    public static Square instanceForFileAndRank(final char c, final int n) {
        return instanceForRowAndColumn(n - 1, c - 'a');
    }
    
    public static Square instanceForNumber(final int n) {
        return instanceForRowAndColumn(n / 8, n % 8);
    }
    
    public static Square instanceForRowAndColumn(final int n, final int n2) {
        try {
            return Square.ALL_SQUARES_12x12[n2 + 2][n + 2];
        }
        catch (Exception ex) {
            return new Square(n, n2);
        }
    }
    
    @Override
    public boolean equals(final Object o) {
        final boolean b = o instanceof Square;
        final boolean b2 = false;
        if (b) {
            final Square square = (Square)o;
            boolean b3 = b2;
            if (square._column == this._column) {
                b3 = b2;
                if (square._row == this._row) {
                    b3 = true;
                }
            }
            return b3;
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
            final StringBuilder sb = new StringBuilder();
            sb.append("");
            sb.append(Square._columnNames[this._column]);
            sb.append(this._row + 1);
            return sb.toString();
        }
        return "";
    }
    
    public int getRank() {
        return this._row + 1;
    }
    
    public int getRow() {
        return this._row;
    }
    
    @Override
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
        return (this._column + this._row) % 2 == 1;
    }
    
    @Override
    public String toString() {
        if (this.isValid()) {
            return this.getName();
        }
        final StringBuilder sb = new StringBuilder();
        sb.append("");
        sb.append(this._row);
        sb.append("/");
        sb.append(this._column);
        return sb.toString();
    }
}

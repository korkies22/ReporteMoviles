/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.chess.model;

import java.io.Serializable;

public class MoveDirection
implements Serializable {
    public static final MoveDirection[] ALL_MOVE_DIRECTIONS;
    public static final MoveDirection DOWN;
    public static final MoveDirection DOWN_KNIGHT_LEFT;
    public static final MoveDirection DOWN_KNIGHT_RIGHT;
    public static final MoveDirection DOWN_LEFT;
    public static final MoveDirection DOWN_RIGHT;
    public static final MoveDirection LEFT;
    public static final MoveDirection LEFT_KNIGHT_DOWN;
    public static final MoveDirection LEFT_KNIGHT_UP;
    public static final MoveDirection RIGHT;
    public static final MoveDirection RIGHT_KNIGHT_DOWN;
    public static final MoveDirection RIGHT_KNIGHT_UP;
    public static final MoveDirection UP;
    public static final MoveDirection UP_KNIGHT_LEFT;
    public static final MoveDirection UP_KNIGHT_RIGHT;
    public static final MoveDirection UP_LEFT;
    public static final MoveDirection UP_RIGHT;
    private static final long serialVersionUID = -4880464488330359273L;
    private int _colDifference;
    private int _rowDifference;

    static {
        UP = new MoveDirection(1, 0);
        DOWN = new MoveDirection(-1, 0);
        LEFT = new MoveDirection(0, -1);
        RIGHT = new MoveDirection(0, 1);
        UP_RIGHT = new MoveDirection(1, 1);
        DOWN_RIGHT = new MoveDirection(-1, 1);
        UP_LEFT = new MoveDirection(1, -1);
        DOWN_LEFT = new MoveDirection(-1, -1);
        UP_KNIGHT_RIGHT = new MoveDirection(2, 1);
        RIGHT_KNIGHT_UP = new MoveDirection(1, 2);
        RIGHT_KNIGHT_DOWN = new MoveDirection(-1, 2);
        DOWN_KNIGHT_RIGHT = new MoveDirection(-2, 1);
        UP_KNIGHT_LEFT = new MoveDirection(2, -1);
        LEFT_KNIGHT_UP = new MoveDirection(1, -2);
        LEFT_KNIGHT_DOWN = new MoveDirection(-1, -2);
        DOWN_KNIGHT_LEFT = new MoveDirection(-2, -1);
        ALL_MOVE_DIRECTIONS = new MoveDirection[]{DOWN, RIGHT, UP, LEFT, DOWN_LEFT, DOWN_RIGHT, UP_LEFT, UP_RIGHT};
    }

    public MoveDirection(int n, int n2) {
        this._rowDifference = n;
        this._colDifference = n2;
    }

    public int getColDifference() {
        return this._colDifference;
    }

    public int getRowDifference() {
        return this._rowDifference;
    }

    public String toString() {
        CharSequence charSequence = new StringBuilder();
        charSequence.append("");
        charSequence.append(this._rowDifference);
        charSequence.append(" ");
        charSequence.append(this._colDifference);
        charSequence = charSequence.toString();
        switch (Math.abs(this._rowDifference) + Math.abs(this._colDifference)) {
            default: {
                return charSequence;
            }
            case 3: {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append((String)charSequence);
                stringBuilder.append(" springer");
                return stringBuilder.toString();
            }
            case 2: {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append((String)charSequence);
                stringBuilder.append(" diagonal");
                return stringBuilder.toString();
            }
            case 1: 
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append((String)charSequence);
        stringBuilder.append(" gerade");
        return stringBuilder.toString();
    }
}

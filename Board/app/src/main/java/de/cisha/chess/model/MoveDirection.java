// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.chess.model;

import java.io.Serializable;

public class MoveDirection implements Serializable
{
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
        ALL_MOVE_DIRECTIONS = new MoveDirection[] { MoveDirection.DOWN, MoveDirection.RIGHT, MoveDirection.UP, MoveDirection.LEFT, MoveDirection.DOWN_LEFT, MoveDirection.DOWN_RIGHT, MoveDirection.UP_LEFT, MoveDirection.UP_RIGHT };
    }
    
    public MoveDirection(final int rowDifference, final int colDifference) {
        this._rowDifference = rowDifference;
        this._colDifference = colDifference;
    }
    
    public int getColDifference() {
        return this._colDifference;
    }
    
    public int getRowDifference() {
        return this._rowDifference;
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("");
        sb.append(this._rowDifference);
        sb.append(" ");
        sb.append(this._colDifference);
        final String string = sb.toString();
        switch (Math.abs(this._rowDifference) + Math.abs(this._colDifference)) {
            default: {
                return string;
            }
            case 3: {
                final StringBuilder sb2 = new StringBuilder();
                sb2.append(string);
                sb2.append(" springer");
                return sb2.toString();
            }
            case 2: {
                final StringBuilder sb3 = new StringBuilder();
                sb3.append(string);
                sb3.append(" diagonal");
                return sb3.toString();
            }
            case 1: {
                final StringBuilder sb4 = new StringBuilder();
                sb4.append(string);
                sb4.append(" gerade");
                return sb4.toString();
            }
        }
    }
}

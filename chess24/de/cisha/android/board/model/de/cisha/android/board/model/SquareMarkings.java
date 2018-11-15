/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.model;

import de.cisha.chess.model.Square;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class SquareMarkings {
    private Map<Square, List<Integer>> _coloredSquares;

    public SquareMarkings() {
        this._coloredSquares = new HashMap<Square, List<Integer>>();
    }

    public SquareMarkings(SquareMarkings squareMarkings) {
        this._coloredSquares = new HashMap<Square, List<Integer>>(squareMarkings._coloredSquares);
    }

    public void addSquareMark(Square square, int n) {
        List<Integer> list;
        List<Integer> list2 = list = this._coloredSquares.get(square);
        if (list == null) {
            list2 = new LinkedList<Integer>();
            this._coloredSquares.put(square, list2);
        }
        list2.add(n);
    }

    public void clear() {
        this._coloredSquares.clear();
    }

    public List<Integer> getMarkingsForSquare(Square square) {
        return this._coloredSquares.get(square);
    }

    public void removeSquareMark(Square square) {
        this._coloredSquares.remove(square);
    }
}

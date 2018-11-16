// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.model;

import java.util.LinkedList;
import java.util.HashMap;
import java.util.List;
import de.cisha.chess.model.Square;
import java.util.Map;

public class SquareMarkings
{
    private Map<Square, List<Integer>> _coloredSquares;
    
    public SquareMarkings() {
        this._coloredSquares = new HashMap<Square, List<Integer>>();
    }
    
    public SquareMarkings(final SquareMarkings squareMarkings) {
        this._coloredSquares = new HashMap<Square, List<Integer>>(squareMarkings._coloredSquares);
    }
    
    public void addSquareMark(final Square square, final int n) {
        List<Integer> list;
        if ((list = this._coloredSquares.get(square)) == null) {
            list = new LinkedList<Integer>();
            this._coloredSquares.put(square, list);
        }
        list.add(n);
    }
    
    public void clear() {
        this._coloredSquares.clear();
    }
    
    public List<Integer> getMarkingsForSquare(final Square square) {
        return this._coloredSquares.get(square);
    }
    
    public void removeSquareMark(final Square square) {
        this._coloredSquares.remove(square);
    }
}

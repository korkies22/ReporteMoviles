// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.view;

import de.cisha.chess.model.Square;

public interface BoardMarkingDisplay
{
    void addArrowSquare(final Square p0, final Square p1, final int p2);
    
    void markSquare(final Square p0, final int p1);
    
    void removeAllArrows();
    
    void removeArrow(final Square p0, final Square p1);
    
    void removeArrowSquare(final Square p0, final Square p1, final int p2);
    
    void reset();
    
    void setUpdateArrows(final boolean p0);
    
    void unmarkField();
    
    void unmarkSquare(final Square p0);
}

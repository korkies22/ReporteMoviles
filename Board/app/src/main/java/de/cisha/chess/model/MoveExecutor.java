// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.chess.model;

public interface MoveExecutor extends MoveSelector
{
    boolean advanceOneMoveInCurrentLine();
    
    Move doMoveInCurrentPosition(final SEP p0);
    
    boolean goBackOneMove();
    
    void gotoEndingPosition();
    
    boolean gotoStartingPosition();
    
    void registerPremove(final Square p0, final Square p1);
}

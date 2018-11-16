// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.chess.model;

import de.cisha.chess.model.position.Position;
import java.util.List;

public interface MoveContainer
{
    void addMove(final Move p0);
    
    void addMoveInMainLine(final Move p0);
    
    boolean containsMove(final Move p0, final MoveContainer p1);
    
    List<Move> getAllMainLineMoves();
    
    Move getChild(final SEP p0);
    
    List<Move> getChildren();
    
    List<Move> getChildrenOfAllLevels();
    
    String getComment();
    
    Move getFirstMove();
    
    Game getGame();
    
    Move getLastMoveinMainLine();
    
    int getMoveId();
    
    int getMoveTimeInMills();
    
    Move getNextMove();
    
    MoveContainer getParent();
    
    Position getResultingPosition();
    
    String getSymbolMisc();
    
    String getSymbolMove();
    
    String getSymbolPosition();
    
    int getTotalTimeTakenInMills();
    
    int getVariationLevel();
    
    String getVariationPrefix();
    
    boolean hasChild(final SEP p0);
    
    boolean hasChildren();
    
    boolean hasSiblings();
    
    boolean hasVariants();
    
    boolean isMainLine();
    
    boolean isMainMove();
    
    void promoteMove(final Move p0);
    
    void removeChildAllMoves();
    
    void removeMove(final Move p0);
    
    void setComment(final String p0);
    
    void setMoveTimeInMills(final int p0);
    
    void setSymbolMisc(final String p0);
    
    void setSymbolMove(final String p0);
    
    void setSymbolPosition(final String p0);
}

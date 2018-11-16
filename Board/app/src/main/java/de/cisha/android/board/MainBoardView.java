// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board;

import de.cisha.chess.model.position.Position;
import java.util.List;
import de.cisha.chess.model.Move;
import de.cisha.chess.model.SEP;
import de.cisha.chess.model.Square;
import de.cisha.chess.model.MoveExecutor;

public interface MainBoardView extends MoveExecutor
{
    void addArrowSquare(final Square p0, final Square p1, final int p2);
    
    boolean addMoveInCurrentPosition(final SEP p0, final int p1, final int p2);
    
    boolean addMoveInMainLine(final SEP p0, final int p1, final int p2);
    
    void doMoveInCurrentPositionWithAnimation(final Move p0, final int p1);
    
    Move getMove(final int p0);
    
    List<Move> getMoveList();
    
    void markSquare(final Square p0, final int p1);
    
    void removeAllArrows();
    
    void setPosition(final Position p0);
    
    void setUpdateViews(final boolean p0);
    
    void setUserInteractionEnabled(final boolean p0);
    
    void startRemoteEngine(final String p0, final String p1, final String p2);
    
    void unmarkField();
    
    void unmarkSquare(final Square p0);
    
    void updateMoveList();
}

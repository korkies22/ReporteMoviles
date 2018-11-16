// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.view;

import de.cisha.chess.model.MoveSelector;
import de.cisha.chess.model.MovesObserver;

public interface MoveListView extends MovesObserver
{
    void setMoveSelector(final MoveSelector p0);
    
    void setNavigationSelectionEnabled(final boolean p0);
}

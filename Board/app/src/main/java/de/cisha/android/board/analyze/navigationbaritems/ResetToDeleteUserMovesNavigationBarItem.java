// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.analyze.navigationbaritems;

import de.cisha.chess.model.GameHolder;

public class ResetToDeleteUserMovesNavigationBarItem extends AbstractResetNavigationBarItem
{
    private GameHolder _gameHolder;
    
    public ResetToDeleteUserMovesNavigationBarItem(final GameHolder gameHolder) {
        this._gameHolder = gameHolder;
    }
    
    @Override
    public void handleClick() {
        this._gameHolder.deleteUserMoves();
    }
}

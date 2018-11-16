// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.analyze.navigationbaritems;

import de.cisha.chess.model.Game;
import de.cisha.chess.model.GameHolder;

public class ResetToNewGameNavigationBarItem extends AbstractResetNavigationBarItem
{
    private GameHolder _gameHolder;
    
    public ResetToNewGameNavigationBarItem(final GameHolder gameHolder) {
        this._gameHolder = gameHolder;
    }
    
    @Override
    public void handleClick() {
        this._gameHolder.setNewGame(new Game());
    }
}

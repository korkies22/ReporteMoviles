// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.video.command;

import de.cisha.android.board.view.BoardMarkingDisplay;
import de.cisha.android.board.video.model.VideoCommandDelegate;

public class SelectGameCommand extends TimedCommand
{
    private int _gameIndex;
    private int _moveId;
    
    public SelectGameCommand(final int gameIndex, final int moveId, final long n) {
        super(n);
        this._gameIndex = gameIndex;
        this._moveId = moveId;
    }
    
    @Override
    public boolean executeOn(final VideoCommandDelegate videoCommandDelegate, final BoardMarkingDisplay boardMarkingDisplay) {
        boolean selectGame = videoCommandDelegate.selectGame(this._gameIndex);
        if (this._moveId != 0) {
            selectGame = (selectGame && videoCommandDelegate.selectMove(this._moveId));
        }
        return selectGame;
    }
}

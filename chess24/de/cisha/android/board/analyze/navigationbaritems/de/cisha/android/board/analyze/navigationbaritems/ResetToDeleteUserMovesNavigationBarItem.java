/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.analyze.navigationbaritems;

import de.cisha.android.board.analyze.navigationbaritems.AbstractResetNavigationBarItem;
import de.cisha.chess.model.GameHolder;

public class ResetToDeleteUserMovesNavigationBarItem
extends AbstractResetNavigationBarItem {
    private GameHolder _gameHolder;

    public ResetToDeleteUserMovesNavigationBarItem(GameHolder gameHolder) {
        this._gameHolder = gameHolder;
    }

    @Override
    public void handleClick() {
        this._gameHolder.deleteUserMoves();
    }
}

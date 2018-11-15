/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.setup;

import de.cisha.android.board.setup.model.PositionHolder;
import de.cisha.chess.model.FEN;
import de.cisha.chess.model.position.Position;

class SetupBoardNavigationBarController
implements Runnable {
    SetupBoardNavigationBarController() {
    }

    @Override
    public void run() {
        SetupBoardNavigationBarController.this._positionHolder.setPosition(new Position(FEN.STARTING_POSITION));
    }
}

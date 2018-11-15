/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.setup;

import de.cisha.android.board.setup.model.PositionHolder;

class SetupBoardNavigationBarController
implements Runnable {
    SetupBoardNavigationBarController() {
    }

    @Override
    public void run() {
        SetupBoardNavigationBarController.this._positionHolder.clearPosition();
    }
}

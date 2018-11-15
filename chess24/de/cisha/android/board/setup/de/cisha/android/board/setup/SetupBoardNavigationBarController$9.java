/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 */
package de.cisha.android.board.setup;

import android.view.View;
import de.cisha.android.board.setup.model.PositionHolder;
import de.cisha.android.board.setup.view.EnpassantView;
import de.cisha.chess.model.position.Position;

class SetupBoardNavigationBarController
implements Runnable {
    SetupBoardNavigationBarController() {
    }

    @Override
    public void run() {
        SetupBoardNavigationBarController.this.updateEnpassantView(SetupBoardNavigationBarController.this._positionHolder.getPosition());
        SetupBoardNavigationBarController.this.showSubmenu((View)SetupBoardNavigationBarController.this._enpassantView);
    }
}

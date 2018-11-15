/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.setup;

import de.cisha.android.board.setup.model.PositionHolder;
import de.cisha.android.board.setup.view.EnpassantView;
import de.cisha.chess.model.ModifyablePosition;
import de.cisha.chess.model.Square;
import de.cisha.chess.model.position.Position;

class SetupBoardNavigationBarController
implements EnpassantView.EnpassantListener {
    SetupBoardNavigationBarController() {
    }

    @Override
    public void enpassantChanged(Square square) {
        ModifyablePosition modifyablePosition = new ModifyablePosition(SetupBoardNavigationBarController.this._positionHolder.getPosition());
        int n = square != null ? square.getColumn() : -1;
        modifyablePosition.setEnPassantColumn(n);
        SetupBoardNavigationBarController.this._positionHolder.setPosition(modifyablePosition.getSafePosition());
    }
}

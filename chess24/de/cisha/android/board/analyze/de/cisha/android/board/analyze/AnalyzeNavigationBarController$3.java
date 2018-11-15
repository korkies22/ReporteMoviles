/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.analyze;

import de.cisha.chess.model.Move;

class AnalyzeNavigationBarController
implements Runnable {
    final /* synthetic */ Move val$selectedMove;

    AnalyzeNavigationBarController(Move move) {
        this.val$selectedMove = move;
    }

    @Override
    public void run() {
        AnalyzeNavigationBarController.this.updatePositionNavigationButtons(this.val$selectedMove);
        if (AnalyzeNavigationBarController.this._closeSubmenuOnMoveChanges) {
            AnalyzeNavigationBarController.this.clearSubMenuView();
        }
    }
}

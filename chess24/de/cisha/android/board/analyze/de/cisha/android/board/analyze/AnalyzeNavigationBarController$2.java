/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.analyze;

import de.cisha.android.ui.patterns.navigationbar.MenuBarItem;
import de.cisha.chess.model.MoveContainer;

class AnalyzeNavigationBarController
implements Runnable {
    final /* synthetic */ MoveContainer val$newRootMoveContainer;

    AnalyzeNavigationBarController(MoveContainer moveContainer) {
        this.val$newRootMoveContainer = moveContainer;
    }

    @Override
    public void run() {
        AnalyzeNavigationBarController.this._next.setEnabled(this.val$newRootMoveContainer.hasChildren());
        AnalyzeNavigationBarController.this._previous.setEnabled(false);
        if (AnalyzeNavigationBarController.this._closeSubmenuOnMoveChanges) {
            AnalyzeNavigationBarController.this.clearSubMenuView();
        }
    }
}

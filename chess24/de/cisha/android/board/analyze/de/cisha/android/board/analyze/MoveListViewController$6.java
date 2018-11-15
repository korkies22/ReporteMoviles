/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 */
package de.cisha.android.board.analyze;

import android.view.View;
import de.cisha.android.board.view.notation.NotationListView;
import de.cisha.chess.model.Move;

class MoveListViewController
implements Runnable {
    final /* synthetic */ Move val$currentMove;

    MoveListViewController(Move move) {
        this.val$currentMove = move;
    }

    @Override
    public void run() {
        MoveListViewController.this._deleteYesNoDialog.setVisibility(0);
        MoveListViewController.this._deleteButton.setSelected(true);
        MoveListViewController.this._notationList.markMovesWithDeletionFlag(this.val$currentMove);
    }
}

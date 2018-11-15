/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 */
package de.cisha.android.board.analyze;

import android.view.View;
import de.cisha.android.board.view.notation.NotationListView;

class MoveListViewController
implements Runnable {
    MoveListViewController() {
    }

    @Override
    public void run() {
        MoveListViewController.this._deleteYesNoDialog.setVisibility(8);
        MoveListViewController.this._deleteButton.setSelected(false);
        MoveListViewController.this._notationList.unMarkDeletionFlagOfAllMoves();
    }
}

/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.View$OnClickListener
 */
package de.cisha.android.board.view;

import android.view.View;
import de.cisha.chess.model.Move;
import de.cisha.chess.model.MoveSelector;

class MoveListHorizontalLayout
implements View.OnClickListener {
    final /* synthetic */ Move val$move;

    MoveListHorizontalLayout(Move move) {
        this.val$move = move;
    }

    public void onClick(View view) {
        if (MoveListHorizontalLayout.this.isMoveSelectionActivated() && MoveListHorizontalLayout.this._moveSelector != null) {
            MoveListHorizontalLayout.this._moveSelector.selectMove(this.val$move);
        }
    }
}

/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.view;

import de.cisha.chess.model.Move;

class MoveListHorizontalLayout
implements Runnable {
    final /* synthetic */ Move val$move;

    MoveListHorizontalLayout(Move move) {
        this.val$move = move;
    }

    @Override
    public void run() {
        MoveListHorizontalLayout.this.addViewWithSAN(this.val$move, true);
    }
}

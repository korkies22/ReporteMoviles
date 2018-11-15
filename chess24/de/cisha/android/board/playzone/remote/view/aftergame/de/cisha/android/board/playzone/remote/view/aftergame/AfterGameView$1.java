/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.playzone.remote.view.aftergame;

import de.cisha.android.board.ModelHolder;

class AfterGameView
implements ModelHolder.ModelChangeListener<Boolean> {
    AfterGameView() {
    }

    @Override
    public void modelChanged(Boolean bl) {
        if (bl.booleanValue()) {
            AfterGameView.this.opponentDeclinedRematch();
        }
    }
}

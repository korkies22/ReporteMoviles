/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.playzone.remote.view.aftergame;

class AfterGameView
implements Runnable {
    AfterGameView() {
    }

    @Override
    public void run() {
        AfterGameView.this.deactivateRematch();
    }
}

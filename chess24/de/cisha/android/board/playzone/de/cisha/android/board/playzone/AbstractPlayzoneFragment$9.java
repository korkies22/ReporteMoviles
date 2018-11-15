/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.playzone;

import de.cisha.android.board.playzone.view.ChessClockView;

class AbstractPlayzoneFragment
implements Runnable {
    final /* synthetic */ boolean val$running;

    AbstractPlayzoneFragment(boolean bl) {
        this.val$running = bl;
    }

    @Override
    public void run() {
        AbstractPlayzoneFragment.this.getChessClockView().setRunning(this.val$running);
    }
}

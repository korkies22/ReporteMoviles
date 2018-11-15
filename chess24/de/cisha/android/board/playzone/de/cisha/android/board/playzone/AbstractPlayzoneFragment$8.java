/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.playzone;

import de.cisha.android.board.playzone.view.ChessClockView;

class AbstractPlayzoneFragment
implements Runnable {
    final /* synthetic */ boolean val$colorWhite;
    final /* synthetic */ long val$time;

    AbstractPlayzoneFragment(boolean bl, long l) {
        this.val$colorWhite = bl;
        this.val$time = l;
    }

    @Override
    public void run() {
        AbstractPlayzoneFragment.this.getChessClockView().setTime(this.val$colorWhite, this.val$time);
    }
}

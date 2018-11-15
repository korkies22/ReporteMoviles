/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.playzone;

import de.cisha.android.board.playzone.AbstractPlayzoneFragment;
import de.cisha.chess.model.GameStatus;

class AbstractPlayzoneFragment
implements Runnable {
    final /* synthetic */ GameStatus val$gameStatus;

    AbstractPlayzoneFragment(GameStatus gameStatus) {
        this.val$gameStatus = gameStatus;
    }

    @Override
    public void run() {
        AbstractPlayzoneFragment.this.dismissResignDialog();
        AbstractPlayzoneFragment.this.updateUI();
        AbstractPlayzoneFragment.this.updateUIOnce();
        AbstractPlayzoneFragment.this.showMenusForPlayzoneState(AbstractPlayzoneFragment.PlayzoneState.AFTER);
        AbstractPlayzoneFragment.this.setResultForClockView(this.val$gameStatus);
    }
}

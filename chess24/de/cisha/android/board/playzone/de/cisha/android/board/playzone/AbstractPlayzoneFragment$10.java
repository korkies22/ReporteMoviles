/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.playzone;

import de.cisha.android.board.playzone.AbstractPlayzoneFragment;
import de.cisha.android.board.playzone.view.ChessClockView;
import de.cisha.chess.model.GameResult;

class AbstractPlayzoneFragment
implements Runnable {
    AbstractPlayzoneFragment() {
    }

    @Override
    public void run() {
        AbstractPlayzoneFragment.this.showMenusForPlayzoneState(AbstractPlayzoneFragment.PlayzoneState.PLAYING);
        AbstractPlayzoneFragment.this.initListener();
        AbstractPlayzoneFragment.this.initBoard();
        AbstractPlayzoneFragment.this.updateUIOnce();
        AbstractPlayzoneFragment.this.updateUI();
        AbstractPlayzoneFragment.this.getChessClockView().setGameResult(null);
    }
}

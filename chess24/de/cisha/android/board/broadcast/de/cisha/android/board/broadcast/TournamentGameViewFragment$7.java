/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.widget.TextView
 */
package de.cisha.android.board.broadcast;

import android.view.View;
import android.widget.TextView;
import de.cisha.android.board.broadcast.model.BroadcastGameHolder;
import de.cisha.android.board.playzone.view.ChessClockView;
import de.cisha.chess.model.GameResult;
import de.cisha.chess.model.GameStatus;

class TournamentGameViewFragment
implements Runnable {
    final /* synthetic */ GameStatus val$gameStatus;

    TournamentGameViewFragment(GameStatus gameStatus) {
        this.val$gameStatus = gameStatus;
    }

    @Override
    public void run() {
        TournamentGameViewFragment.this._broadcastGameHolder.setGameStatus(this.val$gameStatus);
        if (this.val$gameStatus != null) {
            TournamentGameViewFragment.this._chessClockView.setGameResult(this.val$gameStatus.getResult());
            TournamentGameViewFragment.this.updateClock();
            if (this.val$gameStatus.isFinished()) {
                TournamentGameViewFragment.this._liveText.setVisibility(8);
                TournamentGameViewFragment.this._liveNumber.setVisibility(8);
                return;
            }
        } else {
            TournamentGameViewFragment.this._liveText.setVisibility(8);
            TournamentGameViewFragment.this._liveNumber.setVisibility(8);
        }
    }
}

/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.broadcast;

import de.cisha.android.board.broadcast.model.BroadcastChessClock;
import de.cisha.android.board.broadcast.model.BroadcastGameHolder;
import de.cisha.android.board.broadcast.model.ITournamentGameConnection;
import de.cisha.android.board.playzone.view.ChessClockView;
import de.cisha.chess.model.Move;
import de.cisha.chess.model.Piece;
import de.cisha.chess.model.SEP;

class TournamentGameViewFragment
implements Runnable {
    final /* synthetic */ int val$moveId;
    final /* synthetic */ int val$moveTime;
    final /* synthetic */ int val$parentId;
    final /* synthetic */ SEP val$serverMove;

    TournamentGameViewFragment(int n, SEP sEP, int n2, int n3) {
        this.val$parentId = n;
        this.val$serverMove = sEP;
        this.val$moveTime = n2;
        this.val$moveId = n3;
    }

    @Override
    public void run() {
        Move move = TournamentGameViewFragment.this._broadcastGameHolder.addNewServerMove(this.val$parentId, this.val$serverMove, this.val$moveTime, this.val$moveId);
        if (move == null) {
            TournamentGameViewFragment.this._flagReloadGame = true;
            TournamentGameViewFragment.this._gameConnection.close();
            return;
        }
        boolean bl = move.getPiece().getColor();
        if (TournamentGameViewFragment.this._clock != null) {
            TournamentGameViewFragment.this._clock.addTimeUsage(move.getMoveTimeInMills(), bl);
            TournamentGameViewFragment.this._clock.setActiveColor(bl ^ true, false);
        }
        if (TournamentGameViewFragment.this._chessClockView != null) {
            TournamentGameViewFragment.this._chessClockView.setActiveClockMark(true ^ bl);
        }
        TournamentGameViewFragment.this.updateClock();
    }
}

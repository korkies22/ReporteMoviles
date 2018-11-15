/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.playzone.remote.model;

import de.cisha.android.board.playzone.model.PlayzoneGameHolder;
import de.cisha.chess.model.Move;
import de.cisha.chess.model.SEP;

class RemoteGameAdapter
implements Runnable {
    final /* synthetic */ SEP val$sep;
    final /* synthetic */ int val$timeMillis;

    RemoteGameAdapter(SEP sEP, int n) {
        this.val$sep = sEP;
        this.val$timeMillis = n;
    }

    @Override
    public void run() {
        Move move = RemoteGameAdapter.this._gameHolder.doMoveInCurrentPosition(this.val$sep);
        if (move != null) {
            move.setMoveTimeInMills(this.val$timeMillis);
            RemoteGameAdapter.this._gameHolder.addMoveTimeUsageForColor(move, this.val$timeMillis, RemoteGameAdapter.this._playerIsWhite ^ true);
            RemoteGameAdapter.this._gameHolder.setPlayersDrawOffer(false);
            RemoteGameAdapter.this._lastSentMove = null;
        }
    }
}

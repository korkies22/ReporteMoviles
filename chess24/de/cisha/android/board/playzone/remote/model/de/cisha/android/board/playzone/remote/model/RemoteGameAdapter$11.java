/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.playzone.remote.model;

import de.cisha.android.board.playzone.model.PlayzoneGameHolder;
import de.cisha.chess.model.Move;
import de.cisha.chess.model.MoveContainer;

class RemoteGameAdapter
implements Runnable {
    final /* synthetic */ int val$timeMillis;

    RemoteGameAdapter(int n) {
        this.val$timeMillis = n;
    }

    @Override
    public void run() {
        RemoteGameAdapter.this._lastSentMove = null;
        Move move = RemoteGameAdapter.this._gameHolder.getRootMoveContainer().getLastMoveinMainLine();
        if (move != null) {
            RemoteGameAdapter.this._gameHolder.addMoveTimeUsageForColor(move, this.val$timeMillis, RemoteGameAdapter.this._playerIsWhite);
        }
    }
}

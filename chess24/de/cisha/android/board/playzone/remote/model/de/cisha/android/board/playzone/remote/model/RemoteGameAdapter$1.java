/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.util.Log
 */
package de.cisha.android.board.playzone.remote.model;

import android.util.Log;
import de.cisha.android.board.playzone.model.PlayzoneGameHolder;
import de.cisha.android.board.service.NodeServerAddress;
import de.cisha.chess.model.Move;
import de.cisha.chess.model.MoveContainer;
import de.cisha.chess.model.MovesObserver;
import de.cisha.chess.model.Piece;

class RemoteGameAdapter
implements Runnable {
    final /* synthetic */ boolean val$colorWhite;
    final /* synthetic */ String val$gameSessionToken;
    final /* synthetic */ NodeServerAddress val$playingServer;

    RemoteGameAdapter(String string, boolean bl, NodeServerAddress nodeServerAddress) {
        this.val$gameSessionToken = string;
        this.val$colorWhite = bl;
        this.val$playingServer = nodeServerAddress;
    }

    @Override
    public void run() {
        RemoteGameAdapter.this._gameSessionToken = this.val$gameSessionToken;
        RemoteGameAdapter.this._playerIsWhite = this.val$colorWhite;
        RemoteGameAdapter.this._playing = this.val$playingServer;
        RemoteGameAdapter.this._gameHolder.setPlayerColor(RemoteGameAdapter.this._playerIsWhite);
        RemoteGameAdapter.this._movesObserver = new MovesObserver(){

            @Override
            public void allMovesChanged(MoveContainer moveContainer) {
            }

            @Override
            public boolean canSkipMoves() {
                return false;
            }

            @Override
            public void newMove(Move move) {
                if (move.getPiece().isWhite() == RemoteGameAdapter.this._playerIsWhite) {
                    RemoteGameAdapter.this._lastSentMove = move;
                }
            }

            @Override
            public void selectedMoveChanged(Move move) {
            }
        };
        RemoteGameAdapter.this._gameHolder.addMovesObserver(RemoteGameAdapter.this._movesObserver);
        Log.d((String)"de.cisha.test.socketIO", (String)"called onPairingSucceeded()");
    }

}

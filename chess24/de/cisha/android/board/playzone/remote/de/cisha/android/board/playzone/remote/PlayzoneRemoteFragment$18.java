/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.playzone.remote;

import de.cisha.android.board.playzone.remote.view.aftergame.AfterGameView;

class PlayzoneRemoteFragment
implements Runnable {
    PlayzoneRemoteFragment() {
    }

    @Override
    public void run() {
        if (PlayzoneRemoteFragment.this._afterGameView != null) {
            PlayzoneRemoteFragment.this._afterGameView.showOpponentsRematchOffer();
        }
    }
}

/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.playzone.remote;

import de.cisha.android.board.ModelHolder;

class PlayzoneRemoteFragment
implements Runnable {
    PlayzoneRemoteFragment() {
    }

    @Override
    public void run() {
        PlayzoneRemoteFragment.this._opponentDeclinedRematchHolder.setModel(true);
    }
}

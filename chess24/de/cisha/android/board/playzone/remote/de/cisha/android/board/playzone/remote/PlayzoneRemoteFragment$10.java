/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.playzone.remote;

import de.cisha.android.board.playzone.remote.PlayzoneRemoteFragment;

class PlayzoneRemoteFragment
implements Runnable {
    PlayzoneRemoteFragment() {
    }

    @Override
    public void run() {
        if (PlayzoneRemoteFragment.this._waitingDialog != null) {
            PlayzoneRemoteFragment.this._waitingDialog.hide();
        }
    }
}

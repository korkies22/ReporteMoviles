/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.playzone.remote;

import de.cisha.android.board.action.BoardAction;
import de.cisha.android.board.playzone.remote.model.RemoteGameAdapter;

class PlayzoneRemoteFragment
implements BoardAction {
    PlayzoneRemoteFragment() {
    }

    @Override
    public void perform() {
        if (PlayzoneRemoteFragment.this._model != null) {
            PlayzoneRemoteFragment.this._model.requestRematch();
        }
    }
}

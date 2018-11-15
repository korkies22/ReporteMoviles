/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.playzone.remote;

import de.cisha.android.board.modalfragments.RookieDialogLoading;
import de.cisha.android.board.playzone.remote.PlayzoneRemoteFragment;
import de.cisha.android.board.playzone.remote.model.RemoteGameAdapter;

class PlayzoneRemoteFragment
implements RookieDialogLoading.OnCancelListener {
    PlayzoneRemoteFragment() {
    }

    @Override
    public void onCancel() {
        if (7.this.this$0._model != null) {
            7.this.this$0._model.destroy();
        }
    }
}

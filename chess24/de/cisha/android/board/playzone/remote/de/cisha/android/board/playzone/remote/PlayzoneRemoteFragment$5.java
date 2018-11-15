/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.playzone.remote;

import de.cisha.android.board.modalfragments.RookieDialogLoading;

class PlayzoneRemoteFragment
implements RookieDialogLoading.OnCancelListener {
    PlayzoneRemoteFragment() {
    }

    @Override
    public void onCancel() {
        PlayzoneRemoteFragment.this._cancelLoadingPlayzoneInfo = true;
    }
}

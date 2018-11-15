/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.playzone.remote;

import de.cisha.android.board.IErrorPresenter;
import de.cisha.android.board.playzone.remote.PlayzoneRemoteFragment;

class PlayzoneRemoteFragment
implements IErrorPresenter.IReloadAction {
    PlayzoneRemoteFragment() {
    }

    @Override
    public void performReload() {
        3.this.this$0.loadServerAddressesWithReload(3.this.val$success);
    }
}

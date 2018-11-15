/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.playzone.remote;

import de.cisha.android.board.IContentPresenter;
import de.cisha.android.board.modalfragments.AbstractConversionDialogFragment;
import de.cisha.android.board.modalfragments.ConversionContext;
import de.cisha.android.board.playzone.remote.PlayzoneRemoteFragment;

class PlayzoneRemoteFragment
implements Runnable {
    PlayzoneRemoteFragment() {
    }

    @Override
    public void run() {
        11.this.this$0.getContentPresenter().showConversionDialog(null, ConversionContext.PLAYZONE);
    }
}

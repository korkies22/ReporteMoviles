/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.playzone.remote;

import de.cisha.android.board.IContentPresenter;
import de.cisha.android.board.action.BoardAction;

class PlayzoneRemoteFragment
implements BoardAction {
    PlayzoneRemoteFragment() {
    }

    @Override
    public void perform() {
        PlayzoneRemoteFragment.this.getContentPresenter().popCurrentFragment();
    }
}

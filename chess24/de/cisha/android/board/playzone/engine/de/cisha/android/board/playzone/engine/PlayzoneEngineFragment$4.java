/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.playzone.engine;

import de.cisha.android.board.IContentPresenter;
import de.cisha.android.ui.patterns.dialogs.ConfirmCallback;

class PlayzoneEngineFragment
implements ConfirmCallback {
    PlayzoneEngineFragment() {
    }

    @Override
    public void canceled() {
    }

    @Override
    public void confirmed() {
        PlayzoneEngineFragment.this.getContentPresenter().popCurrentFragment();
    }
}

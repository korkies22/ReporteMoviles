/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.playzone.engine;

import de.cisha.android.board.modalfragments.RookieDialogLoading;
import de.cisha.android.board.playzone.AbstractPlayzoneFragment;

class PlayzoneEngineFragment
implements RookieDialogLoading.OnCancelListener {
    PlayzoneEngineFragment() {
    }

    @Override
    public void onCancel() {
        PlayzoneEngineFragment.this.showMenusForPlayzoneState(AbstractPlayzoneFragment.PlayzoneState.CHOOSING_BEFORE);
    }
}

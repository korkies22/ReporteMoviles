/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.profile;

import de.cisha.android.board.IErrorPresenter;
import de.cisha.android.board.profile.ProfileEditorFragment;

class ProfileEditorFragment
implements IErrorPresenter.IReloadAction {
    ProfileEditorFragment() {
    }

    @Override
    public void performReload() {
        4.this.this$0.loadProfileData();
    }
}

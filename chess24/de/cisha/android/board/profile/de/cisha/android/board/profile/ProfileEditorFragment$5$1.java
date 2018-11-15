/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.profile;

import de.cisha.android.board.profile.EditPasswordDialogFragment;
import de.cisha.android.board.profile.ProfileEditorFragment;

class ProfileEditorFragment
implements EditPasswordDialogFragment.OnPasswordChangedListener {
    ProfileEditorFragment() {
    }

    @Override
    public void onPasswordChanged(String string, String string2, String string3) {
        5.this.this$0.setPassword(string, string2, string3);
    }
}

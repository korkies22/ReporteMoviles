/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.profile;

import de.cisha.android.board.profile.EditEmailDialogFragment;
import de.cisha.android.board.profile.ProfileEditorFragment;

class ProfileEditorFragment
implements EditEmailDialogFragment.OnEmailChangedListener {
    ProfileEditorFragment() {
    }

    @Override
    public void onEmailChanged(String string, String string2) {
        6.this.this$0.setEmail(string, string2);
    }
}

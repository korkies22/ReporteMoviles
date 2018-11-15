/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.profile;

import de.cisha.android.board.profile.EditPasswordDialogFragment;
import de.cisha.android.board.profile.ProfileEditorFragment;

class ProfileEditorFragment
implements Runnable {
    ProfileEditorFragment() {
    }

    @Override
    public void run() {
        13.this.this$0.hideDialogWaiting();
        if (13.this.this$0._dialogEditPassword != null && 13.this.this$0._dialogEditPassword.isResumed()) {
            13.this.this$0._dialogEditPassword.dismiss();
        }
    }
}

/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.profile;

import de.cisha.android.board.profile.EditEmailDialogFragment;
import de.cisha.android.board.profile.ProfileEditorFragment;
import de.cisha.android.ui.patterns.input.CustomEditText;

class ProfileEditorFragment
implements Runnable {
    ProfileEditorFragment() {
    }

    @Override
    public void run() {
        if (14.this.this$0._dialogEditEmail != null && 14.this.this$0._dialogEditEmail.isResumed()) {
            14.this.this$0._dialogEditEmail.dismiss();
        }
        14.this.this$0.hideDialogWaiting();
        14.this.this$0._emailField.setText(14.this.val$email);
    }
}

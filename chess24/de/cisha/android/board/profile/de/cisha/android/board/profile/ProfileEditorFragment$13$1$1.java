/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.profile;

import de.cisha.android.board.profile.EditPasswordDialogFragment;
import de.cisha.android.board.profile.ProfileEditorFragment;
import de.cisha.android.board.service.jsonparser.LoadFieldError;
import java.util.List;

class ProfileEditorFragment
implements Runnable {
    ProfileEditorFragment() {
    }

    @Override
    public void run() {
        if (1.this.this$1.this$0._dialogEditPassword != null && 1.this.val$errors != null) {
            1.this.this$1.this$0._dialogEditPassword.setErrors(1.this.val$errors);
        }
        1.this.this$1.this$0.hideDialogWaiting();
    }
}

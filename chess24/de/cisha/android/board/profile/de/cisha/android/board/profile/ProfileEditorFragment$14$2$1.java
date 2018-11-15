/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.profile;

import de.cisha.android.board.profile.EditEmailDialogFragment;
import de.cisha.android.board.profile.ProfileEditorFragment;
import de.cisha.android.board.service.jsonparser.LoadFieldError;
import java.util.List;

class ProfileEditorFragment
implements Runnable {
    ProfileEditorFragment() {
    }

    @Override
    public void run() {
        2.this.this$1.this$0.hideDialogWaiting();
        if (2.this.this$1.this$0._dialogEditEmail != null) {
            2.this.this$1.this$0._dialogEditEmail.setErrors(2.this.val$errors);
        }
    }
}

/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.profile;

import de.cisha.android.board.profile.PrivacySettingsController;
import de.cisha.android.board.profile.ProfileEditorFragment;
import de.cisha.android.board.service.IWebSettingsService;

class ProfileEditorFragment
implements Runnable {
    ProfileEditorFragment() {
    }

    @Override
    public void run() {
        15.this.this$0._privacySettingsController.updatePrivacySetting(15.this.val$setting, 15.this.val$newValue);
        15.this.this$0.hideDialogWaiting();
    }
}

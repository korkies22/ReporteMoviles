/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.profile;

import de.cisha.android.board.profile.PrivacySettingsController;
import de.cisha.android.board.service.IWebSettingsService;
import java.util.Map;

class ProfileEditorFragment
implements Runnable {
    ProfileEditorFragment() {
    }

    @Override
    public void run() {
        if (ProfileEditorFragment.this._privacySettingsController != null && ProfileEditorFragment.this._privacyMap != null) {
            ProfileEditorFragment.this._privacySettingsController.setPrivacySettingValues(ProfileEditorFragment.this._privacyMap);
        }
    }
}

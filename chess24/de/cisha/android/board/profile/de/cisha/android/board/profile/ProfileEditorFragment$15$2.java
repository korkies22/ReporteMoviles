/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.widget.Toast
 */
package de.cisha.android.board.profile;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;
import de.cisha.android.board.profile.PrivacySettingsController;
import de.cisha.android.board.profile.ProfileEditorFragment;
import de.cisha.android.board.service.IWebSettingsService;

class ProfileEditorFragment
implements Runnable {
    ProfileEditorFragment() {
    }

    @Override
    public void run() {
        if (15.this.this$0.isResumed()) {
            Toast.makeText((Context)15.this.this$0.getActivity(), (CharSequence)"set setting failed", (int)0).show();
        }
        15.this.this$0.hideDialogWaiting();
        15.this.this$0._privacySettingsController.resetPrivacySetting(15.this.val$setting);
    }
}

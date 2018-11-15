/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.ComponentName
 *  android.content.Intent
 *  android.content.pm.PackageManager
 *  android.content.res.Resources
 *  android.net.Uri
 *  android.os.Parcelable
 */
package de.cisha.android.board.profile;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Parcelable;
import android.support.v4.app.FragmentActivity;
import de.cisha.android.ui.patterns.dialogs.RookieMoreDialogFragment;
import java.io.File;

class ProfileFragment
implements RookieMoreDialogFragment.ListOption {
    ProfileFragment() {
    }

    @Override
    public void executeAction() {
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        if (intent.resolveActivity(ProfileFragment.this.getActivity().getPackageManager()) != null) {
            File file = ProfileFragment.this.getCameraImageFile();
            if (file != null) {
                if (file.exists()) {
                    file.delete();
                }
                intent.putExtra("output", (Parcelable)Uri.fromFile((File)file));
            }
            ProfileFragment.this.startActivityForResult(intent, 55555);
        }
    }

    @Override
    public String getString() {
        return ProfileFragment.this.getResources().getString(2131690215);
    }
}

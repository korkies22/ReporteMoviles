/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.graphics.Bitmap
 *  android.os.AsyncTask
 */
package de.cisha.android.board.profile;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import de.cisha.android.board.IErrorPresenter;
import de.cisha.android.board.profile.ProfileDataController;
import de.cisha.android.board.profile.ProfileFragment;

class ProfileFragment.UploadResultTask
implements IErrorPresenter.IReloadAction {
    ProfileFragment.UploadResultTask() {
    }

    @Override
    public void performReload() {
        1.this.this$1.this$0._profileDataController.showImageLoading();
        new ProfileFragment.UploadResultTask(1.this.this$1.this$0, 0).execute((Object[])new Bitmap[]{1.this.val$result});
    }
}

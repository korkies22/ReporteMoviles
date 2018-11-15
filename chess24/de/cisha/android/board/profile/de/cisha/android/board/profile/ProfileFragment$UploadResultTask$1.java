/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.graphics.Bitmap
 *  android.os.AsyncTask
 *  org.json.JSONObject
 */
package de.cisha.android.board.profile;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import de.cisha.android.board.IErrorPresenter;
import de.cisha.android.board.profile.ProfileDataController;
import de.cisha.android.board.profile.ProfileFragment;
import de.cisha.android.board.service.IProfileDataService;
import de.cisha.android.board.service.jsonparser.APIStatusCode;
import de.cisha.android.board.service.jsonparser.LoadCommandCallback;
import de.cisha.android.board.service.jsonparser.LoadFieldError;
import java.util.List;
import org.json.JSONObject;

class ProfileFragment.UploadResultTask
implements LoadCommandCallback<IProfileDataService.SetProfileImageResponse> {
    final /* synthetic */ Bitmap val$result;

    ProfileFragment.UploadResultTask(Bitmap bitmap) {
        this.val$result = bitmap;
    }

    @Override
    public void loadingCancelled() {
        UploadResultTask.this.this$0._profileDataController.stopShowingImageLoading();
        UploadResultTask.this.resetImage();
    }

    @Override
    public void loadingFailed(APIStatusCode aPIStatusCode, String string, List<LoadFieldError> list, JSONObject jSONObject) {
        UploadResultTask.this.this$0._profileDataController.stopShowingImageLoading();
        UploadResultTask.this.this$0.showViewForErrorCode(aPIStatusCode, new IErrorPresenter.IReloadAction(){

            @Override
            public void performReload() {
                UploadResultTask.this.this$0._profileDataController.showImageLoading();
                new ProfileFragment.UploadResultTask(UploadResultTask.this.this$0, 0).execute((Object[])new Bitmap[]{1.this.val$result});
            }
        });
        UploadResultTask.this.resetImage();
    }

    @Override
    public void loadingSucceded(IProfileDataService.SetProfileImageResponse setProfileImageResponse) {
        UploadResultTask.this.this$0._profileDataController.stopShowingImageLoading();
    }

}

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
import de.cisha.android.board.service.ServiceProvider;
import de.cisha.android.board.service.jsonparser.APIStatusCode;
import de.cisha.android.board.service.jsonparser.LoadCommandCallback;
import de.cisha.android.board.service.jsonparser.LoadFieldError;
import de.cisha.chess.util.BitmapHelper;
import java.util.List;
import org.json.JSONObject;

private class ProfileFragment.UploadResultTask
extends AsyncTask<Bitmap, Void, Bitmap> {
    private int _rotation;

    public ProfileFragment.UploadResultTask(int n) {
        this._rotation = n;
    }

    private void resetImage() {
        ProfileFragment.this._profileDataController.resetProfileImage();
    }

    protected /* varargs */ Bitmap doInBackground(Bitmap ... bitmap) {
        Bitmap bitmap2 = ((Bitmap[])bitmap).length > 0 ? bitmap[0] : null;
        bitmap = bitmap2;
        if (bitmap2 != null) {
            bitmap = bitmap2 = BitmapHelper.scaleBitmapToSize(bitmap2, 300);
            if (this._rotation != -1) {
                bitmap = bitmap2;
                if (this._rotation != 0) {
                    bitmap = BitmapHelper.rotateBitmap(bitmap2, this._rotation);
                }
            }
        }
        return bitmap;
    }

    protected void onPostExecute(final Bitmap bitmap) {
        ProfileFragment.this._profileDataController.setProfileImage(bitmap);
        ServiceProvider.getInstance().getProfileDataService().setProfileImage(bitmap, new LoadCommandCallback<IProfileDataService.SetProfileImageResponse>(){

            @Override
            public void loadingCancelled() {
                ProfileFragment.this._profileDataController.stopShowingImageLoading();
                UploadResultTask.this.resetImage();
            }

            @Override
            public void loadingFailed(APIStatusCode aPIStatusCode, String string, List<LoadFieldError> list, JSONObject jSONObject) {
                ProfileFragment.this._profileDataController.stopShowingImageLoading();
                ProfileFragment.this.showViewForErrorCode(aPIStatusCode, new IErrorPresenter.IReloadAction(){

                    @Override
                    public void performReload() {
                        ProfileFragment.this._profileDataController.showImageLoading();
                        new ProfileFragment.UploadResultTask(0).execute((Object[])new Bitmap[]{bitmap});
                    }
                });
                UploadResultTask.this.resetImage();
            }

            @Override
            public void loadingSucceded(IProfileDataService.SetProfileImageResponse setProfileImageResponse) {
                ProfileFragment.this._profileDataController.stopShowingImageLoading();
            }

        });
        super.onPostExecute((Object)bitmap);
    }

}

/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.graphics.Bitmap
 *  android.view.View
 *  android.view.View$OnClickListener
 *  org.json.JSONObject
 */
package de.cisha.android.board.profile;

import android.graphics.Bitmap;
import android.view.View;
import de.cisha.android.board.LoadingHelper;
import de.cisha.android.board.profile.view.ProfileDataView;
import de.cisha.android.board.service.IProfileDataService;
import de.cisha.android.board.service.ServiceProvider;
import de.cisha.android.board.service.jsonparser.APIStatusCode;
import de.cisha.android.board.service.jsonparser.LoadCommandCallback;
import de.cisha.android.board.service.jsonparser.LoadFieldError;
import de.cisha.android.board.user.User;
import de.cisha.chess.model.CishaUUID;
import de.cisha.chess.model.Country;
import de.cisha.chess.model.Rating;
import java.util.List;
import org.json.JSONObject;

public class ProfileDataController
implements IProfileDataService.IUserDataChangedListener {
    private LoadingHelper _loadingHelper;
    private IProfileDataService _profileDataService;
    private User _user;
    private ProfileDataView _view;

    public ProfileDataController(ProfileDataView profileDataView, ProfileImageClickListener profileImageClickListener, IProfileDataService iProfileDataService, LoadingHelper loadingHelper) {
        this._view = profileDataView;
        this._profileDataService = iProfileDataService;
        this._loadingHelper = loadingHelper;
        this._view.setOnImageClickListener(profileImageClickListener);
        this.updateViewWithUser(this._profileDataService.getCurrentUserData());
        this._profileDataService.addUserDataChangedListener(this);
    }

    private String ratingToString(Rating rating) {
        if (rating != null) {
            return rating.getRatingString();
        }
        return "";
    }

    private void updateViewWithUser(User user) {
        this._user = user;
        if (user != null) {
            Object object = this._view;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(user.getFirstname());
            stringBuilder.append(" ");
            stringBuilder.append(user.getSurname());
            object.setName(stringBuilder.toString());
            this._view.setNickname(user.getNickname());
            object = user.getCountry();
            this._view.setCountry((Country)object);
            this._view.setProfileImageCouchId(user.getProfileImageCouchId());
            this._view.setRatingStandardText(this.ratingToString(user.getRatingClassical()));
            this._view.setRatingBlitzText(this.ratingToString(user.getRatingBlitz()));
            this._view.setRatingBulletText(this.ratingToString(user.getRatingBullet()));
            this._view.setRatingTacticClassicText(this.ratingToString(user.getRatingTacticClassic()));
            return;
        }
        this._view.reset();
    }

    public void reloadUserData() {
        this._loadingHelper.addLoadable(this);
        ServiceProvider.getInstance().getProfileDataService().getUserData(new LoadCommandCallback<User>(){

            private void notifyLoadingCompleted() {
                ProfileDataController.this._loadingHelper.loadingCompleted(ProfileDataController.this);
            }

            @Override
            public void loadingCancelled() {
            }

            @Override
            public void loadingFailed(APIStatusCode aPIStatusCode, String string, List<LoadFieldError> list, JSONObject jSONObject) {
                this.notifyLoadingCompleted();
            }

            @Override
            public void loadingSucceded(User user) {
                this.notifyLoadingCompleted();
            }
        });
    }

    public void resetProfileImage() {
        if (this._user != null && this._user.getProfileImageCouchId() != null) {
            this._view.setProfileImageCouchId(this._user.getProfileImageCouchId());
        }
    }

    public void setProfileImage(Bitmap bitmap) {
        this._view.setImage(bitmap);
    }

    public void showImageLoading() {
        this._view.showImageLoading();
    }

    public void stopShowingImageLoading() {
        this._view.stopShowingImageLoading();
    }

    @Override
    public void userDataChanged(final User user) {
        this._view.post(new Runnable(){

            @Override
            public void run() {
                ProfileDataController.this.updateViewWithUser(user);
            }
        });
    }

    public static interface ProfileImageClickListener
    extends View.OnClickListener {
    }

}

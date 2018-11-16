// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.profile;

import android.graphics.Bitmap;
import org.json.JSONObject;
import de.cisha.android.board.service.jsonparser.LoadFieldError;
import java.util.List;
import de.cisha.android.board.service.jsonparser.APIStatusCode;
import de.cisha.android.board.service.jsonparser.LoadCommandCallback;
import de.cisha.android.board.service.ServiceProvider;
import de.cisha.chess.model.Rating;
import android.view.View.OnClickListener;
import de.cisha.android.board.profile.view.ProfileDataView;
import de.cisha.android.board.user.User;
import de.cisha.android.board.LoadingHelper;
import de.cisha.android.board.service.IProfileDataService;

public class ProfileDataController implements IUserDataChangedListener
{
    private LoadingHelper _loadingHelper;
    private IProfileDataService _profileDataService;
    private User _user;
    private ProfileDataView _view;
    
    public ProfileDataController(final ProfileDataView view, final ProfileImageClickListener onImageClickListener, final IProfileDataService profileDataService, final LoadingHelper loadingHelper) {
        this._view = view;
        this._profileDataService = profileDataService;
        this._loadingHelper = loadingHelper;
        this._view.setOnImageClickListener((View.OnClickListener)onImageClickListener);
        this.updateViewWithUser(this._profileDataService.getCurrentUserData());
        this._profileDataService.addUserDataChangedListener((IProfileDataService.IUserDataChangedListener)this);
    }
    
    private String ratingToString(final Rating rating) {
        if (rating != null) {
            return rating.getRatingString();
        }
        return "";
    }
    
    private void updateViewWithUser(final User user) {
        this._user = user;
        if (user != null) {
            final ProfileDataView view = this._view;
            final StringBuilder sb = new StringBuilder();
            sb.append(user.getFirstname());
            sb.append(" ");
            sb.append(user.getSurname());
            view.setName(sb.toString());
            this._view.setNickname(user.getNickname());
            this._view.setCountry(user.getCountry());
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
        ServiceProvider.getInstance().getProfileDataService().getUserData(new LoadCommandCallback<User>() {
            private void notifyLoadingCompleted() {
                ProfileDataController.this._loadingHelper.loadingCompleted(ProfileDataController.this);
            }
            
            @Override
            public void loadingCancelled() {
            }
            
            @Override
            public void loadingFailed(final APIStatusCode apiStatusCode, final String s, final List<LoadFieldError> list, final JSONObject jsonObject) {
                this.notifyLoadingCompleted();
            }
            
            @Override
            public void loadingSucceded(final User user) {
                this.notifyLoadingCompleted();
            }
        });
    }
    
    public void resetProfileImage() {
        if (this._user != null && this._user.getProfileImageCouchId() != null) {
            this._view.setProfileImageCouchId(this._user.getProfileImageCouchId());
        }
    }
    
    public void setProfileImage(final Bitmap image) {
        this._view.setImage(image);
    }
    
    public void showImageLoading() {
        this._view.showImageLoading();
    }
    
    public void stopShowingImageLoading() {
        this._view.stopShowingImageLoading();
    }
    
    @Override
    public void userDataChanged(final User user) {
        this._view.post((Runnable)new Runnable() {
            @Override
            public void run() {
                ProfileDataController.this.updateViewWithUser(user);
            }
        });
    }
    
    public interface ProfileImageClickListener extends View.OnClickListener
    {
    }
}

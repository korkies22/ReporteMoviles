/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.profile;

import android.support.v4.app.FragmentManager;
import de.cisha.android.board.IErrorPresenter;
import de.cisha.android.board.profile.ProfileEditorFragment;
import de.cisha.android.board.profile.view.RookieFormErrorDialogFragment;
import de.cisha.android.board.service.IProfileDataService;
import de.cisha.android.board.service.ServiceProvider;
import de.cisha.android.board.service.jsonparser.APIStatusCode;
import de.cisha.android.board.service.jsonparser.LoadCommandCallback;
import de.cisha.android.board.service.jsonparser.LoadFieldError;
import java.util.List;
import java.util.Map;

class ProfileEditorFragment.SetDataCallback
implements Runnable {
    final /* synthetic */ APIStatusCode val$errorCode;
    final /* synthetic */ List val$errors;

    ProfileEditorFragment.SetDataCallback(List list, APIStatusCode aPIStatusCode) {
        this.val$errors = list;
        this.val$errorCode = aPIStatusCode;
    }

    @Override
    public void run() {
        if (this.val$errors != null && this.val$errors.size() > 0) {
            RookieFormErrorDialogFragment rookieFormErrorDialogFragment = new RookieFormErrorDialogFragment();
            rookieFormErrorDialogFragment.setErrors(this.val$errors);
            rookieFormErrorDialogFragment.show(SetDataCallback.this.this$0.getFragmentManager(), "ProfileFormErrors");
        } else {
            SetDataCallback.this.this$0.showViewForErrorCode(this.val$errorCode, new IErrorPresenter.IReloadAction(){

                @Override
                public void performReload() {
                    ServiceProvider.getInstance().getProfileDataService().setUserData(SetDataCallback.this._params, new ProfileEditorFragment.SetDataCallback(SetDataCallback.this.this$0, SetDataCallback.this._params));
                }
            }, false);
        }
        SetDataCallback.this.this$0.hideDialogWaiting();
    }

}

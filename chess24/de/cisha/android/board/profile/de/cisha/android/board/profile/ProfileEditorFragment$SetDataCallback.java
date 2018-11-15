/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.view.View
 *  android.widget.Toast
 *  org.json.JSONObject
 */
package de.cisha.android.board.profile;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.Toast;
import de.cisha.android.board.IContentPresenter;
import de.cisha.android.board.IErrorPresenter;
import de.cisha.android.board.profile.ProfileEditorFragment;
import de.cisha.android.board.profile.view.ProfileSavedToastView;
import de.cisha.android.board.profile.view.RookieFormErrorDialogFragment;
import de.cisha.android.board.service.IProfileDataService;
import de.cisha.android.board.service.ServiceProvider;
import de.cisha.android.board.service.jsonparser.APIStatusCode;
import de.cisha.android.board.service.jsonparser.LoadCommandCallback;
import de.cisha.android.board.service.jsonparser.LoadCommandCallbackWithTimeout;
import de.cisha.android.board.service.jsonparser.LoadFieldError;
import java.util.List;
import java.util.Map;
import org.json.JSONObject;

private class ProfileEditorFragment.SetDataCallback
extends LoadCommandCallbackWithTimeout<Map<String, String>> {
    private Map<String, String> _params;

    public ProfileEditorFragment.SetDataCallback(Map<String, String> map) {
        this._params = map;
    }

    @Override
    protected void failed(final APIStatusCode aPIStatusCode, String string, final List<LoadFieldError> list, JSONObject jSONObject) {
        ProfileEditorFragment.this.runOnUiThreadBetweenStartAndDestroy(new Runnable(){

            @Override
            public void run() {
                if (list != null && list.size() > 0) {
                    RookieFormErrorDialogFragment rookieFormErrorDialogFragment = new RookieFormErrorDialogFragment();
                    rookieFormErrorDialogFragment.setErrors(list);
                    rookieFormErrorDialogFragment.show(ProfileEditorFragment.this.getFragmentManager(), "ProfileFormErrors");
                } else {
                    ProfileEditorFragment.this.showViewForErrorCode(aPIStatusCode, new IErrorPresenter.IReloadAction(){

                        @Override
                        public void performReload() {
                            ServiceProvider.getInstance().getProfileDataService().setUserData(SetDataCallback.this._params, new ProfileEditorFragment.SetDataCallback(SetDataCallback.this._params));
                        }
                    }, false);
                }
                ProfileEditorFragment.this.hideDialogWaiting();
            }

        });
    }

    @Override
    protected void succeded(Map<String, String> map) {
        ProfileEditorFragment.this.runOnUiThreadBetweenStartAndDestroy(new Runnable(){

            @Override
            public void run() {
                ProfileSavedToastView profileSavedToastView = new ProfileSavedToastView(ProfileEditorFragment.this.getActivity().getApplicationContext());
                Toast toast = new Toast(ProfileEditorFragment.this.getActivity().getApplicationContext());
                toast.setGravity(17, 0, -100);
                toast.setDuration(1);
                toast.setView((View)profileSavedToastView);
                toast.show();
                ProfileEditorFragment.this.hideDialogWaiting();
                ProfileEditorFragment.this.getContentPresenter().popCurrentFragment();
            }
        });
    }

}

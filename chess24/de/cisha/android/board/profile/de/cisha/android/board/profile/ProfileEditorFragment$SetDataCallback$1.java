/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.view.View
 *  android.widget.Toast
 */
package de.cisha.android.board.profile;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Toast;
import de.cisha.android.board.IContentPresenter;
import de.cisha.android.board.profile.ProfileEditorFragment;
import de.cisha.android.board.profile.view.ProfileSavedToastView;

class ProfileEditorFragment.SetDataCallback
implements Runnable {
    ProfileEditorFragment.SetDataCallback() {
    }

    @Override
    public void run() {
        ProfileSavedToastView profileSavedToastView = new ProfileSavedToastView(SetDataCallback.this.this$0.getActivity().getApplicationContext());
        Toast toast = new Toast(SetDataCallback.this.this$0.getActivity().getApplicationContext());
        toast.setGravity(17, 0, -100);
        toast.setDuration(1);
        toast.setView((View)profileSavedToastView);
        toast.show();
        SetDataCallback.this.this$0.hideDialogWaiting();
        SetDataCallback.this.this$0.getContentPresenter().popCurrentFragment();
    }
}

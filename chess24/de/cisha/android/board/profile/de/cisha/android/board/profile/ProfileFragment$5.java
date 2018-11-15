/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.View$OnClickListener
 */
package de.cisha.android.board.profile;

import android.view.View;
import de.cisha.android.board.AbstractContentFragment;
import de.cisha.android.board.IContentPresenter;
import de.cisha.android.board.profile.ProfileEditorFragment;
import de.cisha.android.board.profile.ProfileEditorGuestFragment;
import de.cisha.android.board.service.IMembershipService;
import de.cisha.android.board.service.ServiceProvider;

class ProfileFragment
implements View.OnClickListener {
    ProfileFragment() {
    }

    public void onClick(View object) {
        object = ServiceProvider.getInstance().getMembershipService().getMembershipLevel() == IMembershipService.MembershipLevel.MembershipLevelGuest ? new ProfileEditorGuestFragment() : new ProfileEditorFragment();
        ProfileFragment.this.getContentPresenter().showFragment((AbstractContentFragment)object, true, true);
    }
}

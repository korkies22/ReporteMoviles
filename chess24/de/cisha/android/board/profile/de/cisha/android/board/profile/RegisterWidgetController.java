/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.Intent
 *  android.view.View
 *  android.view.View$OnClickListener
 */
package de.cisha.android.board.profile;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import de.cisha.android.board.profile.view.RegisterWidgetView;
import de.cisha.android.board.registration.LoginActivity;
import de.cisha.android.board.service.IMembershipService;
import de.cisha.android.board.service.IProfileDataService;
import de.cisha.android.board.user.User;

public class RegisterWidgetController
implements IProfileDataService.IUserDataChangedListener {
    private IMembershipService _membershipService;
    private RegisterWidgetView _view;

    public RegisterWidgetController(RegisterWidgetView registerWidgetView, IProfileDataService iProfileDataService, IMembershipService iMembershipService) {
        this._view = registerWidgetView;
        this._membershipService = iMembershipService;
        iProfileDataService.addUserDataChangedListener(this);
        registerWidgetView.findViewById(2131296850).setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                view = view.getContext();
                Intent intent = new Intent((Context)view, LoginActivity.class);
                intent.setFlags(131072);
                view.startActivity(intent);
            }
        });
        this.updateAppearence();
    }

    private void updateAppearence() {
        IMembershipService.MembershipLevel membershipLevel = this._membershipService.getMembershipLevel();
        RegisterWidgetView registerWidgetView = this._view;
        int n = membershipLevel == IMembershipService.MembershipLevel.MembershipLevelGuest ? 0 : 8;
        registerWidgetView.setVisibility(n);
    }

    @Override
    public void userDataChanged(User user) {
        this.updateAppearence();
    }

}

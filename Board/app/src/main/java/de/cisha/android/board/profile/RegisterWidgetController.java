// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.profile;

import de.cisha.android.board.user.User;
import android.content.Context;
import android.content.Intent;
import de.cisha.android.board.registration.LoginActivity;
import android.view.View;
import android.view.View.OnClickListener;
import de.cisha.android.board.profile.view.RegisterWidgetView;
import de.cisha.android.board.service.IMembershipService;
import de.cisha.android.board.service.IProfileDataService;

public class RegisterWidgetController implements IUserDataChangedListener
{
    private IMembershipService _membershipService;
    private RegisterWidgetView _view;
    
    public RegisterWidgetController(final RegisterWidgetView view, final IProfileDataService profileDataService, final IMembershipService membershipService) {
        this._view = view;
        this._membershipService = membershipService;
        profileDataService.addUserDataChangedListener((IProfileDataService.IUserDataChangedListener)this);
        view.findViewById(2131296850).setOnClickListener((View.OnClickListener)new View.OnClickListener() {
            public void onClick(final View view) {
                final Context context = view.getContext();
                final Intent intent = new Intent(context, (Class)LoginActivity.class);
                intent.setFlags(131072);
                context.startActivity(intent);
            }
        });
        this.updateAppearence();
    }
    
    private void updateAppearence() {
        final IMembershipService.MembershipLevel membershipLevel = this._membershipService.getMembershipLevel();
        final RegisterWidgetView view = this._view;
        int visibility;
        if (membershipLevel == IMembershipService.MembershipLevel.MembershipLevelGuest) {
            visibility = 0;
        }
        else {
            visibility = 8;
        }
        view.setVisibility(visibility);
    }
    
    @Override
    public void userDataChanged(final User user) {
        this.updateAppearence();
    }
}

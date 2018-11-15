/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 */
package de.cisha.android.board.mainmenu.view;

import android.app.Activity;
import de.cisha.android.board.AbstractContentFragment;
import de.cisha.android.board.IContentPresenter;
import de.cisha.android.board.LandingFragment;
import de.cisha.android.board.mainmenu.view.MenuItem;
import de.cisha.android.board.service.ILoginService;
import de.cisha.android.board.service.ServiceProvider;

static final class MenuItem
implements MenuItem.MenuAction {
    MenuItem() {
    }

    @Override
    public void performMenuAction(Activity activity, IContentPresenter iContentPresenter) {
        ServiceProvider.getInstance().getLoginService().logOut(new ILoginService.LogoutCallback(){

            @Override
            public void logoutFailed(String string) {
            }

            @Override
            public void logoutSucceeded() {
            }
        });
        iContentPresenter.showFragment(new LandingFragment(), true, false);
    }

}

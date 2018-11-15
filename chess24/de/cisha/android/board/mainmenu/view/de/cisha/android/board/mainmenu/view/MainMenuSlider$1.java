/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.mainmenu.view;

import de.cisha.android.board.mainmenu.view.MenuItem;
import de.cisha.android.board.user.User;

class MainMenuSlider
implements Runnable {
    final /* synthetic */ User val$user;

    MainMenuSlider(User user) {
        this.val$user = user;
    }

    @Override
    public void run() {
        MainMenuSlider.this.setUsername(this.val$user);
        MainMenuSlider.this.setProfileImageFor(this.val$user);
        MenuItem menuItem = MenuItem.SIGNOUT;
        int n = this.val$user != null && this.val$user.isGuest() ? 2131690066 : 2131690065;
        menuItem.setTitle(n);
    }
}

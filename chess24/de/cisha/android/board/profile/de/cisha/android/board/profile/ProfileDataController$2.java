/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.profile;

import de.cisha.android.board.user.User;

class ProfileDataController
implements Runnable {
    final /* synthetic */ User val$user;

    ProfileDataController(User user) {
        this.val$user = user;
    }

    @Override
    public void run() {
        ProfileDataController.this.updateViewWithUser(this.val$user);
    }
}

/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.service;

import de.cisha.android.board.service.jsonparser.LoadCommandCallback;
import de.cisha.android.board.service.jsonparser.LoadCommandCallbackWrapper;
import de.cisha.android.board.user.User;

class ProfileDataService
extends LoadCommandCallbackWrapper<User> {
    ProfileDataService(LoadCommandCallback loadCommandCallback) {
        super(loadCommandCallback);
    }

    @Override
    protected void succeded(User user) {
        ProfileDataService.this._user = user;
        super.succeded(user);
        ProfileDataService.this.notifyUserDataChanged();
        ProfileDataService.this.storeUserDataInPreferences();
    }
}

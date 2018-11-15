/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.profile;

import de.cisha.android.board.IErrorPresenter;
import de.cisha.android.board.profile.ProfileEditorFragment;
import de.cisha.android.board.service.IProfileDataService;
import de.cisha.android.board.service.ServiceProvider;
import de.cisha.android.board.service.jsonparser.LoadCommandCallback;
import java.util.Map;

class ProfileEditorFragment.SetDataCallback
implements IErrorPresenter.IReloadAction {
    ProfileEditorFragment.SetDataCallback() {
    }

    @Override
    public void performReload() {
        ServiceProvider.getInstance().getProfileDataService().setUserData(2.this.this$1._params, new ProfileEditorFragment.SetDataCallback(2.this.this$1.this$0, 2.this.this$1._params));
    }
}

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
import de.cisha.android.board.account.StoreFragment;

class ProfileEditorFragment
implements View.OnClickListener {
    ProfileEditorFragment() {
    }

    public void onClick(View view) {
        ProfileEditorFragment.this.getContentPresenter().showFragment(new StoreFragment(), true, true);
    }
}

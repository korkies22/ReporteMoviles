/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.profile;

import android.support.v4.widget.SwipeRefreshLayout;
import de.cisha.android.board.LoadingHelper;

class ProfileFragment
implements LoadingHelper.LoadingHelperListener {
    ProfileFragment() {
    }

    @Override
    public void loadingStart() {
        if (!ProfileFragment.this._refreshView.isRefreshing()) {
            ProfileFragment.this._refreshView.setRefreshing(true);
        }
    }

    @Override
    public void loadingStop(boolean bl) {
        ProfileFragment.this._refreshView.setRefreshing(false);
    }
}

/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.profile;

import android.support.v4.app.FragmentManager;
import de.cisha.android.board.IContentPresenter;
import de.cisha.android.board.profile.YourTacticsStatsController;
import de.cisha.android.board.profile.view.YourTacticsStatsView;

class ProfileFragment
extends YourTacticsStatsController {
    ProfileFragment(YourTacticsStatsView yourTacticsStatsView, FragmentManager fragmentManager) {
        super(yourTacticsStatsView, fragmentManager);
    }

    @Override
    public IContentPresenter getContentPresenter() {
        return ProfileFragment.this.getContentPresenter();
    }
}

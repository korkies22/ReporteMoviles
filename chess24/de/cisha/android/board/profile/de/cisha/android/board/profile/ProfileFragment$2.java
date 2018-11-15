/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.profile;

import android.support.v4.app.FragmentManager;
import de.cisha.android.board.IContentPresenter;
import de.cisha.android.board.profile.YourGamesViewController;
import de.cisha.android.board.profile.view.YourGamesView;

class ProfileFragment
extends YourGamesViewController {
    ProfileFragment(YourGamesView yourGamesView, FragmentManager fragmentManager) {
        super(yourGamesView, fragmentManager);
    }

    @Override
    public IContentPresenter getContentPresenter() {
        return ProfileFragment.this.getContentPresenter();
    }
}

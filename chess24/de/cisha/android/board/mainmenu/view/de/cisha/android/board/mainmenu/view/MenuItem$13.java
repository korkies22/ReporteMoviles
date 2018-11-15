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
import de.cisha.android.board.mainmenu.view.MenuItem;
import de.cisha.android.board.video.VideoSeriesListFragment;

static final class MenuItem
implements MenuItem.MenuAction {
    MenuItem() {
    }

    @Override
    public void performMenuAction(Activity activity, IContentPresenter iContentPresenter) {
        iContentPresenter.showFragment(new VideoSeriesListFragment(), true, false);
    }
}

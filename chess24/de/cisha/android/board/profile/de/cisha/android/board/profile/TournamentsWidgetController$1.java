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
import de.cisha.android.board.broadcast.TournamentListFragment;

class TournamentsWidgetController
implements View.OnClickListener {
    TournamentsWidgetController() {
    }

    public void onClick(View object) {
        object = new TournamentListFragment();
        TournamentsWidgetController.this.getContentPresenter().showFragment((AbstractContentFragment)object, false, true);
    }
}

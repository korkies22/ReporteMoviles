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
import de.cisha.android.board.tactics.TacticsStartFragment;

class YourTacticsStatsController
implements View.OnClickListener {
    YourTacticsStatsController() {
    }

    public void onClick(View object) {
        object = YourTacticsStatsController.this.getContentPresenter();
        if (object != null) {
            object.showFragment(new TacticsStartFragment(), true, false);
        }
    }
}

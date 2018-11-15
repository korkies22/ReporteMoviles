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
import de.cisha.android.board.playzone.engine.OpponentChooserFragment;

class YourGamesViewController
implements View.OnClickListener {
    YourGamesViewController() {
    }

    public void onClick(View object) {
        object = YourGamesViewController.this.getContentPresenter();
        if (object != null) {
            object.showFragment(new OpponentChooserFragment(), true, false);
        }
    }
}

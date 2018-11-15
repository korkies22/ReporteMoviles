/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.View$OnClickListener
 */
package de.cisha.android.board.tactics;

import android.view.View;
import de.cisha.android.board.AbstractContentFragment;
import de.cisha.android.board.IContentPresenter;
import de.cisha.android.board.account.StoreFragment;

class TacticsStartFragment
implements View.OnClickListener {
    TacticsStartFragment() {
    }

    public void onClick(View object) {
        object = new StoreFragment();
        TacticsStartFragment.this.getContentPresenter().showFragment((AbstractContentFragment)object, false, true);
    }
}

/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.tactics;

import de.cisha.android.board.IContentPresenter;
import de.cisha.android.board.tactics.TacticsStopFragment;

class TacticsStopFragment
implements Runnable {
    final /* synthetic */ IContentPresenter val$contentPresenter;

    TacticsStopFragment(IContentPresenter iContentPresenter) {
        this.val$contentPresenter = iContentPresenter;
    }

    @Override
    public void run() {
        this.val$contentPresenter.popCurrentFragment();
    }
}

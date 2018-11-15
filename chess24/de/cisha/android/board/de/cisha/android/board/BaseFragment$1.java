/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board;

import java.util.List;

class BaseFragment
implements Runnable {
    final /* synthetic */ Runnable val$runnable;

    BaseFragment(Runnable runnable) {
        this.val$runnable = runnable;
    }

    @Override
    public void run() {
        if (!BaseFragment.this._flagOnDestroyCalled) {
            if (!BaseFragment.this._flagOnStartCalled && BaseFragment.this._onStartRunnables != null) {
                BaseFragment.this._onStartRunnables.add(this.val$runnable);
                return;
            }
            this.val$runnable.run();
        }
    }
}

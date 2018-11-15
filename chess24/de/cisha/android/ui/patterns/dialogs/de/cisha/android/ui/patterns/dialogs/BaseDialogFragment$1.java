/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.ui.patterns.dialogs;

import java.util.List;

class BaseDialogFragment
implements Runnable {
    final /* synthetic */ Runnable val$runnable;

    BaseDialogFragment(Runnable runnable) {
        this.val$runnable = runnable;
    }

    @Override
    public void run() {
        if (BaseDialogFragment.this._flagOnStartCalled && !BaseDialogFragment.this._flagIsDestroyed) {
            this.val$runnable.run();
            return;
        }
        BaseDialogFragment.this._runnableQueue.add(this.val$runnable);
    }
}

/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.registration;

import java.util.List;

class FacebookButtonActivity
implements Runnable {
    final /* synthetic */ Runnable val$runnable;

    FacebookButtonActivity(Runnable runnable) {
        this.val$runnable = runnable;
    }

    @Override
    public void run() {
        if (FacebookButtonActivity.this._flagOnSaveInstanceStateCalled) {
            FacebookButtonActivity.this._delayedRunnables.add(this.val$runnable);
            return;
        }
        this.val$runnable.run();
    }
}

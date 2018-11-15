/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board;

class MainActivity
implements Runnable {
    final /* synthetic */ Runnable val$action;

    MainActivity(Runnable runnable) {
        this.val$action = runnable;
    }

    @Override
    public void run() {
        if (!MainActivity.this.onSaveInstanceStateCalled()) {
            MainActivity.this.runOnUiThread(this.val$action);
            return;
        }
        MainActivity.this._showFragmentActionOnResume = this.val$action;
    }
}

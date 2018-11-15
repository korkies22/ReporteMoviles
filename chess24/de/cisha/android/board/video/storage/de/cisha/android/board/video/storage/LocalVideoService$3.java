/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.video.storage;

class LocalVideoService
implements Runnable {
    final /* synthetic */ Runnable val$runnable;

    LocalVideoService(Runnable runnable) {
        this.val$runnable = runnable;
    }

    @Override
    public void run() {
        LocalVideoService.this.runOnBackgroundThread(this.val$runnable);
    }
}

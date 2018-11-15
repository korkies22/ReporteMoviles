/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.video;

import de.cisha.android.board.modalfragments.RookieDialogLoading;

class VideoSeriesListFragment
implements Runnable {
    VideoSeriesListFragment() {
    }

    @Override
    public void run() {
        VideoSeriesListFragment.this.showReloadButton(false);
        VideoSeriesListFragment.this.showDialogWaiting(false, true, null, null);
    }
}

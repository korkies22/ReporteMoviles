/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.video;

import de.cisha.android.board.modalfragments.RookieDialogLoading;

class VideoSeriesOverviewFragment
implements Runnable {
    VideoSeriesOverviewFragment() {
    }

    @Override
    public void run() {
        VideoSeriesOverviewFragment.this.showDialogWaiting(true, true, "", null);
    }
}

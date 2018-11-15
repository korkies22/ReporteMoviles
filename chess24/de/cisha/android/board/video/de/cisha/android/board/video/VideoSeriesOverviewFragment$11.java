/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.widget.TextView
 */
package de.cisha.android.board.video;

import android.widget.TextView;

class VideoSeriesOverviewFragment
implements Runnable {
    final /* synthetic */ long val$currentLoadedMBit;
    final /* synthetic */ int val$downloadProgress;
    final /* synthetic */ long val$totalMBit;

    VideoSeriesOverviewFragment(int n, long l, long l2) {
        this.val$downloadProgress = n;
        this.val$currentLoadedMBit = l;
        this.val$totalMBit = l2;
    }

    @Override
    public void run() {
        VideoSeriesOverviewFragment.this._textViewDownloadProgress.setVisibility(0);
        TextView textView = VideoSeriesOverviewFragment.this._textViewDownloadProgress;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.val$downloadProgress);
        stringBuilder.append("% (");
        stringBuilder.append(this.val$currentLoadedMBit);
        stringBuilder.append(" / ");
        stringBuilder.append(this.val$totalMBit);
        stringBuilder.append(" MB)");
        textView.setText((CharSequence)stringBuilder.toString());
        VideoSeriesOverviewFragment.this._setProgressTextAction = null;
    }
}

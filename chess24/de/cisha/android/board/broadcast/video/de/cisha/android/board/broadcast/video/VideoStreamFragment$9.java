/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.widget.TextView
 */
package de.cisha.android.board.broadcast.video;

import android.widget.TextView;

class VideoStreamFragment
implements Runnable {
    final /* synthetic */ int val$percent;

    VideoStreamFragment(int n) {
        this.val$percent = n;
    }

    @Override
    public void run() {
        VideoStreamFragment.this._bufferPercentage = this.val$percent;
        TextView textView = VideoStreamFragment.this._bufferText;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(VideoStreamFragment.this._bufferPercentage);
        stringBuilder.append("%");
        textView.setText((CharSequence)stringBuilder.toString());
    }
}

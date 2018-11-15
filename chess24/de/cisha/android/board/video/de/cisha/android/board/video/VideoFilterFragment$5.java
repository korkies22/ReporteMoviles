/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.View$OnClickListener
 */
package de.cisha.android.board.video;

import android.view.View;

class VideoFilterFragment
implements View.OnClickListener {
    VideoFilterFragment() {
    }

    public void onClick(View view) {
        VideoFilterFragment.this.resetFilterSelection();
        if (VideoFilterFragment.this._resetClickListener != null) {
            VideoFilterFragment.this._resetClickListener.onClick(view);
        }
    }
}

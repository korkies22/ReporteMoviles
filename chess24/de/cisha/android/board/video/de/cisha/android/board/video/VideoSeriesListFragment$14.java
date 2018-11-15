/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.widget.ImageView
 */
package de.cisha.android.board.video;

import android.view.View;
import android.widget.ImageView;

class VideoSeriesListFragment
implements View.OnClickListener {
    VideoSeriesListFragment() {
    }

    public void onClick(View view) {
        VideoSeriesListFragment.this._showOfflineOnly = VideoSeriesListFragment.this._showOfflineOnly ^ true;
        VideoSeriesListFragment.this.loadSeriesList();
        VideoSeriesListFragment.this._filterButtonOfflineVideos.setSelected(VideoSeriesListFragment.this._showOfflineOnly);
    }
}

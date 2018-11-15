/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.widget.ImageView
 */
package de.cisha.android.board.video;

import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.ImageView;
import de.cisha.android.board.video.VideoFilterFragment;

class VideoSeriesListFragment
implements DrawerLayout.DrawerListener {
    VideoSeriesListFragment() {
    }

    @Override
    public void onDrawerClosed(View view) {
        VideoSeriesListFragment.this._filterButton.setSelected(VideoSeriesListFragment.this._filterFragment.isFilterSelected());
    }

    @Override
    public void onDrawerOpened(View view) {
    }

    @Override
    public void onDrawerSlide(View view, float f) {
    }

    @Override
    public void onDrawerStateChanged(int n) {
        if (n == 2) {
            VideoSeriesListFragment.this._filterButton.setSelected(VideoSeriesListFragment.this._filterFragment.isFilterSelected());
        }
    }
}

/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.View$OnClickListener
 */
package de.cisha.android.board.video;

import android.view.View;
import de.cisha.android.board.video.VideoSeriesListFragment;
import de.cisha.android.board.video.model.VideoSeriesId;

class VideoSeriesListFragment.SeriesListAdapter.ViewHolder
implements View.OnClickListener {
    final /* synthetic */ VideoSeriesListFragment.SeriesListAdapter val$this$1;

    VideoSeriesListFragment.SeriesListAdapter.ViewHolder(VideoSeriesListFragment.SeriesListAdapter seriesListAdapter) {
        this.val$this$1 = seriesListAdapter;
    }

    public void onClick(View view) {
        ViewHolder.this.this$1.this$0.videoSeriesSelected(ViewHolder.this._seriesId);
    }
}

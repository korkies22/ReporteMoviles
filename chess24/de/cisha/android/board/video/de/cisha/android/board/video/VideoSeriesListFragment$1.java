/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.video;

import android.support.v7.widget.RecyclerView;
import de.cisha.android.board.service.ServiceProvider;
import de.cisha.android.board.video.VideoSeriesListFragment;
import de.cisha.android.board.video.model.VideoSeriesInformation;
import de.cisha.android.board.video.storage.ILocalVideoService;
import java.util.List;

class VideoSeriesListFragment
implements Runnable {
    VideoSeriesListFragment() {
    }

    @Override
    public void run() {
        final List<VideoSeriesInformation> list = ServiceProvider.getInstance().getLocalVideoService().getAllVideoSeriesInfoAvailable();
        VideoSeriesListFragment.this.runOnUiThreadBetweenStartAndDestroy(new Runnable(){

            @Override
            public void run() {
                VideoSeriesListFragment.this._seriesListAdapter = new VideoSeriesListFragment.SeriesListAdapter(VideoSeriesListFragment.this, list);
                VideoSeriesListFragment.this._recyclerView.setAdapter(VideoSeriesListFragment.this._seriesListAdapter);
                VideoSeriesListFragment.this.showReloadButton(list.isEmpty());
            }
        });
    }

}

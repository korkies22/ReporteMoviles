/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.video;

import de.cisha.android.board.service.ServiceProvider;
import de.cisha.android.board.video.VideoSeriesListFragment;
import de.cisha.android.board.video.model.VideoSeriesId;
import de.cisha.android.board.video.storage.ILocalVideoService;
import java.util.List;

class VideoSeriesListFragment
implements Runnable {
    VideoSeriesListFragment() {
    }

    @Override
    public void run() {
        final List<VideoSeriesId> list = ServiceProvider.getInstance().getLocalVideoService().getAllVideoSeriesIdsInOfflineMode();
        VideoSeriesListFragment.this.runOnUiThreadBetweenStartAndDestroy(new Runnable(){

            @Override
            public void run() {
                VideoSeriesListFragment.this._allVideoSeriesIdsInOfflineMode = list;
                if (VideoSeriesListFragment.this._seriesListAdapter != null) {
                    VideoSeriesListFragment.this._seriesListAdapter.notifyDataSetChanged();
                }
            }
        });
    }

}

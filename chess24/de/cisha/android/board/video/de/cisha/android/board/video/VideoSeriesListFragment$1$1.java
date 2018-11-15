/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.video;

import android.support.v7.widget.RecyclerView;
import de.cisha.android.board.video.VideoSeriesListFragment;
import de.cisha.android.board.video.model.VideoSeriesInformation;
import java.util.List;

class VideoSeriesListFragment
implements Runnable {
    final /* synthetic */ List val$seriesInfoList;

    VideoSeriesListFragment(List list) {
        this.val$seriesInfoList = list;
    }

    @Override
    public void run() {
        1.this.this$0._seriesListAdapter = new VideoSeriesListFragment.SeriesListAdapter(1.this.this$0, this.val$seriesInfoList);
        1.this.this$0._recyclerView.setAdapter(1.this.this$0._seriesListAdapter);
        1.this.this$0.showReloadButton(this.val$seriesInfoList.isEmpty());
    }
}

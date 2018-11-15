/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.video;

import android.support.v7.widget.RecyclerView;
import de.cisha.android.board.service.jsonparser.JSONVideoSeriesInfoListParser;
import de.cisha.android.board.video.VideoSeriesListFragment;
import de.cisha.android.board.video.model.VideoSeriesInformation;
import java.util.List;

class VideoSeriesListFragment
implements Runnable {
    final /* synthetic */ JSONVideoSeriesInfoListParser.VideoSeriesInfoList val$result;

    VideoSeriesListFragment(JSONVideoSeriesInfoListParser.VideoSeriesInfoList videoSeriesInfoList) {
        this.val$result = videoSeriesInfoList;
    }

    @Override
    public void run() {
        5.this.this$0.hideDialogWaiting();
        Object object = 5.this.this$0._showOfflineOnly ? 5.this.this$0.filterListOfflineOnly(this.val$result.getList()) : this.val$result.getList();
        5.this.this$0._seriesListAdapter = new VideoSeriesListFragment.SeriesListAdapter(5.this.this$0, (List<VideoSeriesInformation>)object);
        5.this.this$0._recyclerView.setAdapter(5.this.this$0._seriesListAdapter);
        object = 5.this.this$0;
        boolean bl = this.val$result.getSeriesCount() == 0;
        ((de.cisha.android.board.video.VideoSeriesListFragment)object).showNoResultsView(bl);
    }
}

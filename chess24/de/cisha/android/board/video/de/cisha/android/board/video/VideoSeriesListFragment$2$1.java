/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.video;

import de.cisha.android.board.video.VideoSeriesListFragment;
import de.cisha.android.board.video.model.VideoSeriesId;
import java.util.List;

class VideoSeriesListFragment
implements Runnable {
    final /* synthetic */ List val$allVideoSeriesIdsInOfflineMode;

    VideoSeriesListFragment(List list) {
        this.val$allVideoSeriesIdsInOfflineMode = list;
    }

    @Override
    public void run() {
        2.this.this$0._allVideoSeriesIdsInOfflineMode = this.val$allVideoSeriesIdsInOfflineMode;
        if (2.this.this$0._seriesListAdapter != null) {
            2.this.this$0._seriesListAdapter.notifyDataSetChanged();
        }
    }
}

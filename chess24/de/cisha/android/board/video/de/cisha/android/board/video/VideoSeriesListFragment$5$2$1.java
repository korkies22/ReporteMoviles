/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.video;

import de.cisha.android.board.IErrorPresenter;
import de.cisha.android.board.video.VideoSeriesListFragment;

class VideoSeriesListFragment
implements IErrorPresenter.IReloadAction {
    VideoSeriesListFragment() {
    }

    @Override
    public void performReload() {
        2.this.this$1.this$0.loadSeriesList();
    }
}

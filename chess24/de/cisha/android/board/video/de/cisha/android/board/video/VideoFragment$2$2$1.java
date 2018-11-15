/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.video;

import de.cisha.android.board.IErrorPresenter;
import de.cisha.android.board.video.VideoFragment;
import de.cisha.android.board.video.model.VideoId;
import de.cisha.android.board.video.model.VideoSeriesId;

class VideoFragment
implements IErrorPresenter.IReloadAction {
    VideoFragment() {
    }

    @Override
    public void performReload() {
        2.this.this$1.this$0.loadVideo(2.this.this$1.val$videoId, 2.this.this$1.val$seriesId);
    }
}
